package com.example.mua.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_chat.view.*

class ChatViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(chat: ListChat) {
        view.apply {
            //get data users
            val id = "Nama   : ${chat.id_room}"
            val provider_id = "Alamat : ${chat.id_provider}"
            val user_id = "Umur    : ${chat.id_user}"
            //set view
            tv_name.text = id
            tv_adress.text = provider_id
            tv_age.text = user_id
        }
    }
}