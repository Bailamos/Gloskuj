package com.sztokrotki.gloskuj.game.decisions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseGameKindFragment extends Fragment {

    private Spinner spinnerA, spinnerZ, spinnerOneLetter;
    private CheckBox checkBoxOneLetter, checkBoxAZ;
    private Switch randomWordsSwitch;
    private Button startbutton;
    private List<String> letterList, voiceLetters;
    private OnFragmentInteractionListener mListener;

    public ChooseGameKindFragment() {
        letterList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_game_kind, container, false);
        spinnerA = (Spinner) view.findViewById(R.id.spinner);
        spinnerZ = (Spinner) view.findViewById(R.id.spinner2);
        startbutton = (Button) view.findViewById(R.id.button2);
        spinnerOneLetter = (Spinner) view.findViewById(R.id.spinner3);
        checkBoxOneLetter = (CheckBox) view.findViewById(R.id.checkBox);
        randomWordsSwitch  = (Switch) view.findViewById(R.id.switch1);
        checkBoxAZ = (CheckBox) view.findViewById(R.id.checkBoxAZ);
        addCheckBoxListeners();
        addButtonListeners();
        initLetterList();

        populateSpinner(spinnerA, letterList);
        populateSpinner(spinnerZ, letterList);
        populateSpinner(spinnerOneLetter, voiceLetters);
        spinnerOneLetter.setSelection(0);
        spinnerA.setSelection(0);
        spinnerZ.setSelection(letterList.size() - 1);

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(boolean azGameEnabled, String message, String randomWordsOrder);
    }

    private void populateSpinner(Spinner spinner, List<String> letters){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, letters);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void initLetterList(){
        Character letter = 'A';
        while (letter <= 'Z'){
            letterList.add(letter.toString());
            letter++;
        }

        voiceLetters = new ArrayList<String>(Arrays.asList("W", "F", "T", "D", "P", "B", "K", "G"));
    }

    private void addCheckBoxListeners(){
        checkBoxOneLetter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxAZ.setChecked(!checkBoxOneLetter.isChecked());
                spinnerA.setEnabled(!checkBoxOneLetter.isChecked());
                spinnerZ.setEnabled(!checkBoxOneLetter.isChecked());
                spinnerOneLetter.setEnabled(checkBoxOneLetter.isChecked());
            }
        });
        checkBoxOneLetter.setChecked(true);

        checkBoxAZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxOneLetter.setChecked(!checkBoxAZ.isChecked());
            }
        });
    }

    private void addButtonListeners(){
       startbutton.setOnClickListener(new Button.OnClickListener() {
           public void onClick(View v) {
               if(checkBoxAZ.isChecked())
                   sendMessageToActivity(checkBoxAZ.isChecked(), spinnerA.getSelectedItem().toString() + spinnerZ.getSelectedItem().toString(), randomWordsSwitch.isChecked() ? "yes" : "no");
               else
                   sendMessageToActivity(checkBoxAZ.isChecked(), spinnerOneLetter.getSelectedItem().toString(), randomWordsSwitch.isChecked() ? "yes" : "no");
           }
       });
    }

    public void sendMessageToActivity(boolean action, String message, String randomWordsOrder) {
        if (mListener != null) {
            mListener.onFragmentInteraction(action, message, randomWordsOrder);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChooseGameKindFragment.OnFragmentInteractionListener) {
            mListener = (ChooseGameKindFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ChooseGameKindFragment.OnFragmentInteractionListener) {
            mListener = (ChooseGameKindFragment.OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
