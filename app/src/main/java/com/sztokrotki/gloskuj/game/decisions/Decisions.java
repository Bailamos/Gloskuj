package com.sztokrotki.gloskuj.game.decisions;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;
import java.util.Random;

public class Decisions extends Activity {

    ArrayList<Word> knownWords;
    TextView randomWordText;
    int randomWordIndex;
    Word randomWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decisions);

        randomWordText = (TextView)findViewById(R.id.randomWordText);
        knownWords = new ArrayList<>();
        knownWords.add(new Word("Wafel", true, 2));
        knownWords.add(new Word("Wawel", false, 2));
        knownWords.add(new Word("Tomek", true, 0));
        knownWords.add(new Word("Domek", false, 0));
        knownWords.add(new Word("Szale", true, 0));
        knownWords.add(new Word("Żale", false, 0));
        knownWords.add(new Word("Kuma", true, 0));
        knownWords.add(new Word("Guma", false, 0));
        knownWords.add(new Word("Walą", false, 0));
        knownWords.add(new Word("Falą", true, 0));

        Button buttonOne = (Button) findViewById(R.id.Voiceless);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!knownWords.isEmpty()) {
                    testUserChoice(true);
                }
            }
        });

        Button buttonTwo = (Button) findViewById(R.id.Voice);
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!knownWords.isEmpty()) {
                    testUserChoice(false);
                }
            }
        });

        setNewRandomWord();
    }

    private void testUserChoice(Boolean choice){
        TextView decisionLog = (TextView)findViewById(R.id.decisionLog);

        if(choice == randomWord.voiceless) {
            knownWords.remove(randomWord);
            decisionLog.setText(fromHtml(""));

            if(knownWords.isEmpty())
            {
                decisionLog.setText(fromHtml("<font color='#008000'>Super! Rozwiązałeś wszystko.</font>"));
            }
            else
                setNewRandomWord();
        }
        else
            decisionLog.setText(fromHtml("<font color='#EE0000'>Ups! Zła odpowiedź.</font>"));
    }

    private void setNewRandomWord() {
        randomWordIndex = new Random().nextInt(knownWords.size());
        randomWord = knownWords.get(randomWordIndex);
        randomWordText.setText(getWordWithHighlightedLetter(randomWord));
    }

    private Spanned getWordWithHighlightedLetter(Word word){
        String tempWord = "";

        for(int i = 0; i < word.content.length(); i++)
        {
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
}
