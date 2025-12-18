package com.example.supersexiboys

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.supersexiboys.databinding.ActivityRegistroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding

    // Variable para guardar temporalmente qué eligió el usuario (por defecto null)
    private var avatarElegido: String? = null

    // Esto maneja la respuesta cuando vuelven de elegir la imagen
    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            avatarElegido = data?.getStringExtra("avatarSeleccionado")

            // Actualizamos la imagen visualmente en el Registro para que el usuario vea que eligió
            when (avatarElegido) {
                "avatar_uno" -> binding.imageViewPerfil.setImageResource(R.drawable.avatar_uno)
                "avatar_dos" -> binding.imageViewPerfil.setImageResource(R.drawable.avatar_dos)
                "avatar_tres" -> binding.imageViewPerfil.setImageResource(R.drawable.avatar_tres)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // 1. Al hacer click en la imagen vacía, vamos a elegir avatar
        binding.imageViewPerfil.setOnClickListener {
            val intent = Intent(this, SeleccionAvatarActivity::class.java)
            responseLauncher.launch(intent)
        }

        binding.guardarRegistroButton.setOnClickListener {
            val correo = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (avatarElegido == null) {
                Toast.makeText(this, "Por favor elige una imagen de perfil", Toast.LENGTH_SHORT).show()
            } else {
                crearUsuario(correo, password)
            }
        }
    }

    fun crearUsuario(correo: String, password: String) {
        auth.createUserWithEmailAndPassword(correo, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // El usuario se creó, en este momento le pegamos la foto al perfil de Firebase
                    val user = auth.currentUser

                    // Usamos setPhotoUri para guardar el STRING del nombre de la imagen
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(avatarElegido))
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { taskUpdate ->
                            if (taskUpdate.isSuccessful) {
                                // Solo cuando se guardó la foto, pasamos a la siguiente pantalla
                                val intent = Intent(this, MainActivity::class.java) // O HabitosActivity
                                startActivity(intent)
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(baseContext, "Error al registrar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}