package com.juliosguz.todo_list

class ToDoTask {

    var text: String? = null
    var done: Boolean? = false

    companion object Factory {
        fun create(): ToDoTask = ToDoTask()
    }

}