package org.d3if0042.assesmen1pbb.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if0042.assesmen1pbb.R
import org.d3if0042.assesmen1pbb.databinding.FragmentHitungBinding
import java.util.*

const val KEY_USER_TEMP= "user_temp_key"

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    } override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var suhuUser= 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_USER_TEMP, suhuUser)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding= FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.shareButton.setOnClickListener { shareData() }
            val adapter = ArrayAdapter.createFromResource(requireContext(),
                    R.array.opsi_konv, android.R.layout.simple_spinner_item)
            binding.spinner1.adapter = adapter
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)


        binding.suhuTf.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                hitung()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.spinner1.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                hitung()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding.clear.setOnClickListener{reset()}

        binding.formulaButton.setOnClickListener { view: View ->
            val conv= binding.spinner1.selectedItem.toString()
            val hasil= binding.hasilSuhu.text.toString()
            val suhu_awal= binding.suhuTf.text.toString()
            val suhu_hasil = hasil.substring(startIndex = 6)

            val action= HitungFragmentDirections.actionHitungFragmentToFormulaFragment(conv, suhu_awal, suhu_hasil)
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun hitung(){
        val convertWhat= binding.spinner1.selectedItem.toString()
        val suhu= binding.suhuTf.text.toString()

        if (convertWhat.equals("Celsius -> Fahrenheit") || convertWhat.equals("Celsius -> Kelvin") ){
            binding.labelSimbol.text= "°C"
        }else if(convertWhat.equals("Fahrenheit -> Celsius") || convertWhat.equals("Fahrenheit -> Kelvin") ){
            binding.labelSimbol.text= "°F"
        }else {
            binding.labelSimbol.text= "K"
        }
        if (TextUtils.isEmpty(suhu)) {
            binding.hasilSuhu.text= ""
            binding.formulaButton.visibility = View.INVISIBLE
            binding.hasilSuhu.visibility = View.INVISIBLE
            binding.shareButton.visibility = View.INVISIBLE
            return
        }
        else if (convertWhat.equals("Celsius -> Fahrenheit")){
            val hasil= suhu.toFloat()* 1.8 + 32.0
            binding.hasilSuhu.text= getString(R.string.suhuF_x, hasil)
        }else if(convertWhat.equals("Celsius -> Kelvin")){
            val hasil= suhu.toFloat() + 273.15
            binding.hasilSuhu.text= getString(R.string.suhuK_x, hasil)
        }
        else if(convertWhat.equals("Fahrenheit -> Celsius")){
            val hasil= ((suhu.toFloat()-32.0) * 5.0) /9.0
            binding.hasilSuhu.text= getString(R.string.suhuC_x, hasil)
        }
        else if(convertWhat.equals("Fahrenheit -> Kelvin")){
            val hasil= 273.5 + ((suhu. toFloat() -32.0) *5.0) /9.0
            binding.hasilSuhu.text= getString(R.string.suhuK_x, hasil)
        }
        else if(convertWhat.equals("Kelvin -> Celsius")){
            val hasil= suhu.toFloat() - 273.15
            binding.hasilSuhu.text= getString(R.string.suhuC_x, hasil)
        }
        else if(convertWhat.equals("Kelvin -> Fahrenheit")){
            val hasil= ((suhu.toFloat() - 273.15) *9) /5 +32
            binding.hasilSuhu.text= getString(R.string.suhuF_x, hasil)
        }
        binding.hasilSuhu.visibility = View.VISIBLE
        binding.formulaButton.visibility = View.VISIBLE
        binding.shareButton.visibility = View.VISIBLE

    }
    private fun shareData() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, "test")
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
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
        binding.formulaButton.visibility = View.INVISIBLE
        binding.shareButton.visibility = View.INVISIBLE
    }
}