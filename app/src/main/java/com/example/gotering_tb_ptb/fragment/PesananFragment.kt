package com.example.gotering_tb_ptb.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gotering_tb_ptb.R
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.util.TypedValue
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import android.content.Context
import android.view.Gravity
import android.util.Log
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gotering_tb_ptb.Data.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PesananFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pesanan, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = view.findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout.removeAllViews() // Remove any existing TextViews

        val productsList = loadProducts()

        var productTotal = 0.0

        for (product in productsList) {

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
                .load(product.productImageUrl)
                .into(productImage)

            productLayout.addView(productImage)

            // Add product to layout
            val productDetailsLayout = LinearLayout(requireContext())
            productDetailsLayout.orientation = LinearLayout.VERTICAL
            productDetailsLayout.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            ) // Set weight to 1

            productDetailsLayout.setPadding(20, 0, 0, 0)

            val nametextView = TextView(requireContext())
            nametextView.text = product.productName
            nametextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            nametextView.setTextColor(Color.BLACK)
            productDetailsLayout.addView(nametextView)

            val dispricetextview = TextView(requireContext())
            dispricetextview.text = product.productDisprice
            dispricetextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            dispricetextview.setTextColor(Color.BLACK)
            dispricetextview.gravity = Gravity.CENTER_VERTICAL
            productDetailsLayout.addView(dispricetextview)

            // Create a horizontal LinearLayout to hold the TextView and buttons
            val horizontalLayout = LinearLayout(requireContext())
            horizontalLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            horizontalLayout.orientation = LinearLayout.HORIZONTAL

            val tombolkurang = Button(requireContext())
            tombolkurang.layoutParams = LinearLayout.LayoutParams(
                70,
                70
            )
            tombolkurang.setBackgroundResource(R.drawable.baseline_kurang_24)
            horizontalLayout.addView(tombolkurang)

            val angkajumlah = TextView(requireContext())
            angkajumlah.text = product.productJumlah
            angkajumlah.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            angkajumlah.setTextColor(Color.BLACK)
            angkajumlah.gravity = Gravity.CENTER_VERTICAL
            horizontalLayout.addView(angkajumlah)

            val tomboltambah = Button(requireContext())
            tomboltambah.layoutParams = LinearLayout.LayoutParams(
                70,
                70
            )
            tomboltambah.setBackgroundResource(R.drawable.baseline_tambah_24)
            horizontalLayout.addView(tomboltambah)

            var jumlah = product.productJumlah.toInt()

            tombolkurang.setOnClickListener {
                // Ensure angkajumlah doesn't go below 0 when tombolkurang is clicked
                if (jumlah > 1) {
                    jumlah--
                    angkajumlah.text = jumlah.toString()

                    // Calculate total price and update the UI
                    val total = product.productPrice.toInt() * jumlah

                    // Load bookmarks from SharedPreferences and update the bookmark list and status map
                    val productsList = loadProducts()
                    val bookmarkStatusMap = productsList.associateBy({ it.productName }, { true }).toMutableMap()

                    val bookmarkedProduct = Product(product.productName, product.productImageUrl, product.productPrice, product.productDisprice, jumlah.toString(), total.toString())
                    productsList.add(bookmarkedProduct)
                    bookmarkStatusMap[product.productName] = true
                    // Save the updated bookmarked items to SharedPreferences
                    saveBookmarks(productsList)

                } else {
                    // Remove the selected product from the list
                    productsList.remove(product)

                    // Convert the updated list back to a JSON string and save it to SharedPreferences
                    val updatedProductsJsonString = Gson().toJson(productsList)
                    val sharedPreferences = requireContext().getSharedPreferences("products", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("productsList", updatedProductsJsonString)
                    editor.apply()

                    // Remove views associated with the deleted product from the layout
                    linearLayout?.removeView(productLayout)
                }
                updateUI()
            }

            tomboltambah.setOnClickListener {
                // Increment angkajumlah by 1 when tomboltambah is clicked
                jumlah++
                // Update the UI with the new quantity
                angkajumlah.text = jumlah.toString()

                // Calculate total price and update the UI
                val total = product.productPrice.toInt() * jumlah

                // Load bookmarks from SharedPreferences and update the bookmark list and status map
                val productsList = loadProducts()
                val bookmarkStatusMap = productsList.associateBy({ it.productName }, { true }).toMutableMap()

                val bookmarkedProduct = Product(product.productName, product.productImageUrl, product.productPrice, product.productDisprice, jumlah.toString(), total.toString())
                productsList.add(bookmarkedProduct)
                bookmarkStatusMap[product.productName] = true
                // Save the updated bookmarked items to SharedPreferences
                saveBookmarks(productsList)

                updateUI()

            }

            productDetailsLayout.addView(horizontalLayout)

            productLayout.addView(productDetailsLayout)

            val tombolhapus = Button(requireContext())
            tombolhapus.layoutParams = LinearLayout.LayoutParams(
                70,
                70
            )
            tombolhapus.setBackgroundResource(R.drawable.baseline_delete_24)
            productLayout.addView(tombolhapus)

            tombolhapus.setOnClickListener{
                // Remove the selected product from the list
                productsList.remove(product)

                // Convert the updated list back to a JSON string and save it to SharedPreferences
                val updatedProductsJsonString = Gson().toJson(productsList)
                val sharedPreferences = requireContext().getSharedPreferences("products", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("productsList", updatedProductsJsonString)
                editor.apply()

                // Remove views associated with the deleted product from the layout
                linearLayout?.removeView(productLayout)

                updateUI()
            }

            linearLayout?.addView(productLayout)

            val productPrice = product.productPrice.toIntOrNull() ?: 0 // Assuming productPrice is a String

            val total = productPrice * jumlah // Calculate the total for this specific product

            productTotal += total

            productLayout.setOnClickListener {
                val intent = Intent(requireActivity(), com.example.gotering_tb_ptb.Activity.TambahMenuActivity::class.java)
                intent.putExtra("productName", product.productName)
                intent.putExtra("productPrice", product.productPrice)
                intent.putExtra("productDisprice", product.productDisprice)
                intent.putExtra("productImageUrl", product.productImageUrl)
                startActivity(intent)
            }

        }

        // Initialize UI elements
        val subtotalview = view.findViewById<TextView>(R.id.subtotal)
        val delivview = view.findViewById<TextView>(R.id.deliv)
        val discountview = view.findViewById<TextView>(R.id.discount)
        val totalview = view.findViewById<TextView>(R.id.total)

        // Set the total to the TextView
        subtotalview.text = "$productTotal"
        val orderSubtotal = productTotal.toString()

        val deliv = 15000
        delivview.text = "$deliv"

        val disc = productTotal*0.1
        discountview.text = "$disc"

        val totalsemua = productTotal+deliv-disc
        totalview.text = "$totalsemua"

        val checkout_button = view.findViewById<Button>(R.id.checkout)

        checkout_button.setOnClickListener {
            val editName = view.findViewById<EditText>(R.id.editnamaText)
            val orderName: String = editName.text.toString()

            val editDeliver = view.findViewById<EditText>(R.id.editalamatText)
            val orderDeliver: String = editDeliver.text.toString()

            if (orderName.isNotEmpty() && orderDeliver.isNotEmpty()) {

                val deliv = 15000.0
                val orderDeliv = deliv.toString()

                val disc = productTotal * 0.1
                val orderDisc = disc.toString()

                val totalsemua = productTotal + deliv - disc
                val orderTotal = totalsemua.toString()

                Log.d("orderTotal", orderTotal)
                Log.d("orderDisc", orderDisc)

                val orderID = generateRandomOrderId()

                val orderDate = getCurrentDate()

                val productsList: ArrayList<Product> = loadProducts()

                val intent = Intent(
                    requireActivity(),
                    com.example.gotering_tb_ptb.Activity.KonfirmasiActivity::class.java
                )
                intent.putExtra("orderID", orderID)
                intent.putExtra("orderName", orderName)
                intent.putExtra("orderDate", orderDate)
                intent.putExtra("orderDeliver", orderDeliver)
                intent.putExtra("orderSubtotal", orderSubtotal)
                intent.putExtra("orderDeliv", orderDeliv)
                intent.putExtra("orderDisc", orderDisc)
                intent.putExtra("orderTotal", orderTotal)
                for ((index, product) in productsList.withIndex()) {
                    intent.putExtra("productName$index", product.productName)
                    intent.putExtra("productJumlah$index", product.productJumlah)
                    intent.putExtra("productPrice$index", product.productPrice)
                }
                startActivity(intent)
            } else{
                val message = "Harap masukkan nama dan alamat tujuan!"
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

            updateUI()
        }
    }

    private fun generateRandomOrderId(): String {
        val random = java.util.Random()
        val orderId = StringBuilder()
        repeat(9) {
            orderId.append(random.nextInt(10)) // Append a random digit (0-9)
        }
        return orderId.toString()
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }


    private fun loadProducts(): ArrayList<Product> {
        val sharedPreferences = requireContext().getSharedPreferences("products", Context.MODE_PRIVATE)
        val productsJson = sharedPreferences.getString("productsList", null)
        return if (productsJson != null) {
            Gson().fromJson(productsJson, object : TypeToken<ArrayList<Product>>() {}.type)
        } else {
            ArrayList()
        }
    }


    private fun saveBookmarks(productsList: MutableList<Product>) {
        val jsonString = Gson().toJson(productsList)

        // Load the existing bookmarks from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("products", Context.MODE_PRIVATE)
        val existingJsonString = sharedPreferences.getString("productsList", null)

        // If there are existing bookmarks, update them with any new ones
        if (existingJsonString != null) {
            val existingBookmarks = Gson().fromJson(existingJsonString, Array<Product>::class.java).toMutableList()
            for (newBookmark in productsList) {
                val existingBookmarkIndex = existingBookmarks.indexOfFirst { it.productName == newBookmark.productName }
                if (existingBookmarkIndex >= 0) {
                    val existingBookmark = existingBookmarks[existingBookmarkIndex]
                    val updatedBookmark = Product(
                        existingBookmark.productName,
                        newBookmark.productImageUrl,
                        newBookmark.productPrice,
                        newBookmark.productDisprice,
                        newBookmark.productJumlah,
                        newBookmark.productTotal
                    )
                    existingBookmarks[existingBookmarkIndex] = updatedBookmark
                } else {
                    existingBookmarks.add(newBookmark)
                }
            }
            val updatedJsonString = Gson().toJson(existingBookmarks)
            sharedPreferences.edit().putString("productsList", updatedJsonString).apply()
        } else {
            // Otherwise, just save the new bookmarks to SharedPreferences
            sharedPreferences.edit().putString("productsList", jsonString).apply()
        }
    }

    public fun updateUI() {
        // Invalidate the view to force a redraw if needed

        val linearLayout = view?.findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout?.invalidate()

        val productsList = loadProducts()

        var productTotal = 0.0


        for (product in productsList) {

            val productLayout = LinearLayout(requireContext())
            productLayout.invalidate()

            val angkajumlah = TextView(requireContext())
            angkajumlah.text = product.productJumlah

            val productPrice = product.productPrice.toIntOrNull() ?: 0
            var jumlah = product.productJumlah.toInt()

            val total = productPrice * jumlah // Calculate the total for this specific product

            productTotal += total

        }

        val subtotalview = view?.findViewById<TextView>(R.id.subtotal)
        val delivview = view?.findViewById<TextView>(R.id.deliv)
        val discountview = view?.findViewById<TextView>(R.id.discount)
        val totalview = view?.findViewById<TextView>(R.id.total)

        // Set the total to the TextView
        subtotalview?.text = "$productTotal"

        val deliv = 15000
        delivview?.text = "$deliv"

        val disc = productTotal*0.1
        discountview?.text = "$disc"

        val totalsemua = productTotal+deliv-disc
        totalview?.text = "$totalsemua"

        subtotalview?.invalidate()
        delivview?.invalidate()
        discountview?.invalidate()
        totalview?.invalidate()

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

}