package com.example.eatsy.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentOtpBinding
import com.example.eatsy.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.annotations.NotNull
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit


class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var OTP: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var number: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        val bundle = arguments
        OTP = bundle?.getString("OTP").toString()
        resendToken = bundle?.getParcelable("resendToken")!!
        number = bundle.getString("phone").toString()


        val number = bundle?.getString("phone")

        binding.backBtnOtp.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        //GenericTextWatcher here works only for moving to next EditText when a number is entered
        //first parameter is the current EditText and second parameter is next EditText
        binding.ed1.addTextChangedListener(GenericTextWatcher(binding.ed1, binding.ed2))
        binding.ed2.addTextChangedListener(GenericTextWatcher(binding.ed2, binding.ed3))
        binding.ed3.addTextChangedListener(GenericTextWatcher(binding.ed3, binding.ed4))
        binding.ed4.addTextChangedListener(GenericTextWatcher(binding.ed4, binding.ed5))
        binding.ed5.addTextChangedListener(GenericTextWatcher(binding.ed5, binding.ed6))
        binding.ed6.addTextChangedListener(GenericTextWatcher(binding.ed6, null))

        //GenericKeyEvent here works for deleting the element and to switch back to previous EditText
        //first parameter is the current EditText and second parameter is previous EditText
        binding.ed1.setOnKeyListener(GenericKeyEvent(binding.ed1, null))
        binding.ed2.setOnKeyListener(GenericKeyEvent(binding.ed2, binding.ed1))
        binding.ed3.setOnKeyListener(GenericKeyEvent(binding.ed3, binding.ed2))
        binding.ed4.setOnKeyListener(GenericKeyEvent(binding.ed4,binding.ed3))
        binding.ed5.setOnKeyListener(GenericKeyEvent(binding.ed5,binding.ed4))
        binding.ed6.setOnKeyListener(GenericKeyEvent(binding.ed6,binding.ed5))

        binding.verifyNumTV.text = "Verify with OTP sent to $number"

        binding.confirmOtp.setOnClickListener {
            val typedOTP =
                (binding.ed1.text.toString() + binding.ed2.text.toString() + binding.ed3.text.toString()
                        + binding.ed4.text.toString() + binding.ed5.text.toString() + binding.ed6.text.toString())

            binding.resendOtpBtn.visibility = View.VISIBLE
            if (typedOTP.isNotEmpty()) {
                binding.otpProgressBar.visibility = View.VISIBLE
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    signInWithPhoneAuthCredential(credential)

                } else {
                    Toast.makeText(context, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

        binding.resendOtpBtn.setOnClickListener {
            resendVerificationCode()
            binding.ed1.text.clear()
            binding.ed2.text.clear()
            binding.ed3.text.clear()
            binding.ed4.text.clear()
            binding.ed5.text.clear()
            binding.ed6.text.clear()
            it.visibility = View.GONE
            it.isEnabled = false

            Handler(Looper.myLooper()!!).postDelayed(Runnable {
                it.visibility = View.VISIBLE
                it.isEnabled = true
            }, 60000)
        }

        return binding.root
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(@NotNull credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("Login Activity", "onVerificationCompleted:$credential")
            val code = credential.smsCode
            if (code != null) {
                signInWithPhoneAuthCredential(credential)

            }
        }

        override fun onVerificationFailed(@NotNull e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("Login Activity", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("Login Activity", "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            OTP = verificationId
            resendToken = token
        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.otpProgressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser?.uid
                    Log.d("user", user.toString())
                    var firebaseDB  = FirebaseFirestore.getInstance()
                    firebaseDB.collection("users").document(user.toString()).get().addOnSuccessListener { data ->
                        val UserProfile = data.toObject(User::class.java)
                        DataSource.user = UserProfile
                        // intent to main activity
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        activity?.finish()
                    }.addOnFailureListener{
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        activity?.finish()
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("Login Activity", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    private fun resendVerificationCode() {
        val code = "+91"
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(code+number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun pasteText() {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        if (clipboardManager.hasPrimaryClip()) {
            val item = clipboardManager.primaryClip?.getItemAt(0)

            val ptext = item?.text
            ptext?.let {
                for (i in it.indices) {
                    // Handle 6 cases and paste to 6 edittexts
                    if(i==0) binding.ed1.setText( "${it[i]}")
                    else if(i==1) binding.ed2.setText( "${it[i]}")
                    else if(i==2) binding.ed3.setText( "${it[i]}")
                    else if(i==3) binding.ed4.setText( "${it[i]}")
                    else if(i==4) binding.ed5.setText( "${it[i]}")
                    else  binding.ed6.setText( "${it[i]}")
                }
            }
        }
    }


}

class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.ed1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed4 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed5 -> if (text.length == 1) nextView!!.requestFocus()

        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int,

    ) {

    }

}

class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.ed1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }



}