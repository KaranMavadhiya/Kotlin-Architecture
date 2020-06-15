package com.kotlin.architecture.registration.ui.signup

import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentSignupBinding
import com.kotlin.architecture.utils.ViewUtil

class SignupFragment : DataBindingBaseFragment<FragmentSignupBinding, SignupViewModel>(SignupViewModel::class.java) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_signup
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
            animateView(binding.inputFirstName,300,500)
            animateView(binding.inputLastName,300,500)
            animateView(binding.inputEmail,300,500)
            animateView(binding.inputPassword,300,500)
            animateView(binding.inputConfirmPassword,300,500)
            animateView(binding.textPasswordHint,300,500)

            animateView(binding.checkTermsService,500,500)
            animateView(binding.buttonSubmit,500,500)
        }
    }
}