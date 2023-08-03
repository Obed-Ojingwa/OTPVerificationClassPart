package com.example.otpverification.ui.theme

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.otpverification.R
import com.example.otpverification.databinding.ActivityOtpverificationBinding
import android.text.Editable
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat.getSystemService


class OTPVerification : AppCompatActivity() {

    private lateinit var otpEt1: EditText
    private lateinit var otpEt2: EditText
    private lateinit var otpEt3: EditText
    private lateinit var otpEt4: EditText
    private lateinit var resendBtn: TextView

    // Resend every 60 seconds
    private var resendEnabled: Boolean = false

    // Resend time in seconds
    private val resendTime = 60

    private var selectedPosition = 0

    private lateinit var binding: ActivityOtpverificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)


        otpEt1 = findViewById(R.id.otpET1)
        otpEt2 = findViewById(R.id.otpET2)
        otpEt3 = findViewById(R.id.otpET3)
        otpEt4 = findViewById(R.id.otpET4)

        resendBtn = findViewById(R.id.resendBtn)
        val verifyBtn = findViewById<Button>(R.id.verifyBtn)

        val otpEmail = findViewById<TextView>(R.id.otpEmail)
        val otpMobile = findViewById<TextView>(R.id.otpMobile)

        // getting email and mobile from Register Activity through intent
        val getEmail = intent.getStringExtra("email")
        val getMobile = intent.getStringExtra("mobile")

        // setting emaila and mobile to TextView

        otpEmail.text = getEmail.toString()
        otpMobile.text = getMobile.toString()


        otpEt1.addTextChangedListener(textWatcher)
        otpEt2.addTextChangedListener(textWatcher)
        otpEt3.addTextChangedListener(textWatcher)
        otpEt4.addTextChangedListener(textWatcher)


        // by default opening keyboard at otpEt1
        showKeyboard(otpEt1)

        // start resend count down timer
        startCountDownTimer()

        resendBtn.setOnClickListener {

            if (resendEnabled) {

                //  handle your resend code here


                // start new resend count down timer
                startCountDownTimer()

            }
        }


        verifyBtn.setOnClickListener {
            val generateOtp =
                otpEt1.text.toString() + otpEt2.text.toString() + otpEt3.text.toString() + otpEt4.text.toString()

            if (generateOtp.length == 4) {

                // handle your verification here
            }
        }
    }

    private fun showKeyboard(otpEt: EditText) {

        otpEt.requestFocus()
        val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(otpEt, InputMethodManager.SHOW_IMPLICIT)

    }

    private fun startCountDownTimer() {

        resendEnabled = false
        resendBtn.setTextColor(android.graphics.Color.parseColor("#99000000"))

        val countDownTimer = object : CountDownTimer(60, 100) {
            override fun onTick(millisUntilFinished: Long) {
                // This method will be called on each interval (100 milliseconds in this case)
                // You can update UI elements or perform any other action here

                resendBtn.setText("Resend Code(" + millisUntilFinished / 60)

            }

            override fun onFinish() {
                // This method will be called when the timer finishes (after 60 seconds)
                // You can perform actions when the timer is finished

                resendEnabled = true
                resendBtn.setText("Resend Code")
                resendBtn.setTextColor(resources.getColor(R.color.my_primary))

            }
        }.start()
    }


    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // This method is called to notify that the text is about to be changed.

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // This method is called to notify that the text has been changed.
        }

        override fun afterTextChanged(s: Editable?) {
            // This method is called to notify that the text has been changed and processed.

            if (s != null) {
                if (s.length > 0) {

                    if (selectedPosition == 0) {

                        selectedPosition = 1
                        showKeyboard(otpEt2)

                    } else if (selectedPosition == 1) {

                        selectedPosition = 2
                        showKeyboard(otpEt3)

                    } else if (selectedPosition == 2) {

                        selectedPosition = 3
                        showKeyboard(otpEt4)
                    }
                }
            }
        }
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        // Your logic here





        if (keyCode == KeyEvent.KEYCODE_DEL) {
            // Logic for handling the BACKSPACE key (KEYCODE_DEL) here
            if (selectedPosition == 3) {
                selectedPosition = 2
                showKeyboard(otpEt3)
            } else if (selectedPosition == 2) {
                selectedPosition = 1
                showKeyboard(otpEt2)
            } else if (selectedPosition == 1) {
                selectedPosition = 0
                showKeyboard(otpEt1)
            }
            return true
        } else {
            return super.onKeyUp(keyCode, event)
        }
    }

}

