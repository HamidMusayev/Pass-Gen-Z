package com.example.passgenz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Generate password
        Button generateBtn = findViewById(R.id.generateBtn);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConstraintLayout mainLayout = findViewById(R.id.mainLayout);
                CheckBox capitalLetter = findViewById(R.id.capitalLetter);
                CheckBox lowercaseLetter = findViewById(R.id.lowerCaseLetter);
                CheckBox numbers = findViewById(R.id.numbers);
                CheckBox symbols = findViewById(R.id.symbols);
                TextView hintTxt = findViewById(R.id.hintTxt);
                EditText password = findViewById(R.id.passwordTxt);
                RadioButton radio4 = findViewById(R.id.radioButton4);
                RadioButton radio8 = findViewById(R.id.radioButton8);
                RadioButton radio12 = findViewById(R.id.radioButton12);
                RadioButton radio16 = findViewById(R.id.radioButton16);


                //Determines the length of the string based on which radio button the user has selected.
                int MAX_LENGTH = 8;
                if (radio4.isChecked()) {MAX_LENGTH = 4;}
                if (radio8.isChecked()) {MAX_LENGTH = 8;}
                if (radio12.isChecked()) {MAX_LENGTH = 12;}
                if (radio16.isChecked()) {MAX_LENGTH = 16;}

                // Determines the types of characters permitted when a check box is checked.
                String allowedCharacters = "";

                if (capitalLetter.isChecked() || lowercaseLetter.isChecked() || numbers.isChecked() || symbols.isChecked()) {

                    if (capitalLetter.isChecked()) {
                        allowedCharacters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    }
                    if (lowercaseLetter.isChecked()) {
                        allowedCharacters += "abcdefghijklmnopqrstuvwxyz";
                    }
                    if (numbers.isChecked()) {
                        allowedCharacters += "0123456789";
                    }
                    if (symbols.isChecked()) {
                        allowedCharacters += "!@#$%^&*()_-+=<>?/{}~|";
                    }

                    //Generate random password
                    Random random = new Random();
                    StringBuilder randompass = new StringBuilder();

                    for(int i=0; i<MAX_LENGTH; ++i) {
                        randompass.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
                        randompass.toString();
                        password.setText(randompass);
                    }

                }
                else{

                    //Make toast
                    Toast.makeText(MainActivity.this, "Please select Character type", Toast.LENGTH_SHORT).show();
                }

                //Caculate password strength
                int strength = 0;

                String generatedPass = password.getText().toString();

                if( generatedPass.length() < 8 ) {
                    strength -= 1;
                }
                else if( generatedPass.length() > 10 ) {
                    strength += 2;
                }
                else {
                    strength += 1;
                }

                //if it contains one digit, add 2 to total score
                if( generatedPass.matches("(?=.*[0-9]).*") ) {
                    strength += 2;
                }

                //if it contains one lower case letter, add 2 to total score
                if( generatedPass.matches("(?=.*[a-z]).*") ) {
                    strength += 2;
                }

                //if it contains one upper case letter, add 2 to total score
                if( generatedPass.matches("(?=.*[A-Z]).*") ) {
                    strength += 2;
                }

                //if it contains one special character, add 2 to total score
                if( generatedPass.matches("(?=.*[~!@#$%^&*()_-]).*") ) {
                    strength += 2;
                }

                if (strength > 6){
                    mainLayout.setBackgroundResource(R.drawable.background_green);
                    hintTxt.setText("Your password is Strong");
                }

                else if (strength < 5 ){
                    mainLayout.setBackgroundResource(R.drawable.background_red);
                    hintTxt.setText("Your password is Weak");
                }

                else{
                    mainLayout.setBackgroundResource(R.drawable.background_blue);
                    hintTxt.setText("Your password is Normal");
                }

            }
        });


        //Copy to clipboard
        Button copyBtn = findViewById(R.id.copyBtn);

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take text from edit text
                EditText passwordTxt = findViewById(R.id.passwordTxt);
                String generatedPass = passwordTxt.getText().toString();

                //Copy text to clipboard
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(MainActivity.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied text", generatedPass);
                clipboardManager.setPrimaryClip(clipData);

                //Make toast
                Toast.makeText(MainActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
