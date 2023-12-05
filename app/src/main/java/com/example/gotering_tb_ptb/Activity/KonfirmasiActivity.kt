package com.example.gotering_tb_ptb.Activity

import com.example.gotering_tb_ptb.Data.Product
import com.example.gotering_tb_ptb.Data.Order
import android.content.Intent
import android.widget.LinearLayout
import org.json.JSONArray
import org.json.JSONObject
import android.widget.TextView
import android.widget.Button
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gotering_tb_ptb.R

class KonfirmasiActivity : AppCompatActivity(){

    companion object {
        const val EXTRA_ORDER_ID = "orderID"
        const val EXTRA_ORDER_ORDERNAME = "orderName"
        const val EXTRA_ORDER_ORDERDATE = "orderDate"
        const val EXTRA_ORDER_ORDERDELIVER = "orderDeliver"
        const val EXTRA_ORDER_ORDERSUBTOTAL = "orderSubtotal"
        const val EXTRA_ORDER_ORDERDELIV = "orderDeliv"
        const val EXTRA_ORDER_ORDERDISC = "orderDisc"
        const val EXTRA_ORDER_ORDERTOTAL = "orderTotal"
    }

    private lateinit var orderid: TextView
    private lateinit var name: TextView
    private lateinit var date: TextView
    private lateinit var deliver: TextView
    private lateinit var subtotal: TextView
    private lateinit var deliv: TextView
    private lateinit var discount: TextView
    private lateinit var total: TextView
    private lateinit var cancel_button: Button
    private lateinit var confirm_button: Button

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
        setContentView(R.layout.activity_konfirmasi)

        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout.removeAllViews() // Remove any existing TextViews

        // Get product details from intent extras
        val orderID: String = intent.getStringExtra(EXTRA_ORDER_ID) ?: ""
        val orderName: String = intent.getStringExtra(EXTRA_ORDER_ORDERNAME) ?: ""
        val orderDate: String = intent.getStringExtra(EXTRA_ORDER_ORDERDATE) ?: ""
        val orderDeliver: String = intent.getStringExtra(EXTRA_ORDER_ORDERDELIVER) ?: ""
        val orderSubtotal: String = intent.getStringExtra(EXTRA_ORDER_ORDERSUBTOTAL) ?: ""
        val orderDeliv: String = intent.getStringExtra(EXTRA_ORDER_ORDERDELIV) ?: ""
        val orderDisc: String = intent.getStringExtra(EXTRA_ORDER_ORDERDISC) ?: ""
        val orderTotal: String = intent.getStringExtra(EXTRA_ORDER_ORDERTOTAL) ?: ""

        // Initialize UI elements
        orderid = findViewById(R.id.orderid)
        name = findViewById(R.id.name)
        date = findViewById(R.id.date)
        deliver = findViewById(R.id.deliver)
        subtotal = findViewById(R.id.subtotal)
        deliv = findViewById(R.id.deliv)
        discount = findViewById(R.id.discount)
        total = findViewById(R.id.total)
        cancel_button = findViewById(R.id.cancel_button)
        confirm_button = findViewById(R.id.confirm_button)

        // Set product name to text view
        orderid.text = orderID
        name.text = orderName
        date.text = orderDate
        deliver.text = orderDeliver

        // Calculate total price and update the UI
        val orderSubtotal2 = orderSubtotal.toString()
        val split2 = orderSubtotal2.split(".")
        val formattedTotal2 = StringBuilder()

        // Append "Rp " prefix
        formattedTotal2.append("Rp")

        // If there's a decimal part, handle it separately
        formattedTotal2.append(split2[0])

        // Insert dots for thousand separators
        val nonCurrencyPart2 = formattedTotal2.substring(3) // Remove the "Rp" prefix
        val formattedNonCurrencyPart2 =
            nonCurrencyPart2.reversed().chunked(3).joinToString(".").reversed()
        formattedTotal2.replace(3, formattedTotal2.length, formattedNonCurrencyPart2)
        subtotal.text = formattedTotal2.toString()

        // Calculate total price and update the UI
        val orderDeliv3 = orderDeliv.toString()
        val split3 = orderDeliv3.split(".")
        val formattedTotal3 = StringBuilder()

        // Append "Rp " prefix
        formattedTotal3.append("Rp")

        // If there's a decimal part, handle it separately
        formattedTotal3.append(split3[0])

        // Insert dots for thousand separators
        val nonCurrencyPart3 = formattedTotal3.substring(3) // Remove the "Rp" prefix
        val formattedNonCurrencyPart3 =
            nonCurrencyPart3.reversed().chunked(3).joinToString(".").reversed()
        formattedTotal3.replace(3, formattedTotal3.length, formattedNonCurrencyPart3)
        deliv.text = formattedTotal3.toString()

        // Calculate total price and update the UI
        val orderDisc4 = orderDisc.toString()
        val split4 = orderDisc4.split(".")
        val formattedTotal4 = StringBuilder()

        // Append "Rp " prefix
        formattedTotal4.append("Rp")

        // If there's a decimal part, handle it separately
        formattedTotal4.append(split4[0])

        // Insert dots for thousand separators
        val nonCurrencyPart4 = formattedTotal4.substring(3) // Remove the "Rp" prefix
        val formattedNonCurrencyPart4 =
            nonCurrencyPart4.reversed().chunked(3).joinToString(".").reversed()
        formattedTotal4.replace(3, formattedTotal4.length, formattedNonCurrencyPart4)
        discount.text = formattedTotal4.toString()

        // Calculate total price and update the UI
        val orderTotal5 = orderTotal.toString()
        val split5 = orderTotal5.split(".")
        val formattedTotal5 = StringBuilder()

        // Append "Rp " prefix
        formattedTotal5.append("Rp")

        // If there's a decimal part, handle it separately
        formattedTotal5.append(split5[0])

        // Insert dots for thousand separators
        val nonCurrencyPart5 = formattedTotal5.substring(3) // Remove the "Rp" prefix
        val formattedNonCurrencyPart5 =
            nonCurrencyPart5.reversed().chunked(3).joinToString(".").reversed()
        formattedTotal5.replace(3, formattedTotal5.length, formattedNonCurrencyPart5)
        total.text = formattedTotal5.toString()

        val productsList = loadProducts()

        for (product in productsList) {

            val productLayout = LinearLayout(this)
            productLayout.orientation = LinearLayout.HORIZONTAL
            productLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            productLayout.setPadding(20, 0, 20, 0)

            val nametextView = TextView(this)
            nametextView.text = "-" + " " + product.productName
            nametextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            nametextView.setTextColor(Color.BLACK)
            nametextView.textSize = 20f
            nametextView.setPadding(40, 0, 0, 0)
            productLayout.addView(nametextView)

            val jumlahtextView = TextView(this)
            jumlahtextView.text = product.productJumlah + "x"
            jumlahtextView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            jumlahtextView.setTextColor(Color.BLACK)
            jumlahtextView.textSize = 20f
            jumlahtextView.setPadding(10, 0, 0, 0)
            productLayout.addView(jumlahtextView)

            // Calculate total price and update the UI
            val total = product.productPrice.toInt() * product.productJumlah.toInt()
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

            val hargatextView = TextView(this)
            hargatextView.text = formattedTotal.toString()
            hargatextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            hargatextView.setTextColor(Color.BLACK)
            hargatextView.textSize = 20f
            hargatextView.setPadding(0, 0, 40, 0)
            productLayout.addView(hargatextView)

            linearLayout.addView(productLayout)

            cancel_button.setOnClickListener {
                onBackPressed()
            }

            confirm_button.setOnClickListener {
                // Load bookmarks from SharedPreferences and update the bookmark list and status map
                val ordersList = loadOrders()
                val bookmarkStatusMap = ordersList.associateBy({ it.orderID }, { true }).toMutableMap()
                val ordersArray = JSONArray()
                for (product in productsList) {
                    val orderObject = JSONObject()
                    orderObject.put("productName", product.productName)
                    orderObject.put("productJumlah", product.productJumlah)
                    orderObject.put("orderName", orderName)
                    orderObject.put("orderDate", orderDate)
                    orderObject.put("orderDeliver", orderDeliver)
                    orderObject.put("orderSubtotal", orderSubtotal)
                    orderObject.put("orderDeliv", orderDeliv)
                    orderObject.put("orderDisc", orderDisc)
                    orderObject.put("orderTotal", orderTotal)
                    orderObject.put("productPrice", product.productPrice)
                    orderObject.put("productID", orderID)
                    ordersArray.put(orderObject)
                }
                val bookmarkedOrder = Order(orderID, orderName, orderDate, ordersArray.toString())
                ordersList.add(bookmarkedOrder)
                bookmarkStatusMap[orderID] = true
                // Save the updated bookmarked items to SharedPreferences
                saveBookmarks(ordersList)

                // Mendapatkan SharedPreferences
                val sharedPreferences = getSharedPreferences("products", Context.MODE_PRIVATE)

                // Menghapus data dari SharedPreferences menggunakan Editor
                val editor = sharedPreferences.edit()
                editor.remove("productsList") // Menghapus data "productsList"
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("updateTransaksiFragment", true)
                startActivity(intent)
            }

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

    private fun loadOrders(): ArrayList<Order> {
        val sharedPreferences = getSharedPreferences("orders", Context.MODE_PRIVATE)
        val ordersJson = sharedPreferences.getString("ordersList", null)
        return if (ordersJson != null) {
            Gson().fromJson(ordersJson, object : TypeToken<ArrayList<Order>>() {}.type)
        } else {
            ArrayList()
        }
    }

    private fun saveBookmarks(ordersList: MutableList<Order>) {
        val jsonString = Gson().toJson(ordersList)

        // Load the existing bookmarks from SharedPreferences
        val sharedPreferences = getSharedPreferences("orders", Context.MODE_PRIVATE)
        val existingJsonString = sharedPreferences.getString("ordersList", null)

        // If there are existing bookmarks, update them with any new ones
        if (existingJsonString != null) {
            val existingBookmarks = Gson().fromJson(existingJsonString, Array<Order>::class.java).toMutableList()
            for (newBookmark in ordersList) {
                val existingBookmarkIndex = existingBookmarks.indexOfFirst { it.orderID == newBookmark.orderID }
                if (existingBookmarkIndex >= 0) {
                    val existingBookmark = existingBookmarks[existingBookmarkIndex]
                    val updatedBookmark = Order(
                        existingBookmark.orderID,
                        newBookmark.orderName,
                        newBookmark.orderDate,
                        newBookmark.ordersArray,

                    )
                    existingBookmarks[existingBookmarkIndex] = updatedBookmark
                } else {
                    existingBookmarks.add(newBookmark)
                }
            }
            val updatedJsonString = Gson().toJson(existingBookmarks)
            sharedPreferences.edit().putString("ordersList", updatedJsonString).apply()
        } else {
            // Otherwise, just save the new bookmarks to SharedPreferences
            sharedPreferences.edit().putString("ordersList", jsonString).apply()
        }
    }



}