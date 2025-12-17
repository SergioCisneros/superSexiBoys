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


    companion object {
        val DATABASE_NAME: String = "USER_DATABASE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMetasCompletadasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ejemploDataBase = Room.databaseBuilder(
            context, MetaDataBase::class.java,
            com.example.supersexiboys.AgregarMetaActivity.Companion.DATABASE_NAME
        ).build()

        metaDao = ejemploDataBase.metaDao()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val metas = metaDao.getCompleted()

    }
}