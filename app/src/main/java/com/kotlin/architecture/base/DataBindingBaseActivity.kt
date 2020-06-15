package com.kotlin.architecture.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider

abstract class DataBindingBaseActivity< DB : ViewDataBinding, VM : BaseViewModel>(private val viewModelClass: Class<VM>)  : AppCompatActivity(),  View.OnClickListener {

    /**
     * lastClickedTime contains last clicked time of view to prevent double click
     */
    private var lastClickedTime: Long = 0

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    /**
     * abstract method for set view
     *
     * @return int (R.layout.XXX)
     */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * abstract method to initialize components
     */
    abstract fun initializeComponents()

    /**
     * ContentLoadingProgressBar in ProgressDialog
     */
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeComponents()
    }

    override fun onClick(view: View) {
        /*
         * Logic for hide keyboard on click
         */
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        /*
         * Logic to Prevent the Launch Twice if LoginRequestModel makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < MAX_CLICK_INTERVAL) {
            return
        }
        lastClickedTime = SystemClock.elapsedRealtime()

    }

    /**
     * @param className Name of the next Activity (NextActivity::class.java)
     */
    open fun startActivity(className: Class<*>?) {
        startActivity(Intent(applicationContext, className))
    }

    /**
     * @param classPath Path of the next Activity (xxx.xxx.NextActivity)
     */
    open fun startActivity(classPath: String) {
        startActivity(Intent(Intent.ACTION_VIEW).setClassName(applicationContext, classPath))
    }

    /**
     * @param className  Name of the next Activity (NextActivity::class.java)
     * @param resultCode Activity result code
     */
    open fun startActivity(className: Class<*>?, resultCode: Int) {
        startActivityForResult(Intent(applicationContext, className), resultCode)
    }

    /**
     * @param classPath  Path of the next Activity (xxx.xxx.NextActivity)
     * @param resultCode Activity result code
     */
    open fun startActivity(classPath: String, resultCode: Int) {
        startActivityForResult( Intent(Intent.ACTION_VIEW).setClassName( applicationContext, classPath ), resultCode)
    }

    /**
     * @param className Name of the next Activity (NextActivity::class.java)
     * @param bundle  Bundle which need to pass next Activity
     */
    open fun startActivity(className: Class<*>?, bundle: Bundle?) {
        val intent = Intent(applicationContext, className)
        intent.putExtras(bundle!!)
        startActivity(intent)
    }

    /**
     * @param classPath Path of the next Activity (xxx.xxx.NextActivity)
     * @param bundle  Bundle which need to pass next Activity
     */
    open fun startActivity(classPath: String, bundle: Bundle?) {
        val intent = Intent(Intent.ACTION_VIEW).setClassName(applicationContext, classPath)
        intent.putExtras(bundle!!)
        startActivity(intent)
    }

    /**
     * @param intent     Activity Intent
     * @param resultCode Activity result code
     */
    open fun startActivityForResultBack(intent: Intent?, resultCode: Int) {
        setResult(resultCode, intent)
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    /**
     * @param fragment : Fragment that need to add
     * @param frameId  : container id
     */
    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    /**
     * @param fragment : Fragment that need to replace
     * @param frameId  : container id
     */
    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    /**
     * @param supportFragmentManager : FragmentManager
     *
     * This method is used to show progress dialog
     */
    fun showProgressDialog(supportFragmentManager: FragmentManager?) {
        progressDialog = ProgressDialog.showProgressDialog(supportFragmentManager)
    }

    /**
     * This method is used to hide progress dialog
     */
    fun dismissProgressDialog() {
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

    companion object {
        /**
         * MAX_CLICK_INTERVAL contains Max time interval to prevent double click
         */
        const val MAX_CLICK_INTERVAL: Long = 1000
    }
}