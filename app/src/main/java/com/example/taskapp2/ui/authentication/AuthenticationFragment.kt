package com.example.taskapp2.ui.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentAuthenticationBinding
import com.example.taskapp2.extencions.showToast
import com.fraggjkee.smsconfirmationview.SmsConfirmationView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding
    private var auth = FirebaseAuth.getInstance()
    private var correctionCode:String ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationBinding.inflate(inflater,container,false)

        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ccp.registerPhoneNumberTextView(binding.phoneNumberEdt)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initListeners() {
     binding.btnSend.setOnClickListener {
        if (binding.phoneNumberEdt.text!!.isNotEmpty()){
            sendPhone()
     showToast("send")
        }else{
            binding.phoneNumberEdt.error = " Add a number"
        }
     }
        binding.btnConfirm.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode() {
        val credential = correctionCode?.let { it1 -> PhoneAuthProvider.
        getCredential(it1,binding.etLayoutCode.enteredCode) }
        if (credential != null) {
            signInWithPhoneAuthCredential(credential)
        }
        val view = binding.etLayoutCode
        view.onChangeListener = SmsConfirmationView.OnChangeListener { _, _ ->

        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun sendPhone() {
        auth.setLanguageCode("ru")
        auth.useAppLanguage()
      val options = PhoneAuthOptions.newBuilder(auth)
          .setPhoneNumber("+996${binding.phoneNumberEdt.text}")
          .setTimeout(60L,TimeUnit.SECONDS)
          .setActivity(requireActivity())
          .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

              override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                 //navigate to home
              }

              override fun onVerificationFailed(p0: FirebaseException) {
                // showToast(p0.message.toString())
                  Toast.makeText(requireContext(),p0.message.toString(),Toast.LENGTH_LONG).show()
              }

              override fun onCodeSent(verifycationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                 correctionCode = verifycationCode
                  binding.btnSend.isVisible = false
                  binding.phoneNumberEdt.isVisible = false
                  binding.ccp.isVisible = false
                  binding.btnConfirm.isVisible = true
                  binding.etLayoutCode.isVisible = true
                  Log.e("olo", "Correct Code:$verifycationCode")

                  super.onCodeSent(verifycationCode, p1)
              }
          })
          .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()){task ->
                if (task.isSuccessful){
                    findNavController().navigate(R.id.navigation_home)
                  //  val user = task.result?.user
                }else{
                    Log.w("olo", "signInWithPhoneAuthCredential: ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException){
                        Log.e("olo", "signInWithPhoneAuthCredential:${task.exception.toString()} ")
                    }
                }
            }
    }
}