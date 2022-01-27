package com.github.yqyzxd.reflection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val method=Reflection()
            .on(MainActivity::class.java)
            .method("setTheme",Int::class.java)
            .get<Method>()


        println(method.toString())
    }


}