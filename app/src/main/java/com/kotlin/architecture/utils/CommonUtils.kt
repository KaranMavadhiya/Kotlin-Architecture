package com.kotlin.architecture.utils

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kotlin.architecture.R
import com.kotlin.architecture.utils.preferences.PreferenceConstant
import com.kotlin.architecture.utils.preferences.getString
import com.kotlin.architecture.utils.preferences.putString
import java.util.*
import java.util.regex.Pattern

object CommonUtils{

    /**
     * As the requirement for most of applications is to identify a particular installation and not a physical device,
     * a good solution to get unique id for an user if to use UUID class. The following solution has been presented by
     * Reto Meier from Google in a Google I/O presentation
     *
     * @return The value from shared preferences, or the provided default.
     */
    @get:Synchronized
    val uniqueId: String
        get() {
            var uniqueID = PreferenceConstant.UNIQUE_ID.getString(null)
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString()
                PreferenceConstant.UNIQUE_ID.putString(uniqueID)
            }
            return uniqueID
        }

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
