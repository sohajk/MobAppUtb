package com.example.mobapputb.viewAdapters

import android.text.TextUtils
import androidx.databinding.InverseMethod

object StringToFloatConverter  {
    @InverseMethod("stringToFloat")
    @JvmStatic
    fun floatToString(value: Float?): String {
        return value?.toString() ?: ""
    }

    @JvmStatic
    fun stringToFloat(value: String): Float? {
        if (TextUtils.isEmpty(value)) {
            return null
        }
        return value.toFloatOrNull()
    }
}
