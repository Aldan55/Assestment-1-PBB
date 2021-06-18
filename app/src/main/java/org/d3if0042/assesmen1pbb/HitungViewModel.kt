package org.d3if0042.assesmen1pbb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0042.assesmen1pbb.db.KonversiDao
import org.d3if0042.assesmen1pbb.db.KonversiEntity

class HitungViewModel(private val db: KonversiDao) : ViewModel() {
    private val hasilKonversi = MutableLiveData<HasilKonversi?>()

    fun hitungKonversi(convertWhat: String, suhu: String) {

        val dataKonversi = KonversiEntity(
            konversiApa = convertWhat,
            suhuAwal = suhu .toDouble(),
            suhuHasil = suhu.toDouble()
        )
        hasilKonversi.value= HitungKonversi.hitung(dataKonversi)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                db.insert(dataKonversi)
            }

        }

    }
    fun getHasilKonversi(): LiveData<HasilKonversi?> = hasilKonversi
}