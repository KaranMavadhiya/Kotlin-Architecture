package com.kotlin.architecture.registration.ui.signup

import android.content.DialogInterface
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.android.material.textview.MaterialTextView
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.base.ItemClickListener
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentSignupBinding
import com.kotlin.architecture.utils.StatusCode
import com.kotlin.architecture.utils.ViewUtil
import com.network.base.BaseResponseModel

class SignupFragment : DataBindingBaseFragment<FragmentSignupBinding, SignupViewModel>(SignupViewModel::class.java), ItemClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_signup
    }

    override fun initializeController() {
        setupUI()

        with(binding) {

            // set item click listener with data binding
            itemClickListener = this@SignupFragment

            // set ViewModel to layout
            signupViewModel = viewModel

            editFullName.afterTextChanged {
                inputFullName.isErrorEnabled = false
            }

            editEmail.afterTextChanged {
                inputEmail.isErrorEnabled = false
            }

            editPassword.afterTextChanged {
                inputPassword.isErrorEnabled = false
            }
        }

        setObserver()
    }

    private fun setupUI() {
        animateUI()

        // set terms of service and privacy policy
        applySpanTermsConditionPrivacyPolicy(binding.textTermsOfService)
    }

    private fun animateUI() {
        with(ViewUtil) {
            animateView(binding.inputFullName,300,500)
            animateView(binding.inputEmail,300,500)
            animateView(binding.inputPassword,300,500)
            animateView(binding.textPasswordHint,300,500)

            animateView(binding.textTermsOfService,500,500)
            animateView(binding.buttonSubmit,500,500)
        }
    }

    /**
     * Apply specific style to specific text in string
     */
    private fun applySpanTermsConditionPrivacyPolicy(termsCondition: MaterialTextView) {
        termsCondition.movementMethod = LinkMovementMethod.getInstance()

        val spannableString = SpannableString(getString(R.string.str_terms_of_service_and_privacy_policy))

        val startIndexTC = spannableString.indexOf( getString(R.string.str_terms_and_service))
        val endIndexTC: Int = startIndexTC + getString(R.string.str_terms_and_service).length

        val startIndexPP = spannableString.indexOf( getString(R.string.str_privacy_policy))
        val endIndexPP: Int = startIndexPP + getString(R.string.str_privacy_policy).length

        // clickable text
        val clickableSpanTC: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(context,"Terms Of Service Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        spannableString.setSpan(clickableSpanTC, startIndexTC, endIndexTC, 0)

        // clickable text
        val clickableSpanPP: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(context,"Privacy Policy Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        spannableString.setSpan(clickableSpanPP, startIndexPP, endIndexPP, 0)

        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#F68510")), startIndexTC, endIndexTC, 0)

        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#F68510")), startIndexPP, endIndexPP, 0)

        termsCondition.text = spannableString
    }

    private fun setObserver() {
        // set BaseViewModel.ViewState observer
        viewModel.stateLiveData.observe(this, Observer(this::manageState))
    }

    private fun manageState(viewState: BaseViewModel.ViewState) {
        when (viewState) {
            BaseViewModel.ViewState.Idle -> {
                with(binding) {
                    inputEmail.error = null
                    inputPassword.error = null
                }
            }
            BaseViewModel.ViewState.InProgress -> {
                showProgressDialog(requireActivity().supportFragmentManager)
            }
            is BaseViewModel.ViewState.Validate -> {
                dismissProgressDialog()

                when (viewState.status) {
                    StatusCode.STATUS_CODE_NAME_VALIDATION -> {
                        binding.inputFullName.error = getString(viewState.message)
                    }
                    StatusCode.STATUS_CODE_EMAIL_VALIDATION -> {
                        binding.inputEmail.error = getString(viewState.message)
                    }
                    StatusCode.STATUS_CODE_PASSWORD_VALIDATION -> {
                        binding.inputPassword.error = getString(viewState.message)
                    }
                    StatusCode.STATUS_CODE_INTERNET_VALIDATION -> {
                        Toast.makeText(context, getString(viewState.message), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is BaseViewModel.ViewState.Failed -> {
                dismissProgressDialog()

                when (viewState.status) {
                    StatusCode.STATUS_CODE_SERVER_ERROR -> {
                        Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is BaseViewModel.ViewState.Succeed<*> -> {
                dismissProgressDialog()

                if (viewState.data != null && viewState.data is BaseResponseModel<*>) {

                    // Reference : https://www.journaldev.com/309/android-alert-dialog-using-kotlin

                    val builder = AlertDialog.Builder(requireContext())
                    with(builder) {
                        setTitle(com.kotlin.architecture.R.string.app_name)
                        setMessage((viewState.data as BaseResponseModel<*>).message)
                        setPositiveButton(getString(R.string.str_ok), DialogInterface.OnClickListener(function = signupSuccessButtonClick))
                        return@with show()
                    }

                }
            }
        }
    }

    private val signupSuccessButtonClick = { _: DialogInterface, _: Int ->
        requireActivity().onBackPressed()
    }

    override fun onItemClickListener(view: View) {
        when(view.id) {
            R.id.image_back -> requireActivity().onBackPressed()
        }
    }
}