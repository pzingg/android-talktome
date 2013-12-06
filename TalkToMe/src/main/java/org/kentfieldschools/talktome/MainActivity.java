package org.kentfieldschools.talktome;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

public class MainActivity extends Activity {

    // Declare some instance variables for our app.
    private String text;
    private Button talkToMe;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The text we will be speaking.
        text = "Congratulations, you just made your first app!";

        // Start with an inactive button until the text to speech
        // engine is ready.
        talkToMe = (Button) findViewById(R.id.button1);
        talkToMe.setEnabled(false);
        talkToMe.setClickable(false);

        // Create our speach engine, using this class as the context,
        // and creating a handler to check whether the TTS engine setup
        // is successful.
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            // Called when the TextToSpeech engine is set up or fails
            // to initialize.
            // Set up American English as the dialect.
            // And make our button clickable.
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result >= 0) {
                        talkToMe.setEnabled(true);
                        talkToMe.setClickable(true);
                    } else {
                        Log.e("TTS", "This language is not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });

        // Create a handler that listens for a click on the "Talk to Me" button
        talkToMe.setOnClickListener(new View.OnClickListener() {

            // When button is clicked call our speak function.
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    // Called when our activity is destroyed.
    // We have to shutdown the text-to-textToSpeech engine.
    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    // Here's the function that does the speaking.
    private void speak() {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
