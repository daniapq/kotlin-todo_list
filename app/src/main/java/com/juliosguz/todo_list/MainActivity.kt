package com.juliosguz.todo_list

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.juliosguz.todo_list.Adapters.ToDoTaskAdapter
import com.juliosguz.todo_list.Classes.ToDoTask
import com.juliosguz.todo_list.database.Constants
import com.juliosguz.todo_list.interfaces.ItemRowListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ItemRowListener {

    lateinit var firebaseDb: DatabaseReference
    var toDoTaskList: MutableList<ToDoTask>? = null
    lateinit var adapter: ToDoTaskAdapter
    private var listViewItems: ListView? = null
    var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            addDataToList(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("Database error: ", databaseError.toException())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        listViewItems = findViewById(R.id.list_view)
        fab.setOnClickListener {
            showTaskItemDialog()
        }
        getList()
    }

    fun getList() {
        firebaseDb = FirebaseDatabase.getInstance().reference
        toDoTaskList = mutableListOf<ToDoTask>()
        adapter = ToDoTaskAdapter(this, toDoTaskList!!)
        listViewItems!!.setAdapter(adapter)
        firebaseDb.orderByKey().addListenerForSingleValueEvent(itemListener)
    }

    fun showTaskItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setTitle("Introduzca su tarea")
        alert.setView(itemEditText)
        alert.setPositiveButton(R.string.save) { _, _ ->
            val task = ToDoTask.create()
            task.text = itemEditText.text.toString()
            task.done = false
            val newTask = firebaseDb.child(Constants.FIREBASE_COLLECTION).push()
            task.objectId = newTask.key
            newTask.setValue(task)
            getList()
            Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton(R.string.cancel) { _, _ ->   }
        alert.show()
    }

    fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        if (items.hasNext()) {
            val toDoListindex = items.next()
            val itemsIterator = toDoListindex.children.iterator()
            while (itemsIterator.hasNext()) {
                val currentItem = itemsIterator.next()
                Log.d("Hello", currentItem.toString())
                val todoItem = ToDoTask.create()
                val map = currentItem.getValue() as HashMap<String, Any>
                todoItem.objectId = currentItem.key
                todoItem.done = map.get("done") as Boolean?
                todoItem.text = map.get("text") as String?
                toDoTaskList!!.add(todoItem);
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun modifyItemState(itemObjectId: String, isDone: Boolean) {
        val itemReference = firebaseDb.child(Constants.FIREBASE_COLLECTION).child(itemObjectId)
        itemReference.child("done").setValue(isDone);
    }

    override fun onItemDelete(itemObjectId: String) {
        val itemReference = firebaseDb.child(Constants.FIREBASE_COLLECTION).child(itemObjectId)
        itemReference.removeValue()
        getList()
    }

}
