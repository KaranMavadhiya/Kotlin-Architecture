package com.kotlin.architecture.registration.ui.login

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.base.ItemClickListener
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentLoginBinding
import com.kotlin.architecture.utils.ErrorCode
import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.ViewUtil
import com.kotlin.architecture.utils.preferences.PreferenceConstant
import com.kotlin.architecture.utils.preferences.putBoolean
import com.kotlin.architecture.utils.preferences.putString
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class LoginFragment : DataBindingBaseFragment<FragmentLoginBinding, LoginViewModel>(LoginViewModel::class.java), ItemClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initializeController() {
        // set up UI
        setupUI()

        with(binding) {

            // set item click listener with data binding
            itemClickListener = this@LoginFragment

            // set LoginViewModel
            loginViewModel = viewModel

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
        // add animation
        animateUI()

        //set spannable text
        CommonUtils.applySpanPrimaryColor(binding.textSignUp, getString(R.string.str_don_t_have_an_account), getString(R.string.str_sign_up))
    }

    private fun animateUI() {
        with(ViewUtil) {
            animateView(binding.inputEmail, 300, 500)
            animateView(binding.inputPassword, 300, 500)

            animateView(binding.textForgotPassword, 500, 500)
            animateView(binding.buttonSubmit, 500, 500)

            animateView(binding.viewDivider, 600, 500)
            animateView(binding.textOr, 600, 500)
            animateView(binding.buttonFacebook, 600, 500)
            animateView(binding.buttonGoogle, 600, 500)
            animateView(binding.buttonTwitter, 600, 500)

            animateView(binding.textSignUp, 700, 500)
        }
    }

    override fun onItemClickListener(view: View) {
        when (view.id) {
            R.id.image_back -> requireActivity().onBackPressed()
            R.id.textForgotPassword -> Navigation.findNavController(binding.textForgotPassword)
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            R.id.textSignUp -> Navigation.findNavController(binding.buttonSubmit)
                .navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun setObserver() {
        // set BaseViewModel.ViewState observer
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer(this::manageState))
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
                    ErrorCode.STATUS_CODE_EMAIL_VALIDATION -> {
                        binding.inputEmail.error = getString(viewState.message)
                    }
                    ErrorCode.STATUS_CODE_PASSWORD_VALIDATION -> {
                        binding.inputPassword.error = getString(viewState.message)
                    }
                    ErrorCode.STATUS_CODE_INTERNET_VALIDATION -> {
                        Toast.makeText(context, getString(viewState.message), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            is BaseViewModel.ViewState.Failed -> {
                dismissProgressDialog()

                when (viewState.status) {
                    ErrorCode.STATUS_CODE_SERVER_ERROR -> {
                        Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is BaseViewModel.ViewState.Succeed<*> -> {
                dismissProgressDialog()

                if (viewState.data != null && viewState.data is UserModel) {
                    val userModel = viewState.data as UserModel

                    // Added USER_MODEL in preference
                    PreferenceConstant.USER_MODEL.putString(convertModelToJson(userModel))

                    // Added AUTH_TOKEN in preference
                    PreferenceConstant.AUTH_TOKEN.putString(userModel.accessToken)

                    // Added IS_USER_LOGIN true in preference
                    PreferenceConstant.IS_USER_LOGIN.putBoolean(true)

                    requireActivity().setResult(Activity.RESULT_OK, Intent())
                    requireActivity().finish()
                }
            }
        }
    }

    private inline fun <reified T> convertModelToJson(data: T): String {
        val adapter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(T::class.java)
        return adapter.toJson(data)
    }
}