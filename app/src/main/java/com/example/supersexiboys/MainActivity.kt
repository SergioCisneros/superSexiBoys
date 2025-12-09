package com.example.supersexiboys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase

private lateinit var auth: FirebaseAuth
private lateinit var binding: MainActivityBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentUser = auth.currentUser
        if (currentUser != null){
            val intentUsurioLog = Intent(this, )
            startActivity(intentUsurioLog)
        }

        binding.buttonLogin.setOnClickListener{
            val correo =  binding.editEmail.text.toString
    }

    fun loginUsuario(
        correo: String, password: String{}
    ){
        auth.signInWithEmailAndPassword(correo,password).addonCompleteListener{
            task -> if (task.isSuccessful){
                // Nuestro Usuario se Logeo Correctamente
                val intentBloqup = Intent(pa)
            } else {
            Toast.makeText(
                baseContext,
                "No pudo loguarse",
                Toast.LENGTH_SHORT,
            ).show()
            }
        }
        }
    }
}