package com.sztokrotki.gloskuj.game.decisions;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private String azGameEnabled;
    private String chosenLetters;
    TextView randomWordText;
    int randomWordIndex;
    Word randomWord;
    ArrayList<Word> knownWords, actualWords;
    View view;
    boolean randomOrder = false;

    public GameFragment() {
    }

    public static GameFragment newInstance(Boolean azGameEnabled, String message, String randomWordsOrder) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        if(azGameEnabled)
            args.putString(ARG_PARAM1, "YES");
        else
            args.putString(ARG_PARAM1, "NO");
        args.putString(ARG_PARAM2, message);
        args.putString(ARG_PARAM3, randomWordsOrder);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            azGameEnabled = getArguments().getString(ARG_PARAM1);
            chosenLetters = getArguments().getString(ARG_PARAM2);
            randomOrder = getArguments().getString(ARG_PARAM3).equals("yes") ? true : false;
        }
        initWords();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       initGUI();

       if(azGameEnabled.equals("YES")) {
           if(chosenLetters.charAt(0) < chosenLetters.charAt(1))
               pickWords(chosenLetters.charAt(0), chosenLetters.charAt(1));
           else
               pickWords(chosenLetters.charAt(1), chosenLetters.charAt(0));
       }
       else {
           pickWords(chosenLetters.charAt(0), chosenLetters.charAt(0));
       }

       if(randomOrder){
           setNewRandomWord();
       }
       else {
           nextWord();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        return view;
    }

    private void testUserChoice(Boolean choice){
        TextView decisionLog = (TextView)view.findViewById(R.id.decisionLog);

        if(choice == randomWord.voiceless) {
            actualWords.remove(randomWord);
            decisionLog.setText(fromHtml(""));

            if(actualWords.isEmpty()) {
                decisionLog.setText(fromHtml("<font color='#008000'>Super! Rozwiązałeś wszystko.</font>"));
            }
            else{
                if(randomOrder){
                    setNewRandomWord();
                }
                else {
                    nextWord();
                }
            }

        }
        else
            decisionLog.setText(fromHtml("<font color='#EE0000'>Spróbuj ponownie!.</font>"));
    }

    private void nextWord(){
        randomWord = actualWords.get(0);
        randomWordText.setText(getWordWithHighlightedLetter(randomWord));
    }

    private void setNewRandomWord() {
        randomWordIndex = new Random().nextInt(actualWords.size()); //TODO: Check size = 0
        randomWord = actualWords.get(randomWordIndex);
        randomWordText.setText(getWordWithHighlightedLetter(randomWord));
    }

    private Spanned getWordWithHighlightedLetter(Word word){
        String tempWord = "";

        for(int i = 0; i < word.content.length(); i++) {
            if(i != word.voicePosition)
                tempWord += word.content.charAt(i);
            else
                tempWord += "<font color='#EE0000'>" + word.content.charAt(i) + "</font>"; //zaznacz gloske bezdzwieczna
        }

        return fromHtml(tempWord);
    }

    @SuppressWarnings("deprecation")
    private static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    private void initGUI(){
        randomWordText = (TextView) view.findViewById(R.id.randomWordText);

        Button buttonOne = (Button) view.findViewById(R.id.Voiceless);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!actualWords.isEmpty()) {
                    testUserChoice(true);
                }
            }
        });

        Button buttonTwo = (Button) view.findViewById(R.id.Voice);
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!actualWords.isEmpty()) {
                    testUserChoice(false);
                }
            }
        });
    }

    private void pickWords(Character startLetter, Character endLetter){
        List<Character> characters = new ArrayList<>();
        while(startLetter <= endLetter) {
            characters.add(startLetter);
            startLetter++;
        }
        findWordsWithLetter(characters);
    }

    private void findWordsWithLetter(List<Character> letters){
        for(int i = 0; i < knownWords.size(); i++) {
            for(Character character : letters) {
                if (character.equals(Character.toUpperCase(knownWords.get(i).voice))) {
                    int pairPosition = knownWords.get(i).pairPosition;

                    if(!actualWords.contains((knownWords.get(i)))) {
                        actualWords.add(knownWords.get(i));
                    }
                    if(!actualWords.contains((knownWords.get(i + pairPosition)))){
                        actualWords.add(knownWords.get(i + pairPosition)); //add Word Pair
                    }
                }
            }
        }
    }

    private void initWords(){
        knownWords = new ArrayList<>();
        actualWords = new ArrayList<>();

        //dodawać parami!!!!
        knownWords.add(new Word("Wafel", true, 'f', 2, 1));
        knownWords.add(new Word("Wawel", false,'w', 2, -1));

        knownWords.add(new Word("Loty", true,'t', 2,1));
        knownWords.add(new Word("Lody", false, 'd',2, -1));

        knownWords.add(new Word("Baki", false,'b', 0,1));
        knownWords.add(new Word("Paki", true, 'p',0, -1));

        knownWords.add(new Word("Tomek", true, 't', 0, 1));
        knownWords.add(new Word("Domek", false, 'd', 0, -1));

        knownWords.add(new Word("Kuma", true, 'k', 0, 1));
        knownWords.add(new Word("Guma", false, 'g',0, -1));

        knownWords.add(new Word("Walą", false,'w', 0,1));
        knownWords.add(new Word("Falą", true, 'f',0, -1));

        knownWords.add(new Word("Koty", true,'t', 2,1));
        knownWords.add(new Word("Kody", false, 'd',2, -1));
    }
}
