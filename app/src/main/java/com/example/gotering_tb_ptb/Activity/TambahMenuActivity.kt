package com.example.gotering_tb_ptb.Activity

import com.example.gotering_tb_ptb.Data.Product
import android.content.Intent
import android.widget.TextView
import android.widget.Button
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gotering_tb_ptb.R

class TambahMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT_NAME = "productName"
        const val EXTRA_PRODUCT_IMAGE_URL = "productImageUrl"
        const val EXTRA_PRODUCT_DISPRICE = "productDisprice"
        const val EXTRA_PRODUCT_PRICE = "productPrice"
    }

    private lateinit var namaayamgeprek: TextView
    private lateinit var ayamgeprek: ImageView
    private lateinit var hargaayamgeprek: TextView
    private lateinit var angkajumlah: TextView
    private lateinit var totalhargayamgeprek: TextView
    private lateinit var tombolkurang: Button
    private lateinit var tomboltambah: Button
    private lateinit var Tambahkan: Button

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
        setContentView(R.layout.activity_tambah_menu)

        // Set the toolbar as the action bar for the activity
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        toolbar.title = ""

        setSupportActionBar(toolbar)

        // Add a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get product details from intent extras
        val productName: String = intent.getStringExtra(EXTRA_PRODUCT_NAME) ?: ""
        val productImageUrl: String = intent.getStringExtra(EXTRA_PRODUCT_IMAGE_URL) ?: ""
        val productPrice: String = intent.getStringExtra(EXTRA_PRODUCT_PRICE) ?: ""
        val productDisprice: String = intent.getStringExtra(EXTRA_PRODUCT_DISPRICE) ?: ""

        // Initialize UI elements
        namaayamgeprek = findViewById(R.id.namaayamgeprek)
        ayamgeprek = findViewById(R.id.ayamgeprek)
        hargaayamgeprek = findViewById(R.id.hargaayamgeprek)
        angkajumlah = findViewById(R.id.angkajumlah)
        totalhargayamgeprek = findViewById(R.id.totalhargayamgeprek)
        tombolkurang = findViewById(R.id.tombolkurang)
        tomboltambah = findViewById(R.id.tomboltambah)
        Tambahkan = findViewById(R.id.Tambahkan)

        // Set product name to text view
        namaayamgeprek.text = productName
        hargaayamgeprek.text = productDisprice

        // Load product image using Glide
        Glide.with(this)
            .load(productImageUrl)
            .into(ayamgeprek)

        val productPrices: Double = productPrice?.toDoubleOrNull() ?: 0.0

        // Load products list from SharedPreferences
        val productsList = loadProducts()

        val productNameToFind = productName

        val foundProduct = productsList.find { it.productName == productNameToFind }

        val defaultJumlah = "1" // Default value for productJumlah

        val productJumlah = foundProduct?.productJumlah ?: defaultJumlah

        angkajumlah.text = productJumlah
        var jumlah = productJumlah.toInt()
        // Calculate total price and update the UI
        val total = productPrices * productJumlah.toInt()
        val totalValue = total.toString()
        val split = totalValue.split(".")
        val formattedTotal = StringBuilder()

        // Append "Rp " prefix
        formattedTotal.append("Rp")

        // If there's a decimal part, handle it separately
        formattedTotal.append(split[0])

        // Insert dots for thousand separators
        val nonCurrencyPart = formattedTotal.substring(3) // Remove the "Rp" prefix
        val formattedNonCurrencyPart = nonCurrencyPart.reversed().chunked(3).joinToString(".").reversed()
        formattedTotal.replace(3, formattedTotal.length, formattedNonCurrencyPart)

        totalhargayamgeprek.text = formattedTotal.toString()


        tomboltambah.setOnClickListener {
            // Increment angkajumlah by 1 when tomboltambah is clicked
            jumlah++
            // Update the UI with the new quantity
            angkajumlah.text = jumlah.toString()

            // Calculate total price and update the UI
            val total = productPrices * jumlah
            val totalValue = total.toString()
            val split = totalValue.split(".")
            val formattedTotal = StringBuilder()

            // Append "Rp " prefix
            formattedTotal.append("Rp")

            // If there's a decimal part, handle it separately
            formattedTotal.append(split[0])

            // Insert dots for thousand separators
            val nonCurrencyPart = formattedTotal.substring(3) // Remove the "Rp" prefix
            val formattedNonCurrencyPart = nonCurrencyPart.reversed().chunked(3).joinToString(".").reversed()
            formattedTotal.replace(3, formattedTotal.length, formattedNonCurrencyPart)

            totalhargayamgeprek.text = formattedTotal.toString()

            // Load bookmarks from SharedPreferences and update the bookmark list and status map
            val productsList = loadProducts()
            val bookmarkStatusMap = productsList.associateBy({ it.productName }, { true }).toMutableMap()

            val bookmarkedProduct = Product(productName, productImageUrl, productPrice, productDisprice, jumlah.toString(), total.toString())
            productsList.add(bookmarkedProduct)
            bookmarkStatusMap[productName] = true
            // Save the updated bookmarked items to SharedPreferences
            saveBookmarks(productsList)

        }

        tombolkurang.setOnClickListener {
            // Ensure angkajumlah doesn't go below 0 when tombolkurang is clicked
            if (jumlah > 1) {
                jumlah--
                angkajumlah.text = jumlah.toString()

                // Calculate total price and update the UI
                val total = productPrices * jumlah
                val totalValue = total.toString()
                val split = totalValue.split(".")
                val formattedTotal = StringBuilder()

                // Append "Rp " prefix
                formattedTotal.append("Rp ")

                formattedTotal.append(split[0])

                // Insert dots for thousand separators
                val nonCurrencyPart = formattedTotal.substring(3) // Remove the "Rp " prefix
                val formattedNonCurrencyPart = nonCurrencyPart.reversed().chunked(3).joinToString(".").reversed()
                formattedTotal.replace(3, formattedTotal.length, formattedNonCurrencyPart)

                totalhargayamgeprek.text = formattedTotal.toString()

                // Load bookmarks from SharedPreferences and update the bookmark list and status map
                val productsList = loadProducts()
                val bookmarkStatusMap = productsList.associateBy({ it.productName }, { true }).toMutableMap()

                val bookmarkedProduct = Product(productName, productImageUrl, productPrice, productDisprice, jumlah.toString(), total.toString())
                productsList.add(bookmarkedProduct)
                bookmarkStatusMap[productName] = true
                // Save the updated bookmarked items to SharedPreferences
                saveBookmarks(productsList)

            }
        }

        Tambahkan.setOnClickListener {

            // Load bookmarks from SharedPreferences and update the bookmark list and status map
            val productsList = loadProducts()
            val bookmarkStatusMap = productsList.associateBy({ it.productName }, { true }).toMutableMap()

            val bookmarkedProduct = Product(productName, productImageUrl, productPrice, productDisprice, jumlah.toString(), total.toString())
            productsList.add(bookmarkedProduct)
            bookmarkStatusMap[productName] = true
            // Save the updated bookmarked items to SharedPreferences
            saveBookmarks(productsList)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("updatePesananFragment", true)
            startActivity(intent)

        }

    }

    private fun loadProducts(): ArrayList<Product> {
        val sharedPreferences = getSharedPreferences("products", Context.MODE_PRIVATE)
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
        val sharedPreferences = getSharedPreferences("products", Context.MODE_PRIVATE)
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
}