package com.example.gotering_tb_ptb.Activity

import com.example.gotering_tb_ptb.Data.Order
import android.content.Intent
import android.widget.LinearLayout
import org.json.JSONArray
import android.widget.TextView
import android.widget.Button
import java.text.SimpleDateFormat
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import java.util.*
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gotering_tb_ptb.R
import com.google.android.material.datepicker.MaterialDatePicker

class RekapActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_rekap)

        // Set the toolbar as the action bar for the activity
        val toolbar = findViewById<Toolbar>(R.id.toolbar4)
        toolbar.title = ""

        setSupportActionBar(toolbar)

        // Add a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout.removeAllViews() // Remove any existing TextViews

        val buttonDate = findViewById<TextView>(R.id.date_button)
        buttonDate.text = getCurrentDate()
        val targetDate = buttonDate.text.toString()

        val builder : MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()

        builder.setTitleText("Pilih Tanggal")
        val picker : MaterialDatePicker<*> = builder.build()

        buttonDate.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }

        picker.addOnPositiveButtonClickListener { selection ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = sdf.format(selection)
            buttonDate.text = formattedDate
            val targetDate = buttonDate.text.toString()
            searchRekap(targetDate)
            Log.d("TargetDateValue", targetDate)
        }

        searchRekap(targetDate)
        Log.d("TargetDateValue", targetDate)

    }

    private fun searchRekap(targetDate: String){
        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout.removeAllViews() // Remove any existing TextViews

        var wholeTotal = 0.0

        val ordersList = loadOrders()

        for (order in ordersList) {
            if (order.orderDate == targetDate) {

                val productLayout = LinearLayout(this)
                productLayout.orientation = LinearLayout.HORIZONTAL
                productLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                productLayout.setPadding(20, 20, 20, 20)

                val nametextView = TextView(this)
                nametextView.text = order.orderName
                nametextView.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                nametextView.setPadding(40, 0, 0, 0)
                nametextView.textSize = 20f
                nametextView.setTextColor(Color.BLACK)
                productLayout.addView(nametextView)

                val jsonArray = JSONArray(order.ordersArray)

                var foundTotal = false
                var total6 = ""

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


                    if (productID2 == order.orderID) {
                        foundTotal = true
                        total6 = orderTotal2 // Mengambil nilai orderTotal2 jika productID2 cocok
                        break // Keluar dari loop jika sudah menemukan nilai yang cocok
                    }
                }
                if (foundTotal) {
                    val split = total6.split(".")
                    val formattedTotal = StringBuilder()

                    // Append "Rp " prefix
                    formattedTotal.append("Rp")

                    // If there's a decimal part, handle it separately
                    formattedTotal.append(split[0])

                    // Insert dots for thousand separators
                    val nonCurrencyPart = formattedTotal.substring(3) // Remove the "Rp" prefix
                    val formattedNonCurrencyPart =
                        nonCurrencyPart.reversed().chunked(3).joinToString(".").reversed()
                    formattedTotal.replace(3, formattedTotal.length, formattedNonCurrencyPart)

                    val totaltextView = TextView(this)
                    totaltextView.text = formattedTotal.toString()
                    totaltextView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    totaltextView.setTextColor(Color.BLACK)
                    totaltextView.textSize = 20f
                    totaltextView.setPadding(0, 0, 40, 0)
                    productLayout.addView(totaltextView)

                }

                val thetotal = total6.toDoubleOrNull()?.toInt() ?: 0
                wholeTotal += thetotal

                linearLayout.addView(productLayout)
            }


        }

        val split2 = wholeTotal.toString().split(".")
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

        val wholetotaltextView = findViewById<TextView>(R.id.total)
        wholetotaltextView.text = formattedTotal2.toString()
        wholetotaltextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        wholetotaltextView.setTextColor(Color.BLACK)
        wholetotaltextView.textSize = 20f
        wholetotaltextView.setPadding(0, 0, 40, 0)

        updateUI()
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
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

        val buttonDate = findViewById<TextView>(R.id.date_button)
        val targetDate = buttonDate.text.toString()

//        searchRekap(targetDate)

        updateUI()
    }



}