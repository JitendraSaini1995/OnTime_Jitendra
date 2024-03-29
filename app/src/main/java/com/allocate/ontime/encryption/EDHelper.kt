package com.allocate.ontime.encryption

import android.util.Log
import com.allocate.ontime.BuildConfig
import com.google.gson.Gson
import retrofit2.Response

/**
 * Created by Sublimity on 05/03/2024.
 */

object EDHelper {
    val TAG = "EDHelper"
    const val KEY = BuildConfig.KEY
    fun encrypt(`object`: Any?): EDModel {
        var normalTextEnc: String? = ""
        try {
            var temp = Gson().toJson(`object`)
            temp = temp.replace("QEA\\u003d", "QEA=")
            normalTextEnc = AESHelper.getInstance().encrypt(KEY, temp)
        } catch (e: Exception) {
            Log.d(TAG, "Error in encryption\\nError : \" + $e")
        }
        return EDModel(normalTextEnc!!)
    }

    fun encryptString(value: String): EDModel {
        var normalTextEnc: String? = ""
        try {
            val temp = value.replace("QEA\\u003d", "QEA=")
            normalTextEnc = AESHelper.getInstance().encrypt(KEY, temp)
        } catch (e: Exception) {
            Log.d(TAG, "Error in encryption\\nError : \" + $e")
        }
        return EDModel(normalTextEnc!!)
    }

    fun <T : Any?> decryptModel(o: Any, objClass: Class<T>?): T {
        var encrypted = ""
        var decrypted: String? = ""
        try {
            encrypted = ((o as Response<*>).body() as EDModel?)!!.data
            decrypted = AESHelper.getInstance().decrypt(KEY, encrypted)
        } catch (e: Exception) {
            Log.d(TAG, "Error in encryption\\nError : \" + $e")
        }
        return Gson().fromJson(decrypted, objClass)
    }

    fun <T : Any?> decrypt(encrypted: String?, objClass: Class<T>?): T {
        var decrypted: String? = ""
        try {
            decrypted = AESHelper.getInstance().decrypt(KEY, encrypted)
            Log.d(TAG, "decrypted: $decrypted")
        } catch (e: Exception) {
            Log.d(TAG, "Error in encryption\\nError : \" + $e")
        }
        return Gson().fromJson(decrypted, objClass)
    }

    fun decrypt(encrypted: String?): String {
        var decrypted = ""
        try {
            decrypted = AESHelper.getInstance().decrypt(KEY, encrypted)
        } catch (e: Exception) {
            Log.d(TAG, "Error in encryption\\nError : \" + $e")
        }
        return decrypted
    }
}
