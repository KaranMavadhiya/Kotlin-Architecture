# Kotlin-Architecture
In this project we demonstrate MVVM Architecture with Data Binding and Kotiln Coroutines

### MVVM Architecture and Viewmodel Lifecycle
![](https://res.cloudinary.com/karan-media/image/upload/c_scale,w_420/v1591362329/Android/MVVM/final-architecture_yhiun7.png) ![](https://res.cloudinary.com/karan-media/image/upload/v1591530905/Android/MVVM/viewmodel-lifecycle_mcty6g.png)

## Application Structure 
    com.kotlin.architecture
    |__ KotlinArchitecture.kt (Application class)
    |__ api
    |____ APIManager.kt
    |__ base
    |____ BaseActivity.kt
    |____ BaseFragment.kt
    |____ BaseViewModel.kt
    |____ ProgressDialog.kt
    |__ ui
    |____ splash
    |______ SplashActivity.kt (Splash screen with Kotlin coroutines)
    |__ utils
    |____ preferences
    |______ ApplicationPreferences.kt
    |______ PreferenceConstant.kt
    |______ SharedPreferences.kt
    |____ EncryptionUtils.kt
    |____ LogUtil.kt
    
    
  ## Network Library (Retrofit + Moshi for parsing) 
    com.network
    |__ base
    |____ BaseRequestModel.kt
    |____ BaseResponseModel.kt
    |__ okhttp
    |____ OkHttpClientFactory.kt
    |__ retrofit
    |____ RetrofitClientFactory.kt
        
  * [Update Base Url for API calling](/gradle.properties)
  
In this demo we created 2 build variant debug and release and added applicationIdSuffix to debug build so we can install both application. also we set different app_name with the help of resValue for debug and release so we can identify application with app_name.
 
  
  
  ![](https://res.cloudinary.com/karan-media/image/upload/c_scale,w_900/v1591628475/Android/Code/Screenshot_2020-06-08_at_8.29.20_PM_z8dja9.png)

  * [APIManager.kt](/app/src/main/java/com/kotlin/architecture/api/APIManager.kt)
  ~~~
  object APIManager {

    private const val baseUrl = BuildConfig.API_BASE_URL
    private const val isDebug = true

    fun <T> getRetrofitInstance(interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(baseUrl, interfaceClass,isDebug)
    }

    fun <T> getRetrofitInstance(header: HashMap<String, String>, interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(baseUrl,header, interfaceClass,isDebug)
    }

    fun getOkHttpInstance(): OkHttpClient {
        return OkHttpClientFactory.getInstance(isDebug)
    }

    fun getOkHttpInstance(header: HashMap<String, String>): OkHttpClient {
        return OkHttpClientFactory.getInstance(header, isDebug)
    }
}
  ~~~

 
