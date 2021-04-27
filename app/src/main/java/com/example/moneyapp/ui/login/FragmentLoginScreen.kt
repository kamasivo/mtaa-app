package com.example.moneyapp.ui.login

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
import com.example.moneyapp.databinding.LoginScreenBinding

class FragmentLoginScreen : Fragment() {
    private var _binding: LoginScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val register = binding.register

        username.setText("test@email.sk")
        password.setText("heslo")



        val model: LoginViewModel by viewModels()

        model.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid
            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        model.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success) {
                Log.d("FragmentLoginScreen", "go to main screen as loggin user");
                updateUiWithUser()
            }

        })

        username.afterTextChanged {
            model.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                model.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        model.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                model.login(username.text.toString(), password.text.toString())
            }

            register.setOnClickListener {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_fragmentLoginScreen_to_fragmentRegisterScreen)
            }
        }
    }

    private fun updateUiWithUser() {
        // redirect to main page
        val ide = Intent(this.context, NavigationActivity::class.java)
        startActivity(ide)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
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




