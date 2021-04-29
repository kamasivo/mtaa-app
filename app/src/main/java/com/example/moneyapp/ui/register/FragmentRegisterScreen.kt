package com.example.moneyapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.moneyapp.NavigationActivity
import com.example.moneyapp.R
import com.example.moneyapp.databinding.RegisterScreenBinding

class FragmentRegisterScreen : Fragment() {
    private var _binding: RegisterScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = binding.email
        val password = binding.password
        val fullName = binding.fullName
        val loading = binding.loading
        val register = binding.register

        val model: RegisterViewModel by viewModels()

        model.registerFormState.observe(viewLifecycleOwner, Observer {
            val registerState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = registerState.isDataValid

            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
            if (registerState.usernameError != null) {
                fullName.error = getString(registerState.usernameError)
            }
        })

        model.registerResult.observe(viewLifecycleOwner, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success) {
                Log.d("FragmentRegisterScreen", "Going to login screen")
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_fragmentRegisterScreen_to_fragmentLoginScreen)
//                val ide = Intent(this.context, NavigationActivity::class.java)
//                startActivity(ide)
            }

        })

        email.afterTextChanged {
            model.registerDataChanged(
                fullName.text.toString(),
                password.text.toString(),
                email.text.toString()
            )
        }

        fullName.afterTextChanged {
            model.registerDataChanged(
                fullName.text.toString(),
                password.text.toString(),
                email.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                model.registerDataChanged(
                    fullName.text.toString(),
                    password.text.toString(),
                    email.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        model.register(
                            fullName.text.toString(),
                            password.text.toString(),
                            email.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                model.register(fullName.text.toString(), password.text.toString(), email.text.toString())
            }

        }
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(this.context, errorString, Toast.LENGTH_SHORT).show()
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}