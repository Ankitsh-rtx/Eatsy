package com.example.eatsy.views

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentAddBasicDetailFragmentBinding
import com.example.eatsy.databinding.FragmentCartBinding
import com.example.eatsy.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Add_basic_detail_fragment : Fragment() {

    private lateinit var binding: FragmentAddBasicDetailFragmentBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBasicDetailFragmentBinding.inflate(layoutInflater)


        val birthdate=binding.dateOfBirth.transformIntoDatePicker(requireContext(),"dd/MM/YYYY")

        binding.addDatails.setOnClickListener{
            var firebaseDB  = FirebaseFirestore.getInstance()
            DataSource.user?.name=binding.firstName.text.toString() +" "+ binding.lastName.text.toString()
            DataSource.user?.age=binding.dateOfBirth.text.toString()
            DataSource.user?.email=binding.email.text.toString()
            firebaseAuth = FirebaseAuth.getInstance()
            DataSource.user?.let { it1 ->
                firebaseDB.collection("users").document(firebaseAuth.currentUser!!.uid.toString()).set(
                    it1
                ).addOnSuccessListener {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragmentContainerView,CartFragment())?.addToBackStack(R.id.homeFragment.toString())
                        ?.commit()
                }
            }

        }
        return binding.root
    }
    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

}