package com.example.gotering_tb_ptb.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.gotering_tb_ptb.R
import com.example.gotering_tb_ptb.databinding.ActivityMainBinding
import com.example.gotering_tb_ptb.fragment.HomeFragment
import com.example.gotering_tb_ptb.fragment.PesananFragment
import com.example.gotering_tb_ptb.fragment.TransaksiFragment
import com.example.gotering_tb_ptb.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentSearch: Fragment = SearchFragment()
    private val fragmentPesanan: Fragment = PesananFragment()
    private val fragmentTransaksi: Fragment = TransaksiFragment()

    private val fragmentManager: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonNavigation()

        val updatePesananFragment = intent.getBooleanExtra("updatePesananFragment", false)
        if (updatePesananFragment) {
            refreshPesananFragment()
            callFragment(2, fragmentPesanan)
        }

        val updateTransaksiFragment = intent.getBooleanExtra("updateTransaksiFragment", false)
        if (updateTransaksiFragment) {
            refreshTranksasiFragment()
            callFragment(3, fragmentTransaksi)
        }
    }

    private fun refreshPesananFragment() {
        val pesananFragment = fragmentManager.findFragmentByTag("PESANAN_FRAGMENT") as PesananFragment?
        pesananFragment?.updateUI() // Panggil fungsi updateUI di PesananFragment
    }

    private fun refreshTranksasiFragment() {
        val TransaksiFragment = fragmentManager.findFragmentByTag("TRANSAKSI_FRAGMENT") as TransaksiFragment?
        TransaksiFragment?.updateUI() // Panggil fungsi updateUI di PesananFragment
    }


    private fun callFragment(index: Int, fragment: Fragment) {
        val menuItem: MenuItem = binding.navView.menu.getItem(index)
        menuItem.isChecked = true
        fragmentManager.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    private fun buttonNavigation() {
        fragmentManager.beginTransaction().add(binding.container.id, fragmentHome).show(fragmentHome).commit()
        fragmentManager.beginTransaction().add(binding.container.id, fragmentSearch).hide(fragmentSearch).commit()
        fragmentManager.beginTransaction().add(binding.container.id, fragmentPesanan).hide(fragmentPesanan).commit()
        fragmentManager.beginTransaction().add(binding.container.id, fragmentTransaksi).hide(fragmentTransaksi).commit()

        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    callFragment(0, fragmentHome)
                }
                R.id.navigation_search -> {
                    callFragment(1, fragmentSearch)
                }
                R.id.navigation_pesanan -> {
                    callFragment(2, fragmentPesanan)
                }
                R.id.navigation_transaksi -> {
                    callFragment(3, fragmentTransaksi)
                }
            }
            false
        }
    }
}
