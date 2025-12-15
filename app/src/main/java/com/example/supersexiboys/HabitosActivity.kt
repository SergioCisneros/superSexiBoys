package com.example.supersexiboys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supersexiboys.databinding.ActivityHabitosBinding

class HabitosActivity : AppCompatActivity() {
    val context: Context = this
    private lateinit var binding: ActivityHabitosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHabitosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.nuevoHabitoButton.setOnClickListener {
            val cambioAAgregarHabito: Intent = Intent(context, AgregandoHabitosActivity::class.java)
            startActivity(cambioAAgregarHabito)
        }
    }
}