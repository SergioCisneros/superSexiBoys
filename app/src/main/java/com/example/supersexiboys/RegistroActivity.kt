package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supersexiboys.databinding.ActivityMainBinding
import com.example.supersexiboys.databinding.ActivityRegistroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistroActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.guardarRegistroButton.setOnClickListener {
            val correo = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            crearUsuario(correo, password)
        }

    }

    fun crearUsuario(
        correo: String,
        password: String
    ){
        auth.createUserWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    println("ya se registro")
                    val intentUsuarioPorLoguear = Intent(context, MainActivity::class.java)
                    startActivity(intentUsuarioPorLoguear)
                } else{
                    // No se pudo crear usuario
                    Toast.makeText(
                        baseContext,
                        "No pudo registrarse",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }

    }
}