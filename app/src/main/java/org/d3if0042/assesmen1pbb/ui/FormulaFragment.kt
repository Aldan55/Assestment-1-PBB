package org.d3if0042.assesmen1pbb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if0042.assesmen1pbb.R
import org.d3if0042.assesmen1pbb.databinding.FragmentFormulaBinding

class FormulaFragment:Fragment() {

    private val args: FormulaFragmentArgs by navArgs()
    private lateinit var binding: FragmentFormulaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFormulaBinding.inflate(layoutInflater, container, false)

        updateUI()

        return binding.root
    }

    private fun updateUI() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        val convertWhat= args.convWhat

        if (convertWhat.equals("Celsius -> Fahrenheit")){
            actionBar?.title = getString(R.string.judul_CF)
            binding.formula.text= getString(R.string.formula_CF, args.suhuAwal, args.suhuHasil)

        }else if(convertWhat.equals("Celsius -> Kelvin")){
            actionBar?.title = getString(R.string.judul_CK)
            binding.formula.text= getString(R.string.formula_CK, args.suhuAwal, args.suhuHasil)

        }
        else if(convertWhat.equals("Fahrenheit -> Celsius")){
            actionBar?.title = getString(R.string.judul_FC)
            binding.formula.text= getString(R.string.formula_FC, args.suhuAwal, args.suhuHasil)

        }
        else if(convertWhat.equals("Fahrenheit -> Kelvin")){
            actionBar?.title = getString(R.string.judul_FK)
            binding.formula.text= getString(R.string.formula_FK, args.suhuAwal, args.suhuHasil)

        }
        else if(convertWhat.equals("Kelvin -> Celsius")){
            actionBar?.title = getString(R.string.judul_KC)
            binding.formula.text= getString(R.string.formula_KC, args.suhuAwal, args.suhuHasil)

        }
        else if(convertWhat.equals("Kelvin -> Fahrenheit")){
            actionBar?.title = getString(R.string.judul_KC)
            binding.formula.text= getString(R.string.formula_KF, args.suhuAwal, args.suhuHasil)

        }


    }
}