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
import android.widget.Button
import android.content.Context
import android.view.Gravity
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gotering_tb_ptb.Data.Order

class TransaksiFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaksi, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = view.findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout.removeAllViews() // Remove any existing TextViews

        val ordersList = loadOrders()

        for (order in ordersList) {

            val productLayout = LinearLayout(requireContext())
            productLayout.orientation = LinearLayout.HORIZONTAL
            productLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            productLayout.setPadding(20, 20, 20, 20)

            // Add product to layout
            val productDetailsLayout = LinearLayout(requireContext())
            productDetailsLayout.orientation = LinearLayout.VERTICAL
            productDetailsLayout.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            ) // Set weight to 1

            productDetailsLayout.setPadding(20, 0, 0, 0)

            val idtextView = TextView(requireContext())
            idtextView.text = "Order ID: " + order.orderID
            idtextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            idtextView.setTextColor(Color.BLACK)
            productDetailsLayout.addView(idtextView)

            val nametextView = TextView(requireContext())
            nametextView.text = "Name: " + order.orderName
            nametextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            nametextView.setTextColor(Color.BLACK)
            productDetailsLayout.addView(nametextView)

            val datetextview = TextView(requireContext())
            datetextview.text = "Date: " + order.orderDate
            datetextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            datetextview.setTextColor(Color.BLACK)
            datetextview.gravity = Gravity.CENTER_VERTICAL
            productDetailsLayout.addView(datetextview)

            productLayout.addView(productDetailsLayout)

            productLayout.setOnClickListener {
                val intent = Intent(requireActivity(), com.example.gotering_tb_ptb.Activity.TransaksiActivity::class.java)
                intent.putExtra("orderID", order.orderID)
                intent.putExtra("orderName", order.orderName)
                intent.putExtra("orderDate", order.orderDate)
                startActivity(intent)
            }

            val tombolhapus = Button(requireContext())
            tombolhapus.layoutParams = LinearLayout.LayoutParams(
                70,
                70
            )
            tombolhapus.setBackgroundResource(R.drawable.baseline_delete_24)
            productLayout.addView(tombolhapus)

            tombolhapus.setOnClickListener{
                // Remove the selected product from the list
                ordersList.remove(order)

                // Convert the updated list back to a JSON string and save it to SharedPreferences
                val updatedProductsJsonString = Gson().toJson(ordersList)
                val sharedPreferences = requireContext().getSharedPreferences("orders", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("ordersList", updatedProductsJsonString)
                editor.apply()

                // Remove views associated with the deleted product from the layout
                linearLayout?.removeView(productLayout)

                updateUI()
            }

            linearLayout.addView(productLayout)

        }

        updateUI()

    }

    private fun loadOrders(): ArrayList<Order> {
        val sharedPreferences = requireContext().getSharedPreferences("orders", Context.MODE_PRIVATE)
        val ordersJson = sharedPreferences.getString("ordersList", null)
        return if (ordersJson != null) {
            Gson().fromJson(ordersJson, object : TypeToken<ArrayList<Order>>() {}.type)
        } else {
            ArrayList()
        }
    }

    public fun updateUI() {
        // Invalidate the view to force a redraw if needed

        val linearLayout = view?.findViewById<LinearLayout>(R.id.linear_layout_documents)
        linearLayout?.invalidate()

        val ordersList = loadOrders()

        for (order in ordersList) {

            val productLayout = LinearLayout(requireContext())
            productLayout.invalidate()

        }

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

}