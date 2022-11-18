package com.example.dialogsinandroiddemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.system.exitProcess

object Utils {
    fun openConfirmationDialog(
        context: Context,
        title: String,
        message: String,
        toastMessagePositive: String,
        toastMessageNegative: String
    ) {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Confirm") { _, _ -> showToast(context, toastMessagePositive) }
            .setNegativeButton("Cancel") { _, _ ->
                showToast(context, toastMessageNegative)
                // do nothing
            }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun showToast(context: Context, toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    fun showProgressLoading(
        context: Context,
        message: String,
        cancelMessage: String
    ) {
        ProgressDialog(context).apply {
            setMessage(message)
            setCancelable(false)
            setButton("Cancel") { dialog, _ ->
                showToast(context, cancelMessage)
                dialog.dismiss()
            }
            show()
        }
    }

    fun showHobbyDialog(context: Context) {
        val hobbies = arrayOf(
            "Drawing",
            "Bowling",
            "Soccer",
            "Swimming",
            "Cooking",
            "Running",
            "coding",
            "sleeping"
        )

        val builder = AlertDialog.Builder(context).apply {
            setIcon(R.drawable.ic_baseline_gamepad_24)
            setTitle("Select your hobbies")
            setSingleChoiceItems(hobbies, 0) { dialog, position ->
                val selectHobbies = hobbies[position]
                showToast(context, "Selected hobby is: $selectHobbies")
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    fun showSkillsDialog(context: Context) {
        val skills = arrayOf(
            "Android",
            "Kotlin",
            "Java",
            "C",
            "C++",
            "C#",
            "CSS",
            "Python",
            "HTML",
            "JavaScript",
            "Ruby"
        )
        val checkedItems = BooleanArray(skills.size) { false }
        checkedItems[0] = true
        checkedItems[1] = true

        val builder = AlertDialog.Builder(context).apply {
            setIcon(R.drawable.ic_baseline_code_24)
            setTitle(context.getString(R.string.select_your_skills))
            setMultiChoiceItems(skills, checkedItems) { dialog, position, checked ->
                checkedItems[position] = checked
            }
            setPositiveButton("OK") { dialog, _ ->
                val output = java.lang.StringBuilder("Selected skills are: \n")
                for (i in checkedItems.indices) {
                    if (checkedItems[i]) {
                        output.append("${skills[i]}\n")
                    }
                }
                showToast(context, output.toString())
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                val output = java.lang.StringBuilder("Select skills was canceled")
                showToast(context, output.toString())
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    fun logout(context: Context) {
        val builder = AlertDialog.Builder(context).apply {
            setIcon(R.drawable.ic_baseline_logout_24)
            setTitle(context.getString(R.string.logout))
            setPositiveButton("Confirm") { dialog, _ ->
                val output = java.lang.StringBuilder("Logged out!!")
                showToast(context, output.toString())
                dialog.dismiss()
                exitProcess(0)
            }
            setNegativeButton("Cancel") { dialog, _ ->
                val output = java.lang.StringBuilder("Logout Canceled")
                showToast(context, output.toString())
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    fun selectYourBirthday(context: Context, button: Button) {
        val calendar = Calendar.getInstance()
        calendar.after(calendar)
//        calendar.add(Calendar.YEAR, -21)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                button.text = "Date is: ${selectedMonth + 1}/$selectedDay/$selectedYear"
            },
            year,
            month,
            day
        )
//        picker.datePicker.maxDate = calendar.timeInMillis
        picker.show()
    }

    fun selectEventPartyTime(context: Context, button: Button) {
        val calendar = android.icu.util.Calendar.getInstance()
        val hour = calendar.get(android.icu.util.Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(android.icu.util.Calendar.MINUTE)

        val timePicker = TimePickerDialog(
            context,
            { _: TimePicker, selectHour: Int, selectMinute: Int ->
                button.text = "Event time is $selectHour: $selectMinute"
            },
            hour,
            minutes,
            true
        )
        timePicker.show()
    }

    fun validateLogin(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.length > 6 && password.isNotEmpty()
    }
}