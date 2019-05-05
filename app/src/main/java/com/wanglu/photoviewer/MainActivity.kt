package com.wanglu.photoviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gv.layoutManager = LinearLayoutManager(this)
        val adapter = MainAdapter(this)
        adapter.setNewData(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
        gv.adapter = adapter
    }
}
