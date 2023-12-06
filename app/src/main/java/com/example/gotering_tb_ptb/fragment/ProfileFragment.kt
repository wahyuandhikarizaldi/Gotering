package com.example.gotering_tb_ptb.fragment

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gotering_tb_ptb.Activity.MainActivity
import com.example.gotering_tb_ptb.R
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import android.view.View
import android.view.Gravity
import android.view.MotionEvent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.widget.ScrollView
import com.bumptech.glide.Glide
import com.example.gotering_tb_ptb.Activity.credentials
import java.util.*

class ProfileFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentActivity = activity

        if (parentActivity != null && parentActivity is MainActivity) {
            val username = credentials.myusername
            val password = credentials.mypassword
            val img = credentials.myimg

            val usernameview = view?.findViewById<TextView>(R.id.nameview)
            usernameview?.text = username

            val imageview = view?.findViewById<ImageView>(R.id.image_view)
            imageview?.let { imageView ->
                Glide.with(this)
                    .load(img)
                    .into(imageView)
            }

            val exitbutton = view?.findViewById<Button>(R.id.exit_button)
            exitbutton?.setOnClickListener {
                val intent = Intent(requireActivity(), com.example.gotering_tb_ptb.Activity.LoginActivity::class.java)
                startActivity(intent)
            }

            val tentangbutton = view?.findViewById<Button>(R.id.tentang_button)
            tentangbutton?.setOnClickListener {
                val intent = Intent(requireActivity(),com.example.gotering_tb_ptb.Activity.TentangKamiActivity::class.java)
                startActivity(intent)
            }

            val rekapButton = view?.findViewById<Button>(R.id.transaksi_button)
            rekapButton?.setOnClickListener {
                val intent = Intent(requireActivity(),com.example.gotering_tb_ptb.Activity.RekapActivity::class.java)
                startActivity(intent)
            }

            val editButton = view?.findViewById<Button>(R.id.edit_button)
            editButton?.setOnClickListener {
                val intent = Intent(requireActivity(),com.example.gotering_tb_ptb.Activity.EditActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val imageview = view?.findViewById<ImageView>(R.id.image_view)
        imageview?.let {
            Glide.with(this)
                .load(credentials.myimg)
                .into(it)
        }

    }


}
