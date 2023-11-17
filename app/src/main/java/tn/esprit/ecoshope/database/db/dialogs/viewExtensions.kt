package tn.esprit.ecoshope.database.db.dialogs

import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun View.visible() {
    this.visibility= View.VISIBLE
}

fun View.inVisible() {
    this.visibility= View.INVISIBLE
}

fun View.gone() {
    this.visibility= View.GONE
}
fun Calendar.toFormattedDisplay(): String {
    val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy hh:mm a", Locale.US)
    return simpleDateFormat.format(this.time)
}