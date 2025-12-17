package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.example.supersexiboys.databinding.ActivityLogueadoBinding

class LogueadoActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityLogueadoBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogueadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        // Botón para cerrar sesión
        binding.desloguearse.setOnClickListener {
            auth.signOut()
            val intentCambioAMain: Intent = Intent(context, MainActivity::class.java)
            startActivity(intentCambioAMain)
        }

        // Botón para ir a crear tarea
        binding.btnIrACrearTarea.setOnClickListener {
            val intent = Intent(context, CrearTareaActivity::class.java)
            startActivity(intent)
        }
    }
}