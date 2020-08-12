package com.kotlin.architecture.ui.home

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.kotlin.architecture.R
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.base.ItemClickListener
import com.kotlin.architecture.databinding.FragmentHomeBinding
import com.kotlin.architecture.utils.preferences.PreferenceConstant
import com.kotlin.architecture.utils.preferences.getBoolean
import com.kotlin.architecture.utils.preferences.getString
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class HomeFragment : DataBindingBaseFragment<FragmentHomeBinding, BaseViewModel> (BaseViewModel::class.java), ItemClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initializeController() {
        // set item click listener
        binding.itemClickListener = this
    }

    override fun onItemClickListener(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                Navigation.findNavController(binding.buttonLogin).navigate(R.id.action_homeFragment_to_registrationActivity)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == REGISTRATION_RESULT_CODE && PreferenceConstant.IS_USER_LOGIN.getBoolean(false)!!) {
                val jsonUserModel = PreferenceConstant.USER_MODEL.getString(null)
                if (!jsonUserModel.isNullOrBlank()) {

                    val adapter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(UserModel::class.java)
                    val userModel: UserModel? = adapter.fromJson(jsonUserModel)

                    Log.d(TAG, "User Name : ${userModel?.name}")
                    Log.d(TAG, "Email Address : ${userModel?.email}")

                    Toast.makeText(requireContext(), "Welcome ${userModel?.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        val TAG = HomeFragment::class.java.simpleName
        const val REGISTRATION_RESULT_CODE: Int = 1000
    }
}