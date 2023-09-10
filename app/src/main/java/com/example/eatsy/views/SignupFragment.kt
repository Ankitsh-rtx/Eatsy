package com.example.eatsy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


class SignupFragment : Fragment() {
   private lateinit var binding : FragmentSignupBinding
   private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSignupBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()


        // Inflate the layout for this fragment
        binding.confirmBtn.setOnClickListener {
            val number = binding.numberEditText.text
            if(number.toString().length!=10) {
                binding.textInputLayout.error = "please enter a valid number"

            }
            else {
                binding.textInputLayout.error = null
//                sendVerificationCode()
                val bundle = Bundle()
                bundle.putString("phone",number.toString())
                val otpFragment = OtpFragment()
                otpFragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_signup,otpFragment)?.addToBackStack(null)?.commit()
            }
        }

        binding.backBtnSignUp.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        return binding.root
    }

    private fun sendVerificationCode() {
        TODO("Not yet implemented")
    }


}