package com.wanglu.usercenter.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wanglu.usercenter.R
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity

/**
 * Created by WangLu on 2018/2/26.
 */
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnGetCode.setOnClickListener {
            snackbar(btnGetCode, "哈哈")
        }
        btnRegister.setOnClickListener {
            startActivity<TestActivity>("text" to "我是跳转")
        }
    }
}