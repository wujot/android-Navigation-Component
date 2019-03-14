package com.hemmersbach.android.service

import android.text.TextUtils

class ValidatorService {

    fun isNullorEmpty(input: String?): Boolean = TextUtils.isEmpty(input)

    fun isFirstLetterUppercase(input: String): Boolean = input.first().isUpperCase()

    fun minCharacters(input: String): Boolean = input.length >= 3
}
