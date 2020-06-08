# Kotlin-Architecture
In this project we demonstrate MVVM Architecture with Data Binding and Kotiln Coroutines

### MVVM Architecture
![](https://res.cloudinary.com/karan-media/image/upload/c_scale,w_466/v1591362329/Android/MVVM/final-architecture_yhiun7.png)

### Viewmodel Lifecycle
![](https://res.cloudinary.com/karan-media/image/upload/v1591530905/Android/MVVM/viewmodel-lifecycle_mcty6g.png)

## Application Structure 
    com.kotlin.architecture
    |__ KotlinArchitecture.kt (Application class)
    |__ base
    |___ [BaseActivity.kt](/app/src/main/java/com/android/kickstart/activities/BaseActivity.kt)
    |___ BaseFragment.kt
    |___ BaseViewModel.kt
    |___ ProgressDialog.kt
    |__ ui
    |___ splash
    |____ SplashActivity.kt (Splash screen with Kotlin coroutines)
    |__ utils
    |___ preferences
    |____ ApplicationPreferences.kt
    |____ PreferenceConstant.kt
    |____ SharedPreferences.kt


