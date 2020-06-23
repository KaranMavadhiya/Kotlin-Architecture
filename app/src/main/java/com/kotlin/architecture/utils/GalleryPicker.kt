package com.kotlin.architecture.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.Fragment

class GalleryPicker {

    /**
     * This method helps to pick image from gallery from [Activity] with default request code 1000 (GalleryPicker.REQUEST_PICK_IMAGE)
     *
     * @param activity Activity to receive result
     */
    fun pickImageFromGallery(activity: Activity) {
        pickImageFromGallery(activity, false, Companion.REQUEST_PICK_IMAGE)
    }

    /**
     * This method helps to pick image from gallery from [androidx.fragment.app.Fragment] with default request code 1000 (GalleryPicker.REQUEST_PICK_IMAGE)
     *
     * @param context  Context
     * @param fragment Fragment to receive result
     * @param allowMultiple  true if need to pick image multiple else false
     */
    fun pickImageFromGallery(context: Context, fragment: Fragment) {
        pickImageFromGallery(context, fragment, false, Companion.REQUEST_PICK_IMAGE)
    }

    /**
     * This method helps to pick image from gallery from [Activity] with @param requestCode (requestCode for result)
     *
     * @param activity    Activity to receive result
     * @param requestCode requestCode for result
     */
    fun pickImageFromGallery(activity: Activity, requestCode: Int) {
        try {
            activity.startActivityForResult(getGalleryImagePicker(false), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(activity, it) }
        }
    }

    /**
     * This method helps to pick image from gallery from [Activity] with default request code 1000 (GalleryPicker.REQUEST_PICK_IMAGE)
     *
     * @param allowMultiple  true if need to pick image multiple else false
     * @param activity Activity to receive result
     */
    fun pickImageFromGallery(activity: Activity, allowMultiple: Boolean) {
       pickImageFromGallery(activity, allowMultiple, Companion.REQUEST_PICK_IMAGE)
    }

    /**
     * This method helps to pick image from gallery from [androidx.fragment.app.Fragment] with default request code 1000 (GalleryPicker.REQUEST_PICK_IMAGE)
     *
     * @param context  Context
     * @param fragment Fragment to receive result
     * @param allowMultiple  true if need to pick image multiple else false
     */
    fun pickImageFromGallery(context: Context, fragment: Fragment, allowMultiple: Boolean) {
       pickImageFromGallery(context, fragment, allowMultiple, Companion.REQUEST_PICK_IMAGE)
    }


    /**
     * This method helps to pick image from gallery from [Activity] with @param requestCode (requestCode for result)
     *
     * @param activity    Activity to receive result
     * @param allowMultiple  true if need to pick image multiple else false
     * @param requestCode requestCode for result
     */
    fun pickImageFromGallery(activity: Activity, allowMultiple: Boolean, requestCode: Int) {
        try {
            activity.startActivityForResult(getGalleryImagePicker(allowMultiple), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(activity, it) }
        }
    }

    /**
     * This method helps to pick image from gallery from [androidx.fragment.app.Fragment] with @param requestCode (requestCode for result)
     *
     * @param context     Context
     * @param fragment    Fragment to receive result
     * @param allowMultiple  true if need to pick image multiple else false
     * @param requestCode requestCode for result
     */
    fun pickImageFromGallery(context: Context, fragment: Fragment, allowMultiple: Boolean, requestCode: Int) {
        try {
            fragment.startActivityForResult(getGalleryImagePicker(allowMultiple), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(context, it) }
        }
    }

    private fun getGalleryImagePicker(allowMultiple: Boolean): Intent? {
        return if (allowMultiple){
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*")
        }
    }


    /**
     * This method helps to pick video from gallery from [Activity] with default request code 2000 (GalleryPicker.REQUEST_PICK_VIDEO)
     *
     * @param activity Activity to receive result
     */
    fun pickVideoFromGallery(activity: Activity) {
        pickVideoFromGallery(activity, Companion.REQUEST_PICK_VIDEO)
    }

    /**
     * This method helps to pick video from gallery from [androidx.fragment.app.Fragment]  with default request code 2000 (GalleryPicker.REQUEST_PICK_VIDEO)
     *
     * @param context  Context
     * @param fragment Fragment to receive result
     */
    fun pickVideoFromGallery(context: Context, fragment: Fragment) {
        pickVideoFromGallery(context, fragment, Companion.REQUEST_PICK_VIDEO)
    }

    /**
     * This method helps to pick video from gallery from [Activity] with @param requestCode (requestCode for result)
     *
     * @param activity    Activity to receive result
     * @param requestCode requestCode for result
     */
    fun pickVideoFromGallery(activity: Activity, requestCode: Int) {
        try {
            activity.startActivityForResult(getGalleryVideoPicker(false), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(activity, it) }
        }
    }

    /**
     * This method helps to pick video from gallery from [Activity] with default request code 2000 (GalleryPicker.REQUEST_PICK_VIDEO)
     *
     * @param allowMultiple  true if need to pick video multiple else false
     * @param activity Activity to receive result
     */
    fun pickVideoFromGallery(activity: Activity, allowMultiple: Boolean) {
       pickVideoFromGallery(activity, allowMultiple, Companion.REQUEST_PICK_VIDEO)
    }

    /**
     * This method helps to pick video from gallery from [androidx.fragment.app.Fragment]  with default request code 2000 (GalleryPicker.REQUEST_PICK_VIDEO)
     *
     * @param context  Context
     * @param fragment Fragment to receive result
     * @param allowMultiple  true if need to pick video multiple else false
     */
    fun pickVideoFromGallery(context: Context, fragment: Fragment, allowMultiple: Boolean) {
        pickVideoFromGallery(context, fragment, allowMultiple, Companion.REQUEST_PICK_VIDEO)
    }

    /**
     * This method helps to pick video from gallery from [androidx.fragment.app.Fragment] with @param requestCode (requestCode for result)
     *
     * @param context     Context
     * @param fragment    Fragment to receive result
     * @param requestCode requestCode for result
     */
    fun pickVideoFromGallery(context: Context, fragment: Fragment, requestCode: Int) {
        try {
            fragment.startActivityForResult(getGalleryVideoPicker(false), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(context, it) }
        }
    }

    /**
     * This method helps to pick video from gallery from [Activity] with @param requestCode (requestCode for result)
     *
     * @param activity    Activity to receive result
     * @param allowMultiple  true if need to pick video multiple else false
     * @param requestCode requestCode for result
     */
    fun pickVideoFromGallery(activity: Activity, allowMultiple: Boolean, requestCode: Int) {
        try {
            activity.startActivityForResult(getGalleryVideoPicker(allowMultiple), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(activity, it) }
        }
    }

    /**
     * This method helps to pick video from gallery from [androidx.fragment.app.Fragment] with @param requestCode (requestCode for result)
     *
     * @param context     Context
     * @param fragment    Fragment to receive result
     * @param allowMultiple  true if need to pick video multiple else false
     * @param requestCode requestCode for result
     */
    fun pickVideoFromGallery(context: Context, fragment: Fragment, allowMultiple: Boolean, requestCode: Int) {
        try {
            fragment.startActivityForResult(getGalleryVideoPicker(allowMultiple), requestCode)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            e.message?.let { showMessage(context, it) }
        }
    }

    private fun getGalleryVideoPicker(allowMultiple: Boolean): Intent? {
        return if (allowMultiple){
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("video/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        } else {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("video/*")
        }
    }

    private fun showMessage(context: Context, message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_PICK_IMAGE = 1000
        const val REQUEST_PICK_VIDEO = 2000
    }
}