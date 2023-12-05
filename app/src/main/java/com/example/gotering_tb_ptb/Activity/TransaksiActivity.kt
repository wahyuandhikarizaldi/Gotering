package com.example.gotering_tb_ptb.Activity

import com.example.gotering_tb_ptb.Data.Order
import android.content.Intent
import android.widget.LinearLayout
import org.json.JSONArray
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

class TransaksiActivity : AppCompatActivity(){

    companion object {
        const val EXTRA_ORDER_ID = "orderID"
        const val EXTRA_ORDER_ORDERNAME = "orderName"
        const val EXTRA_ORDER_ORDERDATE = "orderDate"
    }

    private lateinit var orderid: TextView
    private lateinit var name: TextView
    private lateinit var date: TextView
    private lateinit var deliver: TextView
    private lateinit var subtotal: TextView
    private lateinit var deliv: TextView
    private lateinit var discount: TextView
    private lateinit var total: TextView
    private lateinit var delete_button: Button

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
        setContentView(R.layout.activity_transaksi)

        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout.removeAllViews() // Remove any existing TextViews

        // Get product details from intent extras
        val orderID: String = intent.getStringExtra(KonfirmasiActivity.EXTRA_ORDER_ID) ?: ""
        val orderName: String = intent.getStringExtra(KonfirmasiActivity.EXTRA_ORDER_ORDERNAME) ?: ""
        val orderDate: String = intent.getStringExtra(KonfirmasiActivity.EXTRA_ORDER_ORDERDATE) ?: ""

        // Initialize UI elements
        orderid = findViewById(R.id.orderid)
        name = findViewById(R.id.name)
        date = findViewById(R.id.date)
        deliver = findViewById(R.id.deliver)
        subtotal = findViewById(R.id.subtotal)
        deliv = findViewById(R.id.deliv)
        discount = findViewById(R.id.discount)
        total = findViewById(R.id.total)
        delete_button = findViewById(R.id.delete_button)

        // Set product name to text view
        orderid.text = orderID
        name.text = orderName
        date.text = orderDate

        val ordersList = loadOrders()

        for (order in ordersList) {

            val jsonArray = JSONArray(order.ordersArray)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val productName2 = jsonObject.getString("productName")
                val productJumlah2 = jsonObject.getString("productJumlah")
                val productPrice2 = jsonObject.getString("productPrice")
                val orderName2 = jsonObject.getString("orderName")
                val orderDate2 = jsonObject.getString("orderDate")
                val orderDeliver2 = jsonObject.getString("orderDeliver")
                val orderSubtotal2 = jsonObject.getString("orderSubtotal")
                val orderDeliv2 = jsonObject.getString("orderDeliv")
                val orderDisc2 = jsonObject.getString("orderDisc")
                val orderTotal2 = jsonObject.getString("orderTotal")
                val productID2 = jsonObject.getString("productID")

                if (productID2 == orderID) {

                    deliver.text = orderDeliver2

                    // Calculate total price and update the UI
                    val orderSubtotal3 = orderSubtotal2.toString()
                    val split2 = orderSubtotal3.split(".")
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
                    val orderDeliv3 = orderDeliv2.toString()
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
                    val orderDisc4 = orderDisc2.toString()
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
                    val orderTotal5 = orderTotal2.toString()
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

                    val productLayout = LinearLayout(this)
                    productLayout.orientation = LinearLayout.HORIZONTAL
                    productLayout.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    productLayout.setPadding(20, 0, 20, 0)

                    val nametextView2 = TextView(this)
                    nametextView2.text = "-" + " " + productName2
                    nametextView2.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    nametextView2.setTextColor(Color.BLACK)
                    nametextView2.textSize = 20f
                    nametextView2.setPadding(40, 0, 0, 0)
                    productLayout.addView(nametextView2)

                    val jumlahtextView2 = TextView(this)
                    jumlahtextView2.text = productJumlah2 + "x"
                    jumlahtextView2.layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                    )
                    jumlahtextView2.setTextColor(Color.BLACK)
                    jumlahtextView2.textSize = 20f
                    jumlahtextView2.setPadding(10, 0, 0, 0)
                    productLayout.addView(jumlahtextView2)

                    // Calculate total price and update the UI
                    val total6 = productPrice2.toInt() * productJumlah2.toInt()
                    val totalValue6 = total6.toString()
                    val split6 = totalValue6.split(".")
                    val formattedTotal6 = StringBuilder()

                    // Append "Rp " prefix
                    formattedTotal6.append("Rp")

                    // If there's a decimal part, handle it separately
                    formattedTotal6.append(split6[0])

                    // Insert dots for thousand separators
                    val nonCurrencyPart6 = formattedTotal6.substring(3) // Remove the "Rp" prefix
                    val formattedNonCurrencyPart6 =
                        nonCurrencyPart6.reversed().chunked(3).joinToString(".").reversed()
                    formattedTotal6.replace(3, formattedTotal6.length, formattedNonCurrencyPart6)

                    val hargatextView2 = TextView(this)
                    hargatextView2.text = formattedTotal6.toString()
                    hargatextView2.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    hargatextView2.setTextColor(Color.BLACK)
                    hargatextView2.textSize = 20f
                    hargatextView2.setPadding(0, 0, 40, 0)
                    productLayout.addView(hargatextView2)

                    linearLayout.addView(productLayout)
                }
            }

            delete_button.setOnClickListener{
                // Mendapatkan SharedPreferences
                val sharedPreferences = getSharedPreferences("orders", Context.MODE_PRIVATE)

                // Mengambil pesanan yang akan dihapus dari SharedPreferences
                val ordersList = loadOrders().toMutableList()

                // Lakukan proses penghapusan pesanan yang sesuai
                // Misalnya, jika ingin menghapus pesanan pertama dari ordersList:
                if (ordersList.isNotEmpty()) {
                    ordersList.removeAt(0) // Menghapus pesanan pertama dari list
                }

                // Menyimpan kembali data yang tersisa ke SharedPreferences
                val editor = sharedPreferences.edit()
                val updatedOrdersJsonString = Gson().toJson(ordersList)
                editor.putString("ordersList", updatedOrdersJsonString)
                editor.apply()

                updateUI()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("updateTransaksiFragment", true)
                startActivity(intent)
            }

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

    public fun updateUI() {
        // Invalidate the view to force a redraw if needed

        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout?.invalidate()

        val ordersList = loadOrders()

        for (order in ordersList) {

            val productLayout = LinearLayout(this)
            productLayout.invalidate()

        }

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

}