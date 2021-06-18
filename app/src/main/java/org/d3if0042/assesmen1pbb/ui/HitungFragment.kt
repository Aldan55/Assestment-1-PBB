package org.d3if0042.assesmen1pbb.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if0042.assesmen1pbb.HitungViewModel
import org.d3if0042.assesmen1pbb.HitungViewModelFactory
import org.d3if0042.assesmen1pbb.R
import org.d3if0042.assesmen1pbb.databinding.FragmentHitungBinding
import org.d3if0042.assesmen1pbb.db.KonversiDao
import org.d3if0042.assesmen1pbb.db.KonversiDb
import java.util.*

class HitungFragment: Fragment() {

    private val viewModel: HitungViewModel by lazy {
        val db = KonversiDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HitungViewModel::class.java)
    }

    private lateinit var binding: FragmentHitungBinding
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    } override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding= FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.shareButton.setOnClickListener { shareData() }
            val adapter = ArrayAdapter.createFromResource(requireContext(),
                    R.array.opsi_konv, android.R.layout.simple_spinner_item)
            binding.spinner1.adapter = adapter
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)

//-- fungsi untuk hitung otomatis
//-- NOTE: Hapus Tombol Hitung kalo mau pakai
//        binding.suhuTf.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                hitung()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//
//        binding.spinner1.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                hitung()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }

//-- hitung manual pakai tombol Hitung
        binding.hitung.setOnClickListener{hitung()}


        binding.clear.setOnClickListener{reset()}
        binding.formulaButton.setOnClickListener { view: View ->
            val conv= binding.spinner1.selectedItem.toString()
            val hasil= binding.hasilSuhu.text.toString()
            val suhu_awal= binding.suhuTf.text.toString()
            val suhu_hasil = hasil.substring(startIndex = 6)
            val labelSuhu= binding.labelSimbol.text.toString()
            val convUnit= binding.convSuhu.text.toString()

            val message =  getString(R.string.bagikan_template, suhu_awal, labelSuhu, convUnit, suhu_hasil)
            val action= HitungFragmentDirections.actionHitungFragmentToFormulaFragment(conv, suhu_awal, suhu_hasil, message)
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHasilKonversi().observe(viewLifecycleOwner, {
            if (it == null) return@observe

            binding.hasilSuhu.text = getString(R.string.suhuF_x, it.suhuHasil)
            if (it.convWhat.equals("Celsius -> Fahrenheit")){
                binding.hasilSuhu.text= getString(R.string.suhuF_x, it.suhuHasil)
            }else if(it.convWhat.equals("Celsius -> Kelvin")){
                binding.hasilSuhu.text= getString(R.string.suhuK_x, it.suhuHasil)
            }
            else if(it.convWhat.equals("Fahrenheit -> Celsius")){
                binding.hasilSuhu.text= getString(R.string.suhuC_x, it.suhuHasil)
            }
            else if(it.convWhat.equals("Fahrenheit -> Kelvin")){
                binding.hasilSuhu.text= getString(R.string.suhuK_x, it.suhuHasil)
            }
            else if(it.convWhat.equals("Kelvin -> Celsius")){
                binding.hasilSuhu.text= getString(R.string.suhuC_x, it.suhuHasil)
            }
            else if(it.convWhat.equals("Kelvin -> Fahrenheit")){
                binding.hasilSuhu.text= getString(R.string.suhuF_x, it.suhuHasil)
            }
        })

    }

    @SuppressLint("StringFormatMatches")
    private fun hitung(){
        val convertWhat= binding.spinner1.selectedItem.toString()
        val suhu= binding.suhuTf.text.toString()
        val suhuHasil= binding.hasilSuhu.toString()

        viewModel.hitungKonversi(convertWhat, suhu)

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
            binding.convSuhu.text= getString(R.string.fahrenheit)
        }else if(convertWhat.equals("Celsius -> Kelvin")){
            binding.convSuhu.text= getString(R.string.kelvin)
        }
        else if(convertWhat.equals("Fahrenheit -> Celsius")){
            binding.convSuhu.text= getString(R.string.celsius)
        }
        else if(convertWhat.equals("Fahrenheit -> Kelvin")){
            binding.convSuhu.text= getString(R.string.kelvin)
        }
        else if(convertWhat.equals("Kelvin -> Celsius")){
            binding.convSuhu.text= getString(R.string.celsius)
        }
        else if(convertWhat.equals("Kelvin -> Fahrenheit")){
            binding.convSuhu.text= getString(R.string.fahrenheit)
        }
        binding.hasilSuhu.visibility = View.VISIBLE
        binding.formulaButton.visibility = View.VISIBLE
        binding.shareButton.visibility = View.VISIBLE
    }
    private fun shareData() {
        val shareIntent = Intent(Intent.ACTION_SEND)

        val suhuAwal = binding.suhuTf.text.toString()
        val labelSuhu= binding.labelSimbol.text.toString()
        val convUnit= binding.convSuhu.text.toString()
        val hasil= binding.hasilSuhu.text.toString()
        val subStrHasil=  hasil.substring(startIndex = 6)

        val message =  getString(R.string.bagikan_template, suhuAwal, labelSuhu, convUnit, subStrHasil)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
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