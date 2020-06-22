package com.kotlin.architecture.ui.home

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kotlin.architecture.R
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.base.DataBindingBaseActivity
import com.kotlin.architecture.databinding.ActivityHomeBinding
import com.kotlin.architecture.utils.Constants
import com.kotlin.architecture.utils.preferences.PreferenceConstant
import com.kotlin.architecture.utils.preferences.getBoolean
import com.kotlin.architecture.utils.preferences.getString

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class HomeActivity : DataBindingBaseActivity<ActivityHomeBinding, BaseViewModel>(BaseViewModel::class.java){

    override fun getLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initializeComponents() {
        setSupportActionBar(binding.toolbar)

        binding.fab

        binding.fab.setOnClickListener {
            startActivity(Constants.REGISTRATION_ACTIVITY, REGISTRATION_RESULT_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == Companion.REGISTRATION_RESULT_CODE && PreferenceConstant.IS_USER_LOGIN.getBoolean(false)!!) {
                val jsonUserModel = PreferenceConstant.USER_MODEL.getString(null)
                if(!jsonUserModel.isNullOrBlank()){
                    val adapter =  Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(UserModel::class.java)
                    val userModel: UserModel? = adapter.fromJson(jsonUserModel)

                    Log.d(TAG, "${userModel?.name}")
                    Log.d(TAG, "${userModel?.email}")

                    Toast.makeText(this, "Welcome ${userModel?.name}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        val TAG = HomeActivity::class.java.simpleName
        const val REGISTRATION_RESULT_CODE : Int = 1000
    }
}