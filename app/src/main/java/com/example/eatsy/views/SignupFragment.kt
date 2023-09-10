package com.example.eatsy.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentSignupBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.annotations.NotNull
import java.util.concurrent.TimeUnit

class SignupFragment : Fragment() {
   private lateinit var binding : FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var number:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSignupBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        binding.confirmBtn.setOnClickListener {

            number = binding.numberEditText.text.trim().toString()
            if(number.length!=10) {
                binding.textInputLayout.error = "please enter a valid number"

            }
            else {
                binding.signinProgressBar.visibility = View.VISIBLE
                binding.textInputLayout.error = null
                val countryCode = "+91"
                sendVerificationCode(countryCode+number)
            }
        }

        binding.backBtnSignUp.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        return binding.root
    }
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

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

            }
        }

        override fun onVerificationFailed(@NotNull e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("Login Activity", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("Login Activity","firebase Exception: $e")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("Login Activity","firebase Exception: $e")
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
            // Save verification ID and resending token so we can use them later
            val bundle = Bundle()
            bundle.putString("phone",number)
            bundle.putString("OTP" , verificationId)
            bundle.putParcelable("resendToken" , token)
            val otpFragment = OtpFragment()
            otpFragment.arguments = bundle
            binding.signinProgressBar.visibility = View.GONE
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_signup,otpFragment)?.addToBackStack(null)?.commit()


        }
    }

}