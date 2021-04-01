package org.d3if0042.assesmen1pbb.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.d3if0042.assesmen1pbb.R
import org.d3if0042.assesmen1pbb.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding= FragmentHitungBinding.inflate(layoutInflater, container, false)
            val adapter = ArrayAdapter.createFromResource(requireContext(),
                    R.array.opsi_konv, android.R.layout.simple_spinner_item)
            binding.spinner1.adapter = adapter
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)
            binding.spinner1.adapter = adapter

        binding.convert.setOnClickListener{hitung()}
        binding.clear.setOnClickListener{reset()}



        return binding.root
    }
    private fun hitung(){
        val suhu= binding.suhuTf.text.toString()
        if (TextUtils.isEmpty(suhu)) {
            Toast.makeText(context, R.string.suhu_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val convertWhat= binding.spinner1.selectedItem.toString()
        if (convertWhat.equals("Celcius -> Fahrenheit")){
            val hasil= suhu.toFloat()* 1.8 + 32.0
            binding.hasilSuhu.text= getString(R.string.suhuF_x, hasil)
        }else if(convertWhat.equals("Celcius -> Kelvin")){
            val hasil= suhu.toFloat() + 273.15
            binding.hasilSuhu.text= getString(R.string.suhuK_x, hasil)
        }
        else if(convertWhat.equals("Fahrenheit -> Celcius")){
            val hasil= ((suhu.toFloat()-32.0) * 5.0) /9.0
            binding.hasilSuhu.text= getString(R.string.suhuC_x, hasil)
        }
        else if(convertWhat.equals("Fahrenheit -> Kelvin")){
            val hasil= 273.5 + ((suhu. toFloat() -32.0) *5.0) /9.0
            binding.hasilSuhu.text= getString(R.string.suhuK_x, hasil)
        }
        else if(convertWhat.equals("Kelvin -> Celcius")){
            val hasil= suhu.toFloat() - 273.15
            binding.hasilSuhu.text= getString(R.string.suhuC_x, hasil)
        }
        else if(convertWhat.equals("Kelvin -> Fahrenheit")){
            val hasil= ((suhu.toFloat() - 273.15) *9) /5 +32
            binding.hasilSuhu.text= getString(R.string.suhuF_x, hasil)
        }
    }

    private fun reset(){
        val suhu= binding.suhuTf.text.toString()
        if (TextUtils.isEmpty(suhu)) {
                Toast.makeText(context, R.string.already_empty, Toast.LENGTH_LONG).show()
                return
        }
        binding.suhuTf.setText("")
        binding.hasilSuhu.setText("")



    }
}