package org.d3if0042.assesmen1pbb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if0042.assesmen1pbb.databinding.ItemHistoriBinding
import org.d3if0042.assesmen1pbb.db.KonversiEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : RecyclerView.Adapter<HistoriAdapter.ViewHolder>() {
    private val data = mutableListOf<KonversiEntity>()
    fun updateData(newData: List<KonversiEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(private val binding: ItemHistoriBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        fun bind(item: KonversiEntity) = with(binding) {
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            val hasilKonversi = HitungKonversi.hitung(item)
            konversi2.text = hasilKonversi.convWhat
        }
    }
}
