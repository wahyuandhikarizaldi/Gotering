package com.example.gotering_tb_ptb.Activity

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gotering_tb_ptb.R
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import android.view.View
import android.view.Gravity
import android.view.MotionEvent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.widget.ScrollView
import com.bumptech.glide.Glide
import java.util.*
import androidx.appcompat.app.AppCompatActivity

object credentials {
    var myusername: String = ""
    var mypassword: String = ""
    var myimg: String = "https://th.bing.com/th/id/OIP.6I8GSRo273wTecfBSQ7k5wHaHa?w=161&h=180&c=7&r=0&o=5&dpr=2&pid=1.7"
}

class LoginActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameview = findViewById<EditText>(R.id.username)
        val passwordview = findViewById<EditText>(R.id.password)
        val submitButton = findViewById<Button>(R.id.submit_button)

        submitButton.setOnClickListener {
            val username = usernameview.text.toString()
            val password = passwordview.text.toString()

            if (username.isNotEmpty()) {
                // Check Firestore for the user document
                val docRef = db.collection("akun").document(username)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val actualPassword = document.getString("password")
                            val img = document.getString("img") ?: "https://th.bing.com/th/id/OIP.6I8GSRo273wTecfBSQ7k5wHaHa?w=161&h=180&c=7&r=0&o=5&dpr=2&pid=1.7"
                            if (actualPassword == password) {
                                credentials.myusername = username
                                credentials.mypassword = password
                                credentials.myimg = img
                                val intent = Intent(this, com.example.gotering_tb_ptb.Activity.MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Wrong password
                                Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // User not found
                            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter username and password!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
