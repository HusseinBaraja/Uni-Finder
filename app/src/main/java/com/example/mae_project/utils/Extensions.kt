package com.example.mae_project.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mae_project.R

inline fun <reified T> Context.startNewActivity(destination: Class<T>, isFinish: Boolean) {
    startActivity(Intent(this, destination))
    if (isFinish) {
        (this as Activity).finish()
    }
}

inline fun <reified T> Context.startNewActivity(
    destination: Class<T>,
    values: (Intent) -> Unit,
    isFinish: Boolean
) {
    startActivity(Intent(this, destination::class.java).also {
        values(it)
    })
    if (isFinish) {
        (this as Activity).finish()
    }
}

fun View.beVisible() {
    visibility = View.VISIBLE
}

fun View.beGone() {
    visibility = View.GONE
}

fun ImageView.setImage(resource: Any?) {
    Glide.with(this).load(resource ?: R.drawable.school).error(R.drawable.school).into(this)
}

fun Activity.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}