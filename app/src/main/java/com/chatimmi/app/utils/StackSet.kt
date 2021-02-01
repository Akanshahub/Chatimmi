package com.chatimmi.app.utils

import java.util.*

class StackSet<T> {
    private val stack: MutableList<T> = ArrayList()
    val size: Int
        get() = stack.size

    // Push element into stack
    fun push(e: T) {
        val index = stack.indexOf(e)
        if (index != -1) {
            stack.removeAt(index)
        }
        stack.add(e)
    }

    // Pop element from stack
    fun pop(): T? {
        return if (size > 0) {
            stack.removeAt(size - 1)
        } else {
            null
        }
    }

    // Look at upper element of stack, don't pop it
    fun peek(): T? {
        return if (size > 0) {
            stack[size - 1]
        } else {
            null
        }
    }
}