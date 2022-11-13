package com.example.mae_project.baseclasses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mae_project.utils.DialogUtils

open class BaseActivity : AppCompatActivity() {
    var mDialog: DialogUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        mDialog = DialogUtils(this)


    }
}
