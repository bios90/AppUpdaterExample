package com.example.appupdaterexample.data.repo

import android.os.Build
import com.example.appupdaterexample.BuildConfig
import com.example.appupdaterexample.common.AppClass
import com.example.appupdaterexample.data.StoreType
import com.example.appupdaterexample.data.models.AppInfoData
import com.google.gson.Gson

class RepoAppInfo {

    fun getAppInfo(): AppInfoData = getAppVersionMock() ?: AppInfoData(
        versionName = BuildConfig.VERSION_NAME,
        osVersion = Build.VERSION.SDK_INT.toString(),
        store = getInstallerSourceStore()
    )

    fun getInstallerSourceStore(): StoreType {
        return getAppVersionMock()?.store ?: getInstallerPackage()?.let {
            StoreType.initFromPackageName(it)
        } ?: StoreType.NONE
    }

    private fun getInstallerPackage(): String? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val info = AppClass.app.packageManager.getInstallSourceInfo(AppClass.app.packageName)
            info.initiatingPackageName ?: info.installingPackageName
        } else {
            @Suppress("DEPRECATION")
            AppClass.app.packageManager.getInstallerPackageName(AppClass.app.packageName)
        }

    private fun getAppVersionMock(): AppInfoData? = try {
        val appInfoString = readAssetsFile("app_info_data.json")
        Gson().fromJson(appInfoString, AppInfoData::class.java)
    } catch (e: Exception) {
        null
    }
}