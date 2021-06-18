package org.d3if0042.assesmen1pbb

import androidx.lifecycle.ViewModel
import org.d3if0042.assesmen1pbb.db.KonversiDao

class HistoriViewModel(db: KonversiDao) : ViewModel() {
    val data = db.getLastBmi()
}