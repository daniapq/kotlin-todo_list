package com.juliosguz.todo_list

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var toDoTaskList: ArrayList<ToDoTask>? = null
    lateinit var adapter: ToDoTaskAdapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        listViewItems = findViewById(R.id.list_view)
        fab.setOnClickListener {
            showTaskItemDialog()
        }
        toDoTaskList = ArrayList<ToDoTask>()
        initializeList()
    }

    private fun initializeList() {
        adapter = ToDoTaskAdapter(this, toDoTaskList!!)
        listViewItems!!.setAdapter(adapter)
    }

    fun showTaskItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setTitle("Introduzca su tarea")
        alert.setView(itemEditText)
        alert.setPositiveButton(R.string.save) { _, _ ->
            val todoItem = ToDoTask.create()
            todoItem.text = itemEditText.text.toString()
            todoItem.done = false
            toDoTaskList?.add(todoItem)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show()
        }
        alert.setNegativeButton(R.string.cancel) { _, _ ->   }
        alert.show()
    }

}
