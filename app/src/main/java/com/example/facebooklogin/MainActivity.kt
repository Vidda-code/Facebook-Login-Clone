package com.example.facebooklogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.facebooklogin.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase Call
        firebaseAuth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this)


        //On click the Login Button
        binding.btnLogin.setOnClickListener { logIn() }

        //On click the Forget Password Button
        binding.tvForgotPassword.setOnClickListener { forgotPass() }

        //On click the Create New Account Button
        binding.btnNewAccount.setOnClickListener { newAccount() }

        //On click the Signup Button
        binding.btnSignup.setOnClickListener { signUp() }

        //On click the Reset Password Button
        binding.btnReset.setOnClickListener { resetPass() }

        //On click the SignIn Button
        binding.btnSignin.setOnClickListener { signIn() }

    }

    //Function for the Login Button
    private fun logIn() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        } else {
            Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show()
        }
    }

    //Function for the SignUp Button
    private fun signUp() {

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPass = binding.etConfirmPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
            if (password == confirmPass) {

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Password not a match", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show()

        }
    }

    //Function for the Forget Passqord Button
    private fun forgotPass() {

        binding.etPassword.visibility = View.GONE
        binding.btnLogin.visibility = View.GONE
        binding.tvForgotPassword.visibility = View.GONE
        binding.llLine.visibility = View.GONE
        binding.btnNewAccount.visibility = View.GONE
        binding.btnReset.visibility = View.VISIBLE
        binding.btnSignin.visibility = View.VISIBLE
        binding.llLine.visibility = View.VISIBLE

    }

    //Function for the Reset password Button
    private fun resetPass() {
        val email = binding.etEmail.text.toString()
        if (email.isNotEmpty()) {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password reset email sent successfully
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    binding.btnReset.visibility = View.GONE
                    binding.etPassword.visibility = View.VISIBLE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.tvForgotPassword.visibility = View.VISIBLE
                    binding.llLine.visibility = View.VISIBLE
                    binding.btnNewAccount.visibility = View.VISIBLE
                    binding.btnSignin.visibility = View.GONE
                } else {
                    // Failed to send password reset email
                    Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            Toast.makeText(this,"Enter Email", Toast.LENGTH_LONG).show()
        }
    }

    //Function for the Create New Account Button
    private fun newAccount() {

        binding.btnLogin.visibility = View.GONE
        binding.btnSignup.visibility = View.VISIBLE
        binding.etConfirmPassword.visibility = View.VISIBLE
        binding.tvForgotPassword.visibility = View.GONE
        binding.btnNewAccount.visibility = View.GONE
        binding.btnSignin.visibility = View.VISIBLE

    }

    //Function for the SignIn Button
    private fun signIn() {

        binding.etConfirmPassword.visibility = View.GONE
        binding.btnSignup.visibility = View.GONE
        binding.btnSignin.visibility = View.GONE
        binding.btnReset.visibility = View.GONE
        binding.etPassword.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.VISIBLE
        binding.tvForgotPassword.visibility = View.VISIBLE
        binding.btnNewAccount.visibility = View.VISIBLE
    }

    //On starting function to check if the user is logged in
    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}