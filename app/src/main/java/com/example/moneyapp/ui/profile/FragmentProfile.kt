package com.example.moneyapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Base64.decode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.moneyapp.R
import com.example.moneyapp.api.models.NewImage
import com.example.moneyapp.api.services.UserService
import com.example.moneyapp.databinding.ProfileBinding
import com.example.moneyapp.ui.new_bill.afterTextChanged
import java.io.ByteArrayOutputStream
import java.util.*


class Profile : Fragment() {
    private lateinit var model: ProfileViewModel
    private var _binding: ProfileBinding? = null
    private val binding get() = _binding!!
    private var selectedImage: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model.user.observe(viewLifecycleOwner, Observer {
            val user = it ?: return@Observer
            Log.d("ProfileFragment", user.fullName.toString())
            binding.nickname.setText(user.fullName)
            binding.email.setText(user.emailAddress)
        })

        val name = binding.nickname
        val email = binding.email
        val loading = binding.loading
        val create = binding.changeProfile
        val upload = binding.upload
        val choose = binding.choose
        val apiService = UserService()

        apiService.getUser {
            if (it != null) {
                Log.d("Profile", "user loaded")
                name.setText(it.fullName)
                email.setText(it.emailAddress)
                val decodedString: ByteArray = android.util.Base64.decode(it.image, android.util.Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.profileImage.setImageBitmap(decodedByte)
            }
        }

        model.profileResult.observe(viewLifecycleOwner, Observer {
            val profileResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (profileResult.error != null) {
                showProfileFailed(profileResult.error)
            }
            if (profileResult.success) {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_nav_profile_to_nav_home)
            }

        })

        model.profilFormState.observe(viewLifecycleOwner, Observer {
            val profileState = it ?: return@Observer

            create.isEnabled = profileState.isDataValid

            if (profileState.emailError != null) {
                email.error = getString(profileState.emailError)
            }
        })

        email.afterTextChanged {
            model.emailDataChanged(
                    email.text.toString()
            )
        }

        create.setOnClickListener {
            loading.visibility = View.VISIBLE
            model.updateProfile(name.text.toString(), email.text.toString())
        }

        choose.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
            }
        }
        upload.setOnClickListener {
            val byteArrayOutputStream = ByteArrayOutputStream()
            selectedImage?.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

            val encodedString = android.util.Base64.encodeToString(
                byteArray,
                android.util.Base64.DEFAULT
            )
            val image = NewImage(
                newImage = encodedString,
            )
            apiService.editImage(image) {
                if (it != null) {
                    Log.d("ProfileViewModel", it)
                    if (it == "OK") {
                        Log.d("fragmentProfile", "obrazok sa uspesne nahral")
                        binding.profileImage.setImageBitmap(selectedImage)
                    } else {
                        // todo toast ze sa nepodarilo zmenit obrazok
                    }
                }
            }
        }


        val password = binding.profilePassword
        val newPassword = binding.newPassword
        val repeatPassword = binding.repeatPassword
        val update = binding.updatePassword
        binding.upload.isEnabled = false

        model.profileFormState.observe(viewLifecycleOwner, Observer {
            val profileState = it ?: return@Observer

            update.isEnabled = profileState.isDataValid

            if (profileState.newPasswordError != null) {
                newPassword.error = getString(profileState.newPasswordError)
            }
            if (profileState.repeatPasswordError != null) {
                repeatPassword.error = getString(profileState.repeatPasswordError)
            }
        })

        newPassword.afterTextChanged {
            model.passwordDataChanged(
                newPassword.text.toString(),
                repeatPassword.text.toString()
            )
        }

        repeatPassword.afterTextChanged {
            model.passwordDataChanged(
                newPassword.text.toString(),
                repeatPassword.text.toString()
            )
        }
        update.setOnClickListener {
            loading.visibility = View.VISIBLE
            model.updatePassword(password.text.toString(), newPassword.text.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    val imageUri: Uri = data?.data!!
                    selectedImage = ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            requireContext().contentResolver,
                            imageUri
                        )
                    )
                    binding.upload.isEnabled = true
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }


    private fun showProfileFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}



