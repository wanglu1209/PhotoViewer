package com.wanglu.photoviewer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.wanglu.photoviewer.R.id.btn
import com.wanglu.photoviewer.R.id.btn2
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btn2.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }
}