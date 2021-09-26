package id.ac.telkomuniversity.mrrezki.utils

import android.app.Activity
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.showToast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun View.viewShow() {
    visibility = View.VISIBLE
}

fun View.viewHide() {
    visibility = View.GONE
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)