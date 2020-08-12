package com.kotlin.architecture.registration.ui.forgot_password

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.base.ItemClickListener
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentForgotPasswordBinding
import com.kotlin.architecture.utils.StatusCode
import com.kotlin.architecture.utils.ViewUtil

class ForgotPasswordFragment : DataBindingBaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java), ItemClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun initializeController() {
        setupUI()


        with(binding) {

            // set item click listener with data binding
            binding.itemClickListener = this@ForgotPasswordFragment

            // set ViewModel to layout
            binding.forgotPasswordViewModel = viewModel

            editEmail.afterTextChanged {
                inputEmail.isErrorEnabled = false
            }
        }
        setObserver()

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
        when(view.id) {
            R.id.image_back -> requireActivity().onBackPressed()
        }
    }

    private fun setObserver() {
        // set BaseViewModel.ViewState observer
        viewModel.forgotPasswordModel.observe(this, Observer { result ->

            if (result is BaseViewModel.ResultOf.InProgress) {
                showProgressDialog(requireActivity().supportFragmentManager)
            }

            result.validate { status, message ->
                when (status) {
                    StatusCode.STATUS_CODE_EMAIL_VALIDATION -> {
                        binding.inputEmail.error = message
                    }
                    StatusCode.STATUS_CODE_INTERNET_VALIDATION -> {
                        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            result.success {
                dismissProgressDialog()
                Toast.makeText(context,   it.message, Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }

            result.failure { status, message, _ ->
                dismissProgressDialog()
                when (status) {
                    StatusCode.STATUS_CODE_SERVER_ERROR -> {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}