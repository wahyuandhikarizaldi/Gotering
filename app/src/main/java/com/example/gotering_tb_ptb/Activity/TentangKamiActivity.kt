package com.example.gotering_tb_ptb.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.gotering_tb_ptb.R
import androidx.appcompat.widget.Toolbar
class TentangKamiActivity : AppCompatActivity() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_kami)

        // Set the toolbar as the action bar for the activity
        val toolbar = findViewById<Toolbar>(R.id.toolbar4)
        toolbar.title = ""

        setSupportActionBar(toolbar)

        // Add a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}