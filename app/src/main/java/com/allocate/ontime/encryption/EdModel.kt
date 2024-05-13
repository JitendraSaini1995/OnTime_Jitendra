package com.allocate.ontime.encryption

import android.util.Log
import com.allocate.ontime.BuildConfig.KEY
import com.allocate.ontime.encryption.EDHelper.TAG
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class EDModel(@SerializedName("data") var data: String) {
    fun <T> getResponseModel(clazz: Class<T>): T {
        return EDHelper.decrypt(data, clazz)
    }

    fun encryptModel(model: Any): EDModel {
        var normalTextEnc: String? = ""
        try {
            val temp = Gson().toJson(model)
            normalTextEnc = AESHelper.getInstance().encrypt(KEY, temp)
        } catch (e: Exception) {
            Log.d(TAG, "Error in encryption\\nError : $e")
        }
        return EDModel(normalTextEnc!!)
    }
}