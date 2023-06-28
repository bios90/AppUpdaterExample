package com.example.appupdaterexample.data.models

import com.example.appupdaterexample.data.StoreType

data class AppInfoData(
    val versionName: String,
    val osVersion: String,
    val store: StoreType
)