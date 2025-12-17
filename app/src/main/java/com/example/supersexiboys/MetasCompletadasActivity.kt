package com.example.supersexiboys

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.supersexiboys.basededatos.MetaDao
import com.example.supersexiboys.basededatos.MetaDataBase
import com.example.supersexiboys.databinding.ActivityMetasBinding
import com.example.supersexiboys.databinding.ActivityMetasCompletadasBinding

class MetasCompletadasActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMetasCompletadasBinding
    val context: Context = this

    private lateinit var metaDao: MetaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMetasCompletadasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            MetaDataBase::class.java,
            MetaDataBase.DATABASE_NAME
        ).build()


        metaDao = db.metaDao()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val metas = metaDao.getCompleted()

    }
}