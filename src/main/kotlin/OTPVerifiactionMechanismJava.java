package com.example.otpverification.ui.theme;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.otpverification.R;

public class OTPVerification extends AppCompatActivity {

    private EditText otpEt1;
    private EditText otpEt2;
    private EditText otpEt3;
    private EditText otpEt4;
    private TextView resendBtn;

    // Resend every 60 seconds
    private boolean resendEnabled = false;

    // Resend time in seconds
    private final int resendTime = 60;

    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpEt1 = findViewById(R.id.otpET1);
        otpEt2 = findViewById(R.id.otpET2);
        otpEt3 = findViewById(R.id.otpET3);
        otpEt4 = findViewById(R.id.otpET4);

        resendBtn = findViewById(R.id.resendBtn);
        Button verifyBtn = findViewById(R.id.verifyBtn);

        TextView otpEmail = findViewById(R.id.otpEmail);
        TextView otpMobile = findViewById(R.id.otpMobile);

        // getting email and mobile from Register Activity through intent
        String getEmail = getIntent().getStringExtra("email");
        String getMobile = getIntent().getStringExtra("mobile");

        // setting email and mobile to TextView
        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);

        // by default opening keyboard at otpEt1
        showKeyboard(otpEt1);

        // start resend count down timer
        startCountDownTimer();

        resendBtn.setOnClickListener(v -> {
            if (resendEnabled) {
                // handle your resend code here
                // start new resend count down timer
                startCountDownTimer();
            }
        });

        verifyBtn.setOnClickListener(v -> {
            String generateOtp = otpEt1.getText().toString()
                    + otpEt2.getText().toString()
                    + otpEt3.getText().toString()
                    + otpEt4.getText().toString();

            if (generateOtp.length() == 4) {
                // handle your verification here
            }
        });
    }

    private void showKeyboard(EditText otpEt) {
        otpEt.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(otpEt, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void startCountDownTimer() {
        resendEnabled = false;
        resendBtn.setTextColor(android.graphics.Color.parseColor("#99000000"));

        new CountDownTimer(60000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // This method will be called on each interval (100 milliseconds in this case)
                // You can update UI elements or perform any other action here
                resendBtn.setText("Resend Code(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                // This method will be called when the timer finishes (after 60 seconds)
                // You can perform actions when the timer is finished
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.my_primary));
            }
        }.start();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // This method is called to notify that the text is about to be changed.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // This method is called to notify that the text has been changed.
        }

        @Override
        public void afterTextChanged(Editable s) {
            // This method is called to notify that the text has been changed and processed.
            if (s != null) {
                if (s.length() > 0) {
                    if (selectedPosition == 0) {
                        selectedPosition = 1;
                        showKeyboard(otpEt2);
                    } else if (selectedPosition == 1) {
                        selectedPosition = 2;
                        showKeyboard(otpEt3);
                    } else if (selectedPosition == 2) {
                        selectedPosition = 3;
                        showKeyboard(otpEt4);
                    }
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // Your logic here

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            // Logic for handling the BACKSPACE key (KEYCODE_DEL) here
            if (selectedPosition == 3) {
                selectedPosition = 2;
                showKeyboard(otpEt3);
            } else if (selectedPosition == 2) {
                selectedPosition = 1;
                showKeyboard(otpEt2);
            } else if (selectedPosition == 1) {
                selectedPosition = 0;
                showKeyboard(otpEt1);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
