package com.example.mua.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    var fromUid: String? = null,
    var text: String? = null,
    @ServerTimestamp
    val send_at: Timestamp? = null
)