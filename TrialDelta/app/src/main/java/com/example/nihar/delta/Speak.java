package com.example.nihar.delta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class Speak extends Fragment {
    SpeechRecognizer mSpeechRecogniser;
    Intent mSpeechRecogniserIntent;
    String commandText;
    public Speak() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speak, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //checkPermission();
        //editText=findViewById(R.id.editText);

        if(!GlobalVariables.user.boolSpeechInput){
            getView().findViewById(R.id.speakBtn).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.speakBtn).setEnabled(false);
            return;
        }
        mSpeechRecogniser= SpeechRecognizer.createSpeechRecognizer(getContext());
        mSpeechRecogniserIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecogniser.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }
            @Override
            public void onBeginningOfSpeech() {
            }
            @Override
            public void onRmsChanged(float v) {
            }
            @Override
            public void onBufferReceived(byte[] bytes) {
            }
            @Override
            public void onEndOfSpeech() {
            }
            @Override
            public void onError(int i) {
            }
            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches!=null)
                {
                    commandText= (matches.get(0));
                }
                processCommands();
            }
            @Override
            public void onPartialResults(Bundle bundle) {
            }
            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

        //mSpeechRecogniser.startListening(mSpeechRecogniserIntent);

        getView().findViewById(R.id.speakBtn).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP :
                        mSpeechRecogniser.stopListening();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecogniser.startListening(mSpeechRecogniserIntent);
                        break;
                }
                return false;
            }
        });
    }
    private void processCommands(){
        commandText = commandText.toLowerCase();
        //Toast.makeText(getContext(),commandText,Toast.LENGTH_LONG).show();
        switch(GlobalVariables.current_page){
            case 1:
                processLoginPage();
                break;
            case 2:
                processRegisterPage();
                break;
            case 3:
                processMainMenu();
                break;
        }
    }
    private void processLoginPage(){
        String temp;
        if(commandText.indexOf("username")==0){
            temp = commandText.substring("username".length());
            temp= temp.replaceAll("\\s+","");
            ((Login)getActivity()).setUsername(temp);
        }
        else if(commandText.indexOf("password")==0){
            temp = commandText.substring("password".length());
            temp= temp.replaceAll("\\s+","");
            ((Login)getActivity()).setPass(temp);
        }
        else if(commandText.indexOf("login")==0){
            ((Login)getActivity()).clickLogin();
        }
        else if(commandText.indexOf("register")==0){
            ((Login)getActivity()).clickRegister();
        }
        else if(commandText.indexOf("skip")==0){
            ((Login)getActivity()).clickSkip();
        }
        else if(commandText.indexOf("back")==0){
            ((Login)getActivity()).callBack();
        }
        else if(commandText.indexOf("help")==0){
            TTS.speak("Available commands are: " +
                    " Speak Username your username to set your username in Username Field." +
                    " Speak Password your password to set your password in Password Field." +
                    " Speak Login to select Login Button." +
                    " Speak Register to select Register Button." +
                    " Speak Skip to skip to Main Menu." +
                    " Speak back to go back.");
        }
        else {
            TTS.speak("Unknown Command");
        }
    }
    private void processRegisterPage(){
        String temp;
        if(commandText.indexOf("username")==0){
            temp = commandText.substring("username".length());
            temp= temp.replaceAll("\\s+","");
            ((Register)getActivity()).setUsername(temp);
        }
        else if(commandText.indexOf("password")==0){
            temp = commandText.substring("password".length());
            temp= temp.replaceAll("\\s+","");
            ((Register)getActivity()).setPass(temp);
        }
        else if(commandText.indexOf("verify password")==0){
            temp = commandText.substring("verify password".length());
            temp= temp.replaceAll("\\s+","");
            ((Register)getActivity()).setrePass(temp);
        }
        else if(commandText.indexOf("register")==0){
            ((Register)getActivity()).clickRegister();
        }
        else if(commandText.indexOf("skip")==0){
            ((Register)getActivity()).clickSkip();
        }
        else if(commandText.indexOf("back")==0){
            ((Register)getActivity()).callBack();
        }
        else if(commandText.indexOf("help")==0){
            TTS.speak("Available commands are: " +
                    " Speak Username your username to set your username in Username Field." +
                    " Speak Password your password to set your password in Password Field." +
                    " Speak verify Password your password to set your password in verify Password Field." +
                    " Speak Register to select Register Button." +
                    " Speak Skip to skip to Main Menu" +
                    " Speak back to go back.");
        }
        else {
            TTS.speak("Unknown Command");
        }

    }
    public void processMainMenu(){
        String temp;

        if(commandText.indexOf("text")==0){
            ((MainMenu)getActivity()).clickTextRecog();
        }
        else if(commandText.indexOf("object")==0){
            ((MainMenu)getActivity()).clickObjectDet();
        }
        else if(commandText.indexOf("settings")==0){
            ((MainMenu)getActivity()).clickSettings();
        }
        else if(commandText.indexOf("profile")==0){
            ((MainMenu)getActivity()).clickProfile();
        }
        else if(commandText.indexOf("back")==0){
            ((MainMenu)getActivity()).callBack();
        }
        else if(commandText.indexOf("help")==0){
            TTS.speak("Available commands are: " +
                    " Speak Text to go to Text Recognition." +
                    " Speak Object to go to Object Detection." +
                    " Speak Settings to go to Settings" +
                    " Speak Profile to go to Profile" +
                    " Speak back to go back.");
        }
        else {
            TTS.speak("Unknown Command");
        }
    }
}
