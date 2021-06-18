package org.d3if0042.assesmen1pbb.db

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KonversiDao {

    @Insert
    fun insert(konversi: KonversiEntity)

    @Query("SELECT * FROM konversi ORDER BY id DESC")
    fun getLastBmi(): LiveData<List<KonversiEntity>>
}