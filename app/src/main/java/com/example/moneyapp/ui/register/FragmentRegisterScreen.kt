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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moneyapp.NavigationActivity
import com.example.moneyapp.databinding.FragmentRegisterScreenBinding

class FragmentRegisterScreen : Fragment() {
    private var _binding: FragmentRegisterScreenBinding? = null
    private lateinit var registerViewModel: RegisterViewModel

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = binding.email
        val password = binding.password
        val fullName = binding.fullName
        val loading = binding.loading
        val register = binding.register

        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(viewLifecycleOwner, Observer {
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

        registerViewModel.registerResult.observe(viewLifecycleOwner, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success) {
                Log.d("FragmentRegisterScreen", "Going to main screen")
                updateUiWithUser()
//                setResult(Activity.RESULT_OK)
//                finish()
            }

        })

        email.afterTextChanged {
            registerViewModel.registerDataChanged(
                fullName.text.toString(),
                password.text.toString(),
                email.text.toString()
            )
        }

        fullName.afterTextChanged {
            registerViewModel.registerDataChanged(
                fullName.text.toString(),
                password.text.toString(),
                email.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    fullName.text.toString(),
                    password.text.toString(),
                    email.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            fullName.text.toString(),
                            password.text.toString(),
                            email.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                registerViewModel.register(fullName.text.toString(), password.text.toString(), email.text.toString())
            }

        }
    }
    private fun updateUiWithUser() {
        // redirect to main page
        val ide = Intent(this.context, NavigationActivity::class.java)
        startActivity(ide)
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