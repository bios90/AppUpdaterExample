package com.example.appupdaterexample.data.repo

import com.example.appupdaterexample.data.StoreType
import com.example.appupdaterexample.data.models.AppVersionData
import com.example.appupdaterexample.data.models.ModelAppUpdateVersions
import com.google.gson.Gson

class RepoAppVersions {

    fun getAppVersions(): ModelAppUpdateVersions? = try {
        val versionsString = readAssetsFile("update_versions.json")
        Gson().fromJson(versionsString, ModelAppUpdateVersions::class.java)
    } catch (e: java.lang.Exception) {
        null
    }
}

fun AppVersionData.isForSdk(sdk: Int): Boolean {
    val firstSupported = this.firstSupportedSdkVersion.toIntOrNull()
    val lastSupported = this.lastSupportedSdkVersion?.toIntOrNull()
    return firstSupported != null
            && sdk >= firstSupported
            && (lastSupported == null || sdk <= lastSupported)
}

fun ModelAppUpdateVersions.getVersionsByStore(store: StoreType) = when (store) {
    StoreType.GOOGLE_PLAY_MARKET -> googlePlayMarket
    StoreType.HUAWEI_APP_GALLERY -> huaweiAppGallery
    StoreType.XIAOMI_MI_STORE -> xiaomiMiStore
    StoreType.SAMSUNG_STORE -> samsungStore
    StoreType.RU_STORE -> ruStore
    StoreType.NONE -> null
}
