# Kotlin-Architecture
In this project we demonstrate MVVM Architecture with Data Binding and Navigation Componant

### MVVM Architecture and Viewmodel Lifecycle
![](https://res.cloudinary.com/karan-media/image/upload/c_scale,w_420/v1591362329/Android/MVVM/final-architecture_yhiun7.png) ![](https://res.cloudinary.com/karan-media/image/upload/v1591530905/Android/MVVM/viewmodel-lifecycle_mcty6g.png)

## Application Structure 
    app (Main Module)
    |__ KotlinArchitecture.kt (Application class)
    |__ api
    |____ APIManager.kt (Manages OKHTTP and Retrofit instance)
    |__ base
    |____ BaseActivity.kt
    |____ DataBindingBaseActivity.kt (BaseActivity with DataBinding and Viewmodel)
    |____ BaseFragment.kt
    |____ DataBindingBaseFragment.kt (BaseFragment with DataBinding and Viewmodel)
    |____ BaseViewModel.kt
    |____ ProgressDialog.kt
    |____ ItemClickListener.kt (Interface for manage click listener)
    |__ ui
    |____ splash
    |______ SplashActivity.kt
    |____ home
    |______ HomeActivity.kt
    |__ utils
    |____ preferences
    |______ ApplicationPreferences.kt
    |______ PreferenceConstant.kt (Preference Constatnt contains all preference key)
    |______ SharedPreferences.kt (Manages all the put and get preference)
    |____ CommonUtils.kt
    |____ EncryptionUtils.kt (Encription Util for incrept data into MD5)
    |____ LogUtil.kt ( Manage Logs)
    |____ ViewUtil.kt
    
    registration (Dynamic Feature Module)
    |__ api
    |____ request
    |______ LoginRequestModel.kt
    |____ response
    |______ UserModel.kt
    |____ RegistrationInterceptor.kt (Registration API Interface)
    |__ ui
    |____ forgot_password
    |______ ForgotPasswordFragment.kt
    |______ ForgotPasswordRepository.kt
    |______ ForgotPasswordViewModel.kt
    |____ login
    |______ LoginFragment.kt
    |______ LoginRepository.kt
    |______ LoginViewModel.kt
    |____ signup
    |______ SignupFragment.kt
    |______ SignupRepository.kt
    |______ SignupViewModel.kt
    |__ RegistrationActivity.kt
    
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

 
