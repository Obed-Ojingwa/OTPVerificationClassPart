import UIKit

class OTPVerification: UIViewController {






    @IBOutlet weak var otpEt1: UITextField!
    @IBOutlet weak var otpEt2: UITextField!
    @IBOutlet weak var otpEt3: UITextField!
    @IBOutlet weak var otpEt4: UITextField!
    @IBOutlet weak var resendBtn: UIButton!

    // Resend every 60 seconds
    var resendEnabled = false

    // Resend time in seconds
    let resendTime = 60

    var selectedPosition = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        // Your setup code here

        otpEt1.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
        otpEt2.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
        otpEt3.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
        otpEt4.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)

        // By default, open the keyboard at otpEt1
        showKeyboard(for: otpEt1)

        // Start the resend count down timer
        startCountDownTimer()

        resendBtn.addTarget(self, action: #selector(resendBtnTapped), for: .touchUpInside)
    }

    func showKeyboard(for textField: UITextField) {
        textField.becomeFirstResponder()
    }

    @objc func resendBtnTapped() {
        if resendEnabled {
            // Handle your resend code here

            // Start a new resend count down timer
            startCountDownTimer()
        }
    }

    @objc func textFieldDidChange(_ textField: UITextField) {
        if let text = textField.text, !text.isEmpty {
            if selectedPosition == 0 {
                selectedPosition = 1
                showKeyboard(for: otpEt2)
            } else if selectedPosition == 1 {
                selectedPosition = 2
                showKeyboard(for: otpEt3)
            } else if selectedPosition == 2 {
                selectedPosition = 3
                showKeyboard(for: otpEt4)
            }
        }
    }

    func startCountDownTimer() {
        resendEnabled = false
        resendBtn.setTitleColor(UIColor(red: 0.6, green: 0.6, blue: 0.6, alpha: 1.0), for: .normal)

        let countDownTimer = CountDownTimer(timeInterval: 1, duration: 60) { remainingTime in
            // This method will be called on each interval (1 second in this case)
            // You can update UI elements or perform any other action here

            self.resendBtn.setTitle("Resend Code(\(Int(remainingTime)))", for: .normal)
        }

        countDownTimer.start { [weak self] in
            // This block will be called when the timer finishes (after 60 seconds)
            // You can perform actions when the timer is finished

            self?.resendEnabled = true
            self?.resendBtn.setTitle("Resend Code", for: .normal)
            self?.resendBtn.setTitleColor(UIColor.systemBlue, for: .normal)
        }
    }

    // Implement the logic for handling the BACKSPACE key (KEYCODE_DEL)
    override func pressesEnded(_ presses: Set<UIPress>, with event: UIPressesEvent?) {
        super.pressesEnded(presses, with: event)

        if let key = presses.first?.key, key.keyCode == .keyboardDelete {
            // Logic for handling the BACKSPACE key (KEYCODE_DEL) here
            if selectedPosition == 3 {
                selectedPosition = 2
                showKeyboard(for: otpEt3)
            } else if selectedPosition == 2 {
                selectedPosition = 1
                showKeyboard(for: otpEt2)
            } else if selectedPosition == 1 {
                selectedPosition = 0
                showKeyboard(for: otpEt1)
            }
        }
    }
}
