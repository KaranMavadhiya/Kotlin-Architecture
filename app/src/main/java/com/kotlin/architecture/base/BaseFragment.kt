package com.kotlin.architecture.base

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

abstract class BaseFragment : Fragment() {

    /**
     * abstract method for set view
     *
     * @return int (R.layout.XXX)
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * Initialize the components for Fragment's view
     *
     * @param view A View inflated into Fragment
     */
    abstract fun initializeComponent(view: View?)

    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponent(view)
    }

    fun showProgressDialog(supportFragmentManager: FragmentManager?) {
        progressDialog = ProgressDialog.showProgressDialog(supportFragmentManager)
    }

    fun dismissProgressDialog(){
        progressDialog?.dismiss()
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    inline fun <reified T> BaseViewModel.ResultOf<T>.validate(callback: (status: Int, message: String?) -> Unit) {
        if (this is BaseViewModel.ResultOf.Failure) {
            callback(status, message)
        }
    }

    inline fun <reified T> BaseViewModel.ResultOf<T>.success(callback: (value: T) -> Unit) {
        if (this is BaseViewModel.ResultOf.Success) {
            callback(value)
        }
    }

    inline fun <reified T> BaseViewModel.ResultOf<T>.failure(callback: (status: Int, message: String?, throwable: Throwable?) -> Unit) {
        if (this is BaseViewModel.ResultOf.Failure) {
            callback(status, message, throwable)
        }
    }

    inline fun <reified T> convertModelToJson(data: T): String {
        val adapter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(T::class.java)
        return adapter.toJson(data)
    }
}