package org.d3if0042.assesmen1pbb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if0042.assesmen1pbb.HistoriAdapter
import org.d3if0042.assesmen1pbb.HistoriViewModel
import org.d3if0042.assesmen1pbb.HistoriViewModelFactory
import org.d3if0042.assesmen1pbb.databinding.FragmentHistoriBinding
import org.d3if0042.assesmen1pbb.db.KonversiDb
class HistoriFragment : Fragment() {
    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter

    private val viewModel: HistoriViewModel by lazy {
        val db = KonversiDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HistoriViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater,container, false)
        myAdapter = HistoriAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.updateData(it)        })
    }

}