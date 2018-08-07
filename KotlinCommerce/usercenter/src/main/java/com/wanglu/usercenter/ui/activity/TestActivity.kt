package com.wanglu.usercenter.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wanglu.usercenter.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        mTv.text = intent.getStringExtra("text")
    }
}
