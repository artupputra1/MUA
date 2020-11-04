package com.example.mua.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ListChat(
        var id_room: String = "0",
        var id_provider: String? = null,
        var id_user: String? = null
): Parcelable