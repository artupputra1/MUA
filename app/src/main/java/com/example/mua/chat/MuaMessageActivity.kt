package com.example.mua.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mua.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_mua_message.*
import java.text.SimpleDateFormat
import java.util.*

class MuaMessageActivity : AppCompatActivity() {
    var chat_id = ""
    var last_date = ""
    private var rootRef: FirebaseFirestore? = null
    private var fromUid: String? = ""
    private var adapter: MessageAdapter? = null
    var sharedpreferences: SharedPreferences? = null
    val my_shared_preferences = "mua"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mua_message)

        val bundle = intent.extras
        chat_id = bundle!!.getString("chat_id").toString()

        supportActionBar?.title = "List Pesan"

//        val sharedPreference =  getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE)
//        id_user = sharedPreference?.getString("id_provider", "").toString()

        fromUid = "0"
        println(chat_id)
        rootRef = FirebaseFirestore.getInstance()

        ivSend.setOnClickListener {
            val messageText = edChat.text.toString()
            val message = Message(fromUid!!, messageText)
            rootRef!!.collection("message").document(chat_id).collection("messages").add(message)
            edChat.text.clear()
        }

        rvMessage.layoutManager = LinearLayoutManager(this)
        val query = rootRef!!.collection("message").document(chat_id).collection("messages").orderBy("send_at", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()

        adapter = MessageAdapter(options)
        rvMessage.adapter = adapter

    }

    inner class MessageViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setMessage(message: Message) {
            val chat = view.findViewById<TextView>(R.id.tvChat)
            val date = view.findViewById<TextView>(R.id.tvDate)
            val tvGroupDate = view.findViewById<TextView>(R.id.tvGroupDate)
            val icon = view.findViewById<ImageView>(R.id.ivChatIcon)
            if (message.send_at.toString().equals("null")) {
                //date.visibility = View.INVISIBLE
                date.text = "Sending... "
                Glide.with(applicationContext).load(R.drawable.ic_chat_delay).into(icon)
            }
            else {
                val timestamp = message.send_at
                val milliseconds = timestamp!!.seconds * 1000 + timestamp.nanoseconds / 1000000
                val sdf = SimpleDateFormat("hh:mm")
                val netDate = Date(milliseconds)
                val valDate = sdf.format(netDate).toString()

                val groupDate = SimpleDateFormat("yyyy-MM-dd")
                val valueDate = groupDate.format(netDate).toString()

                if (last_date.equals(valueDate)) {
                    tvGroupDate.visibility = View.GONE
                }
                else {
//                    tvGroupDate.visibility = View.VISIBLE
                    tvGroupDate.text = valueDate.toString()
                    last_date = valueDate.toString()
                }
                //date.visibility = View.VISIBLE
                date.text = valDate
                Glide.with(applicationContext).load(R.drawable.ic_chat_success).into(icon)

            }
            chat.text = message.text
        }
    }

    inner class MessageAdapter internal constructor(options: FirestoreRecyclerOptions<Message>) : FirestoreRecyclerAdapter<Message, MessageViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            return if (viewType == R.layout.item_message_to) {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_to, parent, false)
                MessageViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_from, parent, false)
                MessageViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
            holder.setMessage(model)
        }

        override fun getItemViewType(position: Int): Int {
            return if (fromUid != getItem(position).fromUid) {
                R.layout.item_message_to
            } else {
                R.layout.item_message_from
            }
        }

        override fun onDataChanged() {
            rvMessage.layoutManager?.scrollToPosition(itemCount - 1)
        }
    }


    override fun onStart() {
        super.onStart()
        if (adapter != null) {
            adapter!!.startListening()
        }
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}