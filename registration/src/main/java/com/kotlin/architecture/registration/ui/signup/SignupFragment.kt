package com.kotlin.architecture.registration.ui.signup

import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.textview.MaterialTextView
import com.kotlin.architecture.base.DataBindingBaseFragment
import com.kotlin.architecture.base.ItemClickListener
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.databinding.FragmentSignupBinding
import com.kotlin.architecture.utils.ViewUtil

class SignupFragment : DataBindingBaseFragment<FragmentSignupBinding, SignupViewModel>(SignupViewModel::class.java), ItemClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_signup
    }

    override fun initializeController() {
        setupUI()

        // set item click listener with data binding
        binding.itemClickListener = this@SignupFragment

        // set LoginViewModel
        binding.signupViewModel = viewModel
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

    override fun onItemClickListener(view: View) {
        when(view.id) {
            R.id.image_back -> requireActivity().onBackPressed()
        }
    }
}