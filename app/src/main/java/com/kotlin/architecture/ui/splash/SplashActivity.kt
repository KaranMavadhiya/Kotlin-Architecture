package com.kotlin.architecture.ui.splash

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.kotlin.architecture.R
import com.kotlin.architecture.base.BaseActivity
import com.kotlin.architecture.databinding.ActivitySplashBinding
import com.kotlin.architecture.ui.home.HomeActivity
import kotlinx.coroutines.*

class SplashActivity : BaseActivity() , Animation.AnimationListener {

    private lateinit var binding: ActivitySplashBinding

    private val activityScope = CoroutineScope(Dispatchers.Main)

    private fun performNavigation() {
        startActivity(HomeActivity::class.java)
        finish()
    }

    override fun getLayoutRes(): Int {
      return R.layout.activity_splash
    }

    override fun initializeComponents() {
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_splash
        )
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
        activityScope.launch {
            delay(3000)
            performNavigation()
        }
    }

    override fun onAnimationRepeat(animation: Animation) {}

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}