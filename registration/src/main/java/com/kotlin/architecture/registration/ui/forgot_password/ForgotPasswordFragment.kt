package com.kotlin.architecture.registration.ui.forgot_password

import android.view.View
import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.base.ItemClickListener
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentForgotPasswordBinding
import com.kotlin.architecture.utils.ViewUtil

class ForgotPasswordFragment : DataBindingBaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java), ItemClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun initializeController() {
        setupUI()

        // set item click listener with data binding
        binding.itemClickListener = this@ForgotPasswordFragment

        // set LoginViewModel
        binding.forgotPasswordViewModel = viewModel
    }

    private fun setupUI() {
        animateUI()
    }

    private fun animateUI() {
        with(ViewUtil) {
            animateView(binding.inputEmail,300,500)
            animateView(binding.buttonSubmit,500,500)
        }
    }

    override fun onItemClickListener(view: View) {

    }
}