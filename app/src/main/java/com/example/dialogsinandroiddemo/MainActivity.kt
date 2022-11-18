package com.example.dialogsinandroiddemo

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.dialogsinandroiddemo.Utils.logout
import com.example.dialogsinandroiddemo.Utils.openConfirmationDialog
import com.example.dialogsinandroiddemo.Utils.selectEventPartyTime
import com.example.dialogsinandroiddemo.Utils.selectYourBirthday
import com.example.dialogsinandroiddemo.Utils.showHobbyDialog
import com.example.dialogsinandroiddemo.Utils.showProgressLoading
import com.example.dialogsinandroiddemo.Utils.showSkillsDialog
import com.example.dialogsinandroiddemo.Utils.showToast
import com.example.dialogsinandroiddemo.Utils.validateLogin
import com.example.dialogsinandroiddemo.databinding.ActivityMainBinding
import com.example.dialogsinandroiddemo.databinding.CustomLoginDialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        binding.apply {
            btnDelete.setOnClickListener {
                openConfirmationDialog(
                    context = this@MainActivity,
                    getString(R.string.title_delete),
                    getString(R.string.confirmation_message_delete),
                    getString(R.string.deleted_message),
                    getString(R.string.canceled_message)
                )
            }

            btnSave.setOnClickListener {
                openConfirmationDialog(
                    context = this@MainActivity,
                    getString(R.string.title_save),
                    getString(R.string.confirmation_message_save),
                    getString(R.string.saved_message),
                    getString(R.string.canceled_message)
                )
            }
            btnLoadingProgress.setOnClickListener {
                showProgressLoading(
                    this@MainActivity,
                    getString(R.string.loading),
                    getString(R.string.canceled_message)
                )
            }
            btnSelectHobby.setOnClickListener {
                showHobbyDialog(this@MainActivity)
            }
            btnSelectSkills.setOnClickListener {
                showSkillsDialog(this@MainActivity)
            }
            btnLogout.setOnClickListener {
                logout(this@MainActivity)
            }
            btnSelectBirthday.setOnClickListener {
                selectYourBirthday(
                    this@MainActivity,
                    binding.btnSelectBirthday
                )
            }

            btnLoginUsingCustom.setOnClickListener {
                makeCustomLoginDialog(this@MainActivity)
            }

            btnTimePicker.setOnClickListener {
                selectEventPartyTime(
                    this@MainActivity,
                    binding.btnTimePicker
                )
            }

        }
    }

    private fun makeCustomLoginDialog(context: Context) {
        val dialogBinding: CustomLoginDialogBinding =
            CustomLoginDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(context).apply {
            setView(dialogBinding.root)
            setCancelable(false)
        }

        val dialog = builder.create()
        dialogBinding.apply {
            btnLogin.setOnClickListener {
                val email = etTextEmail.text.toString()
                val password = etTextPassword.text.toString()
                if (validateLogin(email, password)) {
                    showToast(this@MainActivity, getString(R.string.login_success))
                    dialog.dismiss()
                } else {
                    showToast(this@MainActivity, getString(R.string.invalid_cred))
                }
            }
            btnBack.setOnClickListener { dialog.dismiss() }
        }
        dialog.show()
        val params = dialog.window?.attributes
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = params
    }
}