package com.example.mua.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mua.R
import kotlinx.android.synthetic.main.layout_chat.view.*
import kotlinx.android.synthetic.main.layout_list_chat.view.*


class ListChatAdapter(private val context: Context, private val arrayList: ArrayList<ListChat>) : RecyclerView.Adapter<ListChatAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_chat,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tv_name.text = arrayList!!.get(position)?.id_room
        holder.view.tv_adress.text = arrayList?.get(position)?.id_user
        holder.view.tv_age.text = arrayList?.get(position)?.id_provider
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("chat_id", arrayList!!.get(position)?.id_room)
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtras(bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}