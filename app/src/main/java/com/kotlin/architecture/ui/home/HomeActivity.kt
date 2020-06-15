package com.kotlin.architecture.ui.home

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kotlin.architecture.R
import com.kotlin.architecture.base.BaseActivity

class HomeActivity : BaseActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initializeComponents() {
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            startActivity("com.kotlin.architecture.registration.RegistrationActivity")
        }

    }
}