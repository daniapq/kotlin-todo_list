package com.juliosguz.todo_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class ToDoTaskAdapter(context: Context, toDoItemList: MutableList<ToDoTask>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var taskList = toDoItemList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemText: String? = taskList.get(position).text
        val done: Boolean? = taskList.get(position).done
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.row_items, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.label.text = itemText
        vh.isDone.isChecked = done!!
        return view
    }
    override fun getItem(index: Int): Any {
        return taskList.get(index)
    }
    override fun getItemId(index: Int): Long {
        return index.toLong()
    }
    override fun getCount(): Int {
        return taskList.size
    }
    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById(R.id.task_text)
        val isDone: CheckBox = row!!.findViewById(R.id.is_done)
    }

}