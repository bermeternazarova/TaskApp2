package com.example.taskapp2.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentProfileBinding
import com.example.taskapp2.extencions.loadImage
import com.example.taskapp2.utils.Preference
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val getContent : ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()){imageUri:Uri?->
       //binding.circleImage.load(imageUri)
            binding.circleImage.loadImage(imageUri.toString())

           Preference(requireContext()).saveImageUri(imageUri.toString())
    }
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnProfile.alpha = 0f
        binding.btnProfile.animate().apply {
            duration = 2000
            alpha(1f)
        }

        binding.circleImage.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.circleImage.loadImage(Preference(requireContext()).getImageUri())

        binding.btnProfile.setOnClickListener {
            Preference(requireContext()).saveEditText(binding.etProfile.text.toString())
        }
          binding.tvProfile.text = Preference(requireContext()).getEditText()

    }
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val option = arrayOf("No", "Yes")
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Are you sure you want to log out of your account?").setItems(option,
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (i==0){
                    dialogInterface.dismiss()
                }else if (i==1){
                    auth.signOut()
                    findNavController().navigate(R.id.authenticationFragment)
                }
            })
            .create()
            .show()
        return super.onOptionsItemSelected(item)
    }
}