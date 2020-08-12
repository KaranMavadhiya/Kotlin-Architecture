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

# Converting Android App Bundle (.aab) to APK File

After generating the Android App Bundle from Android Studio or from your favorite IDE, you need to first test how the generated APKs behave when deployed on local mobiles. The quickest way to do this is converting .aab file to APK and by installing it in the traditional way.

To extract APK files from Android App Bundle, we’re using bundletool, which is an opensource command-line tool available on GitHub. This tool is for manipulating the Android App Bundle to generate APK set archive (.apks) file. Once the “.apks” file is generated using bundletool, we can extract the APK files by changing the file to .zip format.

### Here is how you can use bundletool to generate .apks archive file from Android App Bundle and extract .apk files:

* Step 1 Download [bundletool](https://github.com/google/bundletool/releases) command-line tool
    
    Download the latest version of bundletool jar file from GitHub and save it inside a folder on Desktop. At the time of writing this article, the latest version of bundletool is 1.0.0 and for simplicity, change the file name from bundletool-all-1.0.0.jar to bundletool.jar

* Step 2 Generate APK Set Archive (.apks) file from .aab

    Copy your Android App Bundle (.aab) file to the same folder where you have downloaded the bundletool.jar in previous steps. This is very important and you must make sure both bundletool.jar and .aab file is in the same folder every time you run the bundletool command.
    
    Now open the Command Prompt (Windows) or Terminal (Mac) and change the directory to your Desktop folder using the CD command. To generate an APK set archive (.apks) file containing different APK files of all devices configurations your app supports, run the build-apks command as shown below.
    
    ```
    java -jar bundletool.jar build-apks --bundle=nhl.aab --output=nhl.apks
    ```
    After running the above command, a new file with the name “debug.apks” will be created in the same folder. This is an APK archive file and by extracting it, you will get multiple APK files for different device configurations.
    
 * Step 3 Extract APK Files

    Once the “debug.apks” file is generated, Rename the “debug.apks” file to “debug.zip” and extract it normally using ZIP file extractor. You will now find a lot of APK files in splits and standalones folders.
    
    Now you have successfully converted the .aab file to .apk files and you can install them on your device normally. Technically, this is how you can install .aab file on an Android device.

    Wait it’s not over yet. Did you notice any problem with the above command? Yes, it is generating plenty of Debug APK files and you are not sure about which one you need to install? What if you want signed APKs or a universal signed APK from Android App Bundle. Fortunately, bundletool comes with different flags for extracting required APK from .aab file.

    Here is a table with different bundletool flags and its usage.

    Flag | Status | Explanation
    ------------ | ------------- | -------------
    –ks= | Optional | Keystore Path
    –ks-pass=pass: | Optional | Keystore password
    –ks-key-alias= | Optional | Key alias
    –key-pass=pass: | Optional | Key alias password
    –mode=universal | Optional | To generate a single Universal APK file
    –overwrite | Optional | Overwrites the output .apks file if same file name exists
    
### Let’s take a look at how to use these flags along with bundletool command.

* Generating Signed APKs using your Keystore
    ```
    java -jar bundletool.jar build-apks --bundle=release.aab --output=release.apks --ks=keystore.jks --ks-pass=pass:your_keystore_password --ks-key-alias=your_key_alias --key-pass=pass:your_key_password
    ```
    This will again generate an “release.apks” file and you need to rename it to “release.zip” and extract the zip file to see signed apks in both splits and standalone folders.
    
* Generating a Universal APK from Android App Bundle
    ```
    java -jar bundletool.jar build-apks --bundle=debug.aab --output=debug.apks --mode=universal
    ```
    This command will generate debug.apks file and again you need to rename it to debug.zip and extract it to get universnal.apk file.

* Overwriting existing .apks file
    If you want to overwrite the old .apks file with the new one, you need to just add the –overwrite flag to your command as shown below.
    ```
    java -jar bundletool.jar build-apks --bundle=debug.aab --output=debug.apks --overwrite
    ```
* Extracting Signed Universal APK file from Android App Bundle
    Now let’s combine all the above flags to generate a single universal APK file and also overwrite the existing APK archive if already available.
    ```
    java -jar bundletool.jar build-apks --bundle=release.aab --output=release.apks --overwrite --mode=universal -ks=keystore.jks --ks-pass=pass:your_keystore_password --ks-key-alias=your_key_alias --key-pass=pass:your_key_password
    ```
    After running this command, you will need to rename the generated “release.apks” file to “release.zip” and extract it to find the signed “universal.apk” file.
