package com.example.eatsy.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentOtpBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

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
            val intent = Intent(requireContext(),MainActivity::class.java )
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            activity?.finish()
        }
        return binding.root
    }

}

class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            R.id.ed1 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed2 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed3 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed4 -> if (text.length == 1) nextView!!.requestFocus()
            R.id.ed5 -> if (text.length == 1) nextView!!.requestFocus()


            //You can use EditText4 same as above to hide the keyboard
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
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