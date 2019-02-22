package com.juliosguz.todo_list.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.juliosguz.todo_list.Classes.ToDoTask
import com.juliosguz.todo_list.R
import com.juliosguz.todo_list.interfaces.ItemRowListener

class ToDoTaskAdapter(context: Context, toDoItemList: MutableList<ToDoTask>) : BaseAdapter() {

    private var rowListener: ItemRowListener = context as ItemRowListener
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoItemList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val objectId: String = itemList.get(position).objectId as String
        val itemText: String = itemList.get(position).text as String
        val done: Boolean = itemList.get(position).done as Boolean
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
        vh.isDone.isChecked = done
        vh.isDone.setOnClickListener {
            rowListener.modifyItemState(objectId, !done)
        }
        vh.ibDeleteObject.setOnClickListener {
            rowListener.onItemDelete(objectId)
        }
        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById<TextView>(R.id.task_text)
        val isDone: CheckBox = row!!.findViewById<CheckBox>(R.id.is_done)
        val ibDeleteObject: ImageButton = row!!.findViewById<ImageButton>(R.id.delete_task)
    }

}