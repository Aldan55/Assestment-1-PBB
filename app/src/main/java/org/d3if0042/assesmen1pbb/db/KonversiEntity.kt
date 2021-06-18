package org.d3if0042.assesmen1pbb.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "konversi")
data class KonversiEntity (
    @PrimaryKey(autoGenerate= true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var konversiApa: String,
    var suhuAwal: Double,
    val suhuHasil: Double
    )