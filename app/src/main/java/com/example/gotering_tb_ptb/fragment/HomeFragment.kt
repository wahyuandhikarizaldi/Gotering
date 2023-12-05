package com.example.gotering_tb_ptb.fragment

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
import android.graphics.Color
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
import java.util.*

class HomeFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val code = "gotering"

        val docIdList = mutableListOf<String>()

        db.collection(code).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    docIdList.add(document.id)
                }
                // Do something with the list of document IDs (docIdList)
                showDocumentsInScrollView(docIdList)
            }
            .addOnFailureListener { exception ->
            // Handle any errors here
            }
    }

    private fun showDocumentsInScrollView(docIdList: List<String>) {
        val code = "gotering"
        val linearLayout = view?.findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout?.removeAllViews() // Remove any existing TextViews

        for (docId in docIdList) {
            db.collection(code).document(docId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val productName = documentSnapshot.getString("name")
                    val productDisprice = documentSnapshot.getString("disprice")
                    val productImageUrl = documentSnapshot.getString("img")
                    val productPrice = documentSnapshot.getString("price")

                    val productLayout = LinearLayout(requireContext())
                    productLayout.orientation = LinearLayout.HORIZONTAL
                    productLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    productLayout.setPadding(20, 20, 20, 20)

                    // Add product image to layout
                    val productImage = ImageView(requireContext())
                    productImage.layoutParams = LinearLayout.LayoutParams(
                        200,
                        200
                    )

                    // Load image using Glide
                    Glide.with(this)
                        .load(productImageUrl)
                        .into(productImage)

                    productLayout.addView(productImage)

                    // Add product to layout
                    val productDetailsLayout = LinearLayout(requireContext())
                    productDetailsLayout.orientation = LinearLayout.VERTICAL
                    productDetailsLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    productDetailsLayout.setPadding(20, 0, 0, 0)

                    val nametextView = TextView(requireContext())
                    nametextView.text = productName
                    nametextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    nametextView.setTextColor(Color.BLACK)
                    productDetailsLayout.addView(nametextView)

                    val dispricetextview = TextView(requireContext())
                    dispricetextview.text = productDisprice
                    dispricetextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    dispricetextview.setTextColor(Color.BLACK)
                    dispricetextview.gravity = Gravity.CENTER_VERTICAL
                    productDetailsLayout.addView(dispricetextview)

                    productLayout.addView(productDetailsLayout)

                    // Add OnClickListener to product layout
                    productLayout.setOnClickListener {
                        val intent = Intent(requireActivity(), com.example.gotering_tb_ptb.Activity.TambahMenuActivity::class.java)
                        intent.putExtra("productName", productName)
                        intent.putExtra("productPrice", productPrice)
                        intent.putExtra("productDisprice", productDisprice)
                        intent.putExtra("productImageUrl", productImageUrl)
                        startActivity(intent)
                    }

                    linearLayout?.addView(productLayout)
                }
        }
    }

}
