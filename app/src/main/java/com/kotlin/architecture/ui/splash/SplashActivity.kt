package com.kotlin.architecture.ui.splash

import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kotlin.architecture.R
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.base.DataBindingBaseActivity
import com.kotlin.architecture.databinding.ActivitySplashBinding
import com.kotlin.architecture.ui.home.HomeActivity

class SplashActivity : DataBindingBaseActivity<ActivitySplashBinding, BaseViewModel>(BaseViewModel::class.java) , Animation.AnimationListener {

    private fun performNavigation() {
        startActivity(HomeActivity::class.java)
        finish()
    }

    override fun getLayoutRes(): Int {
      return R.layout.activity_splash
    }

    override fun initializeComponents() {
        binding.lifecycleOwner = this

        val loadAnimation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.transition_from_top
        )
        // set animation listener
        loadAnimation.setAnimationListener(this)
        // start the animation
        binding.imageLogo.startAnimation(loadAnimation)
    }

    override fun onAnimationStart(animation: Animation) {}

    override fun onAnimationEnd(animation: Animation) {

        val delayMillis: Long = 2000
        /*
         * Handler is used to set some delay on this screen
         */
        Handler().postDelayed({ performNavigation() },delayMillis)

    }

    override fun onAnimationRepeat(animation: Animation) {}

}