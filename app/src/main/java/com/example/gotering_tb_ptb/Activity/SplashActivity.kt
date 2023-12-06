package com.example.gotering_tb_ptb.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.gotering_tb_ptb.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val iv_note = findViewById<LinearLayout>(R.id.imageView)
        iv_note.alpha = 0f
        iv_note.animate().setDuration(3000).alpha(1f).withEndAction {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }

}

