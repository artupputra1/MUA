package com.example.mua.chat

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mua.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mua_list_chat.*

class MuaListChatActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private lateinit var mAdapter: FirestoreRecyclerAdapter<ListChat, ChatViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mUsersCollection = mFirestore.collection("room_chat")
    private val mQuery = mUsersCollection.whereEqualTo("id_user", "1")
    var arrayList = ArrayList<ListChat>()
    private var adapter: MuaListChatAdapter? = null
    var name_user = ""
    var name_provider = ""
    var id_chat = ""
    val db = FirebaseFirestore.getInstance()
    var size = 0
    var sizeChat = 0
    var sharedpreferences: SharedPreferences? = null
    val my_shared_preferences = "mua"
    var id_user = ""


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mua_list_chat)

        val sharedPreference =  getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE)
        id_user = sharedPreference?.getString("id_provider", "").toString()
        println(id_user)
        rvListChat.layoutManager = LinearLayoutManager(this)
        initView()
        readData(db)

    }

    private fun initView() {
        supportActionBar?.title = "Chat"
    }

    private fun setupAdapter() {
        //set adapter yang akan menampilkan data pada recyclerview
        val options = FirestoreRecyclerOptions.Builder<ListChat>()
                .setQuery(mQuery, ListChat::class.java)
                .build()

        print(options)


        mAdapter = object : FirestoreRecyclerAdapter<ListChat, ChatViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
                return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_chat, parent, false))
            }

            override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int, model: ListChat) {
                viewHolder.bindItem(model)

            }
        }
        mAdapter.notifyDataSetChanged()
        rvListChat.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        //mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        //mAdapter.stopListening()
    }




    fun readData(db: FirebaseFirestore) {
        mUsersCollection.whereEqualTo("id_mua", id_user).get().addOnCompleteListener { task ->
            if (task.isComplete) {
                println("SIZE " +task.result!!.size())
                sizeChat = task.result!!.size()
                for (document in task.result!!) {
                    db.collection("user").whereEqualTo("uid",document.data["id_user"]).get().addOnCompleteListener { t->
                        if(t.isComplete) {
                            for (documentProvider in t.result!!) {
                                println("FIELD : " + document.data)
                                println("PROVIDER : " + documentProvider.data)
                                arrayList.add(
                                        ListChat(
                                                document.data["id_room"].toString(),
                                                documentProvider.data["name"].toString(),
                                                document.data["id_mua"].toString()
                                        )
                                )
                                size = size + 1;
                                if (size == sizeChat) {
                                    println("Array " + arrayList)
                                    val adapter =
                                            MuaListChatAdapter(
                                                    applicationContext,
                                                    arrayList
                                            )
                                    adapter.notifyDataSetChanged()
                                    rvListChat.adapter = adapter
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}