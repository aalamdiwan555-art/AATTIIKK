package com.topperg

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.topperg.data.local.entity.BoardEntity
import com.topperg.data.repository.BoardRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class TopperGApplication : Application() {

    @Inject
    lateinit var boardRepository: BoardRepository

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // Initialize AdMob
        MobileAds.initialize(this) {}

        // Seed boards on first launch
        applicationScope.launch {
            seedBoards()
        }
    }

    private suspend fun seedBoards() {
        val boards = listOf(
            BoardEntity("cbse", "CBSE", "Central Board of Secondary Education", "National", null, 1),
            BoardEntity("icse", "ICSE", "Council for the Indian School Certificate Examinations", "National", null, 2),
            BoardEntity("nios", "NIOS", "National Institute of Open Schooling", "National_Open", null, 3),
            BoardEntity("gseb", "GSEB", "Gujarat Secondary and Higher Secondary Education Board", "State", "Gujarat", 4),
            BoardEntity("maharashtra", "MSBSHSE", "Maharashtra State Board", "State", "Maharashtra", 5),
            BoardEntity("up_board", "UP Board", "Uttar Pradesh Madhyamik Shiksha Parishad", "State", "Uttar Pradesh", 6),
            BoardEntity("bseb", "BSEB", "Bihar School Examination Board", "State", "Bihar", 7),
            BoardEntity("rbse", "RBSE", "Board of Secondary Education Rajasthan", "State", "Rajasthan", 8),
            BoardEntity("mpbse", "MPBSE", "Madhya Pradesh Board", "State", "Madhya Pradesh", 9),
            BoardEntity("hbse", "HBSE", "Board of School Education Haryana", "State", "Haryana", 10),
            BoardEntity("jac", "JAC", "Jharkhand Academic Council", "State", "Jharkhand", 11),
            BoardEntity("wbbse", "WBBSE", "West Bengal Board of Secondary Education", "State", "West Bengal", 12),
            BoardEntity("tn_board", "TNBSE", "Tamil Nadu State Board", "State", "Tamil Nadu", 13),
            BoardEntity("karnataka", "KSEAB", "Karnataka School Examination and Assessment Board", "State", "Karnataka", 14),
            BoardEntity("kerala", "KBPE", "Kerala Board of Public Examination", "State", "Kerala", 15),
            BoardEntity("pseb", "PSEB", "Punjab School Education Board", "State", "Punjab", 16),
            BoardEntity("cgbse", "CGBSE", "Chhattisgarh Board of Secondary Education", "State", "Chhattisgarh", 17),
            BoardEntity("bse_odisha", "BSE Odisha", "Board of Secondary Education Odisha", "State", "Odisha", 18),
            BoardEntity("seba", "SEBA", "Board of Secondary Education Assam", "State", "Assam", 19),
            BoardEntity("tbse", "TBSE", "Tripura Board of Secondary Education", "State", "Tripura", 20),
            BoardEntity("mbose", "MBOSE", "Meghalaya Board of School Education", "State", "Meghalaya", 21),
            BoardEntity("nbse", "NBSE", "Nagaland Board of School Education", "State", "Nagaland", 22),
            BoardEntity("bosem", "BOSEM", "Board of Secondary Education Manipur", "State", "Manipur", 23),
            BoardEntity("mizoram", "MBSE", "Mizoram Board of School Education", "State", "Mizoram", 24),
            BoardEntity("bse_ap", "BSEAP", "Board of Secondary Education Andhra Pradesh", "State", "Andhra Pradesh", 25),
            BoardEntity("bse_telangana", "BSE Telangana", "Board of Secondary Education Telangana", "State", "Telangana", 26),
            BoardEntity("hp_board", "HPBOSE", "Himachal Pradesh Board", "State", "Himachal Pradesh", 27),
            BoardEntity("jk_board", "JKBOSE", "Jammu and Kashmir Board", "State", "Jammu and Kashmir", 28),
            BoardEntity("uk_board", "UBSE", "Uttarakhand Board", "State", "Uttarakhand", 29),
            BoardEntity("goa_board", "GBSHSE", "Goa Board of Secondary and Higher Secondary Education", "State", "Goa", 30),
            BoardEntity("cbse_delhi", "CBSE Delhi", "CBSE Delhi (Same as CBSE)", "State", "Delhi", 31)
        )
        boardRepository.seedBoards(boards)
    }
}
