package com.juliosguz.todo_list.Classes

class ToDoTask {

    var text: String? = null
    var done: Boolean? = false
    var objectId: String? = null

    companion object Factory {
        fun create(): ToDoTask = ToDoTask()
    }

}