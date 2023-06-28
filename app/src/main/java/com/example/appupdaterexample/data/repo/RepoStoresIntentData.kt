package com.example.appupdaterexample.data.repo

import com.example.appupdaterexample.common.AppClass
import com.example.appupdaterexample.data.StoreType
import com.example.appupdaterexample.data.models.StoreIntentData

class RepoStoresIntentData(val appPackageName: String, val huaweiAppGalleryAppId: String) {

    fun getAvailableStoreData(installationStoreType: StoreType): StoreIntentData? {
        return getStoreIntentData(installationStoreType) ?: StoreType.values()
            .map { getStoreIntentData(it) }
            .filterNotNull()
            .firstOrNull()
    }

    fun getGooglePlayMarketFallbackUrl() =
        "https://play.google.com/store/apps/details?id=$appPackageName"

    private fun getStoreIntentData(storeType: StoreType): StoreIntentData? {
        val storePackageName = storeType.getStorePackageName()
        val appUri = getStoreAppIntentUri(storeType)
        val webFallbackUrl = getStoreWebUrl(storeType)
        return if (storePackageName != null && appUri != null && webFallbackUrl != null) {
            StoreIntentData(
                storePackageName = storePackageName,
                storeAppUri = appUri,
                storeWebFallBackUrl = webFallbackUrl
            ).takeIf { isAppInstalled(AppClass.app, storePackageName) }
        } else {
            null
        }
    }

    private fun getStoreAppIntentUri(storeType: StoreType) = when (storeType) {
        StoreType.GOOGLE_PLAY_MARKET -> "market://details?id=$appPackageName"
        StoreType.HUAWEI_APP_GALLERY -> "appmarket://details?id=$appPackageName"
        StoreType.XIAOMI_MI_STORE -> "mimarket://details?id=$appPackageName"
        StoreType.SAMSUNG_STORE -> "samsungapps://details?id=$appPackageName"
        StoreType.RU_STORE -> "rustore://apps.rustore.ru/app/$appPackageName"
        StoreType.NONE -> null
    }

    private fun getStoreWebUrl(storeType: StoreType): String? = when (storeType) {
        StoreType.GOOGLE_PLAY_MARKET -> "https://play.google.com/store/apps/details?id=$appPackageName"
        StoreType.HUAWEI_APP_GALLERY -> "https://appgallery.huawei.com/#/app/$huaweiAppGalleryAppId"
        StoreType.XIAOMI_MI_STORE -> "https://global.app.mi.com/details?id=$appPackageName"
        StoreType.SAMSUNG_STORE -> "https://galaxystore.samsung.com/detail/$appPackageName"
        StoreType.RU_STORE -> "https://apps.rustore.ru/app/$appPackageName"
        StoreType.NONE -> null
    }
}