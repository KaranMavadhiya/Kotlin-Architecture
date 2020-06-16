package com.kotlin.architecture.utils

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kotlin.architecture.R
import java.util.regex.Pattern

object CommonUtils{

    /**
     * Apply specific style to specific text in string
     */
    fun applySpanPrimaryColor(textView: TextView, spanText: String, selectedText: String) {
        val spannableString = SpannableString(spanText)
        val startIndex = spanText.indexOf(selectedText)
        val endIndex = startIndex + selectedText.length

        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    textView.context,
                    R.color.colorPrimary
                )
            ), startIndex, endIndex, 0
        )
        textView.text = spannableString
    }

    /**
     * Validates Email Address
     *
     * @param emailAddress email id to be verified
     * @return true valid email id, false invalid emailAddress
     */
    fun isValidEmailAddress(emailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    /**
     * Validate password
     * ^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$
     * ^(?=.*?[A-Z]          # At least one upper case A-Z
     * (?=.*?[a-z])          # At least one lower case a-z
     * (?=.*?[0-9])          # At least one digit 0-9
     * (?=.*?[#?!@$%^&*-])	 # At least one special character
     * .{8,20}	             # Minimum eight and Maximum 20 in length
     *
     * @param password Password string
     * @return boolean true if password match above condition else false
     */
    fun isValidPassword(password: String): Boolean {
        val pattern =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,20}\$")
        return pattern.matcher(password).matches()
    }

    /**
     * Validate Number
     * (^[+]?[0-9]{6,15}$)
     * (			 # Start of group
     * [+]?[0-9]     # Start with + or 0-9
     * {6,15}        # Length at least 6 characters and maximum of 15
     * )			 # End of group
     * @param phone phone number string
     * @return boolean true if phone number match above condition else false
     */
    fun isValidPhoneNumber(phone: String): Boolean {
        val pattern = Pattern.compile("(^[+]?[0-9]{6,15}$)")
        return pattern.matcher(phone).matches()
    }
}
