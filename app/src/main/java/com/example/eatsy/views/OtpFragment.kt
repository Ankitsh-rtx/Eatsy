package com.example.eatsy.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentOtpBinding

class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(layoutInflater)

        val bundle = arguments
        val number = bundle?.getString("phone")

        binding.backBtnOtp.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        binding.verifyNumTV.text = "Verify with OTP sent to $number"

        binding.confirmOtp.setOnClickListener {
            val intent = Intent(requireContext(),MainActivity::class.java )
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            activity?.finish()
        }
        return binding.root
    }

}