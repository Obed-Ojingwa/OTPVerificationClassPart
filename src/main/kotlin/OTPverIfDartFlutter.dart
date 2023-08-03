import 'package:flutter/material.dart';
import 'dart:async';

class OTPVerification extends StatefulWidget {
  @override
  _OTPVerificationState createState() => _OTPVerificationState();
}

class _OTPVerificationState extends State<OTPVerification> {
  late TextEditingController otpEt1;
  late TextEditingController otpEt2;
  late TextEditingController otpEt3;
  late TextEditingController otpEt4;
  late Timer? countDownTimer;
  bool resendEnabled = false;
  int resendTime = 60;
  int selectedPosition = 0;

  @override
  void initState() {
    super.initState();

    otpEt1 = TextEditingController();
    otpEt2 = TextEditingController();
    otpEt3 = TextEditingController();
    otpEt4 = TextEditingController();

    // By default opening keyboard at otpEt1
    showKeyboard(otpEt1);

    // Start resend count down timer
    startCountDownTimer();
  }

  @override
  void dispose() {
    countDownTimer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('OTP Verification'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                buildOTPTextField(otpEt1),
                buildOTPTextField(otpEt2),
                buildOTPTextField(otpEt3),
                buildOTPTextField(otpEt4),
              ],
            ),
            SizedBox(height: 20),
            TextButton(
              onPressed: () {
                if (resendEnabled) {
                  // Handle your resend code here
                  // Start new resend count down timer
                  startCountDownTimer();
                }
              },
              child: Text(
                'Resend Code',
                style: TextStyle(
                  color: resendEnabled ? Colors.blue : Colors.grey,
                ),
              ),
            ),
            ElevatedButton(
              onPressed: () {
                String generateOtp = otpEt1.text +
                    otpEt2.text +
                    otpEt3.text +
                    otpEt4.text;

                if (generateOtp.length == 4) {
                  // Handle your verification here
                }
              },
              child: Text('Verify'),
            ),
          ],
        ),
      ),
    );
  }

  Widget buildOTPTextField(TextEditingController controller) {
    return Container(
      width: 40,
      height: 40,
      margin: EdgeInsets.all(8),
      child: TextField(
        controller: controller,
        maxLength: 1,
        keyboardType: TextInputType.number,
        textAlign: TextAlign.center,
        onChanged: (value) {
          if (value.isNotEmpty) {
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
        },
        onEditingComplete: () {
          FocusScope.of(context).unfocus();
        },
      ),
    );
  }

  void showKeyboard(TextEditingController otpEt) {
    otpEt.selection = TextSelection.fromPosition(
        TextPosition(offset: otpEt.text.length));
    FocusScope.of(context).requestFocus(FocusNode());
  }

  void startCountDownTimer() {
    setState(() {
      resendEnabled = false;
    });

    countDownTimer = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        resendTime--;
        resendEnabled = resendTime == 0;
      });

      if (resendTime == 0) {
        timer.cancel();
        setState(() {
          resendTime = 60;
        });
      }
    });
  }
}
