package com.example.appupdaterexample.data.models

data class AppVersionData(
    val firstSupportedSdkVersion: String,
    val lastSupportedSdkVersion: String?,
    val latestAppVersion: String
)