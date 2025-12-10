package com.example.supersexiboys

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

import com.example.supersexiboys.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentUser = auth.currentUser
        if (currentUser != null){
            val intentUsuarioLogueado = Intent(context, LogueadoActivity::class.java)
            startActivity(intentUsuarioLogueado)
        }

        binding.loginButton.setOnClickListener {
            val correo = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            loginUsuario(correo, password)
        }

        binding.registroButton.setOnClickListener {
            val intentUsuarioPorRegistrar = Intent(context, RegistroActivity::class.java)
            startActivity(intentUsuarioPorRegistrar)
        }


    }

    fun loginUsuario(
        correo: String, password: String
    ){
        auth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    // Nuestro Usuario se Logueo Correctamente
                    val intentLogueado = Intent(this, LogueadoActivity::class.java)
                    startActivity(intentLogueado)
                } else {
                    // Nuestro usuario no se pudo Loguear
                    Toast.makeText(
                        baseContext,
                        "No pudo loguearse",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }
}