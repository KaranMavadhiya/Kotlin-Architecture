package com.kotlin.architecture.registration.ui.forgot_password

import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentForgotPasswordBinding
import com.kotlin.architecture.utils.ViewUtil

class ForgotPasswordFragment : DataBindingBaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun initializeController() {
        setupUI()
    }

    private fun setupUI() {
        animateUI()
    }

    private fun animateUI() {
        with(ViewUtil) {
            animateView(binding.imageLogo,300,500)
            animateView(binding.inputEmail,300,500)

            animateView(binding.buttonSubmit,500,500)
        }
    }
}