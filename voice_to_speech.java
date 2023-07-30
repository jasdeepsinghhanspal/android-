package com.example.voice_to_speech;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_TO_TEXT = 1;

    private TextView tvTranscribedText;
    private Button btnSpeechToText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTranscribedText = findViewById(R.id.tvTranscribedText);
        btnSpeechToText = findViewById(R.id.btnSpeechToText);

        btnSpeechToText.setOnClickListener(v -> startSpeechToText());
    }

    private void startSpeechToText() {
        // Check if the device supports speech recognition
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            // Check if the app has microphone permission
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                // Start speech recognition intent
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");

                try {
                    startActivityForResult(speechIntent, REQUEST_CODE_SPEECH_TO_TEXT);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Your device doesn't support speech-to-text", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Request microphone permission if not granted
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_SPEECH_TO_TEXT);
            }
        } else {
            Toast.makeText(this, "Your device doesn't support speech recognition", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_TO_TEXT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String transcribedText = result.get(0);
                tvTranscribedText.setText(transcribedText);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_SPEECH_TO_TEXT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSpeechToText();
            } else {
                Toast.makeText(this, "Microphone permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
-------------------------------------------------------------------------------------------------------------------------------

  
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <Button
        android:id="@+id/btnSpeechToText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Start Listening"
        android:layout_centerInParent="true"/>

    <TextView
        android:id="@+id/tvTranscribedText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/btnSpeechToText"
        android:layout_marginTop="16dp"
        android:textColor="@android:color/black"
        android:textSize="16sp"
        android:textStyle="bold"/>
</RelativeLayout>

  ------------------------------------------------------------------------------------------------------------------------------------
  <uses-permission android:name="android.permission.RECORD_AUDIO" />
