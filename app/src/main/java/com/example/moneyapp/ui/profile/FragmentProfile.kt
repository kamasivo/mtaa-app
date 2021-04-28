package com.example.moneyapp.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.moneyapp.R
import com.example.moneyapp.databinding.ProfileBinding
import com.example.moneyapp.ui.new_bill.afterTextChanged

class Profile : Fragment() {
    private lateinit var model: ProfileViewModel
    private var _binding: ProfileBinding? = null
    private val binding get() = _binding!!
//    val args: FragmentProfileArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        create.setOnClickListener {
            loading.visibility = View.VISIBLE
            model.updateProfile(name.text.toString(), email.text.toString())
        }


        val password = binding.profilePassword
        val newPassword = binding.newPassword
        val repeatPassword = binding.repeatPassword
        val update = binding.updatePassword

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

        name.afterTextChanged {
            model.passwordDataChanged(
                    newPassword.text.toString(),
                    repeatPassword.text.toString()
            )
        }
        name.afterTextChanged {
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

    private fun showProfileFailed(errorString: String) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}



