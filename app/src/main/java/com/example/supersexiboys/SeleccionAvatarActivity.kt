package com.example.supersexiboys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.supersexiboys.databinding.ActivitySeleccionAvatarBinding

class SeleccionAvatarActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeleccionAvatarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgAvatar1.setOnClickListener { devolverSeleccion("avatar_uno") }
        binding.imgAvatar2.setOnClickListener { devolverSeleccion("avatar_dos") }
        binding.imgAvatar3.setOnClickListener { devolverSeleccion("avatar_tres") }
    }

    private fun devolverSeleccion(nombreAvatar: String) {
        val intent = Intent()
        intent.putExtra("avatarSeleccionado", nombreAvatar)
        setResult(RESULT_OK, intent)
        finish() // Cierra esta pantalla y vuelve al registro
    }
}