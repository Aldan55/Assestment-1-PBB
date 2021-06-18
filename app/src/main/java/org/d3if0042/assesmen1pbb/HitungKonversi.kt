package org.d3if0042.assesmen1pbb

import org.d3if0042.assesmen1pbb.db.KonversiEntity

object HitungKonversi {

    fun hitung(data: KonversiEntity): HasilKonversi {
        var hasil = when {
            data.konversiApa.equals("Celsius -> Fahrenheit") -> data.suhuAwal.toFloat() * 1.8 + 32.0
            data.konversiApa.equals("Celsius -> Kelvin") -> data.suhuAwal.toFloat() + 273.15
            data.konversiApa.equals("Fahrenheit -> Celsius") -> ((data.suhuAwal.toFloat() - 32.0) * 5.0) / 9.0
            data.konversiApa.equals("Fahrenheit -> Kelvin") -> 273.5 + ((data.suhuAwal.toFloat() - 32.0) * 5.0) / 9.0
            data.konversiApa.equals("Kelvin -> Celsius") -> data.suhuAwal.toFloat() - 273.15
            data.konversiApa.equals("Kelvin -> Fahrenheit") -> ((data.suhuAwal.toFloat() - 273.15) * 9) / 5 + 32
            else -> null

        }
        return HasilKonversi(data.konversiApa, data.suhuAwal, hasil)
    }

}