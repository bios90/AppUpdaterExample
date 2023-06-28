package com.example.appupdaterexample.ui.main

import com.example.appupdaterexample.data.StoreType
import com.example.appupdaterexample.data.models.ModelSimpleItem
import com.example.appupdaterexample.data.models.ModelUpdateBannerItem
import com.example.appupdaterexample.data.models.VersionCodeNums
import com.example.appupdaterexample.data.models.compareTo
import com.example.appupdaterexample.data.repo.RepoAppInfo
import com.example.appupdaterexample.data.repo.RepoAppVersions
import com.example.appupdaterexample.data.repo.RepoStoresIntentData
import com.example.appupdaterexample.data.repo.getVersionsByStore
import com.example.appupdaterexample.data.repo.isForSdk
import com.example.appupdaterexample.data.repo.sdkVersionNames
import java.util.*

class MainPresenter(
    private val view: MainView,
    private val repoAppVersions: RepoAppVersions,
    private val repoAppInfo: RepoAppInfo,
    private val repoStoreIntentData: RepoStoresIntentData
) {

    fun onViewCreated() {
        val items = getSimpleItems()
            .map { it as Any }
            .toMutableList()
            .apply {
                if (needToShowAppUpdateBanner()) {
                    add(2, ModelUpdateBannerItem(needToShowUpdateBanner = true))
                }
            }

        view.setItems(Collections.unmodifiableList(items))
    }

    fun onUpdateClicked() {
        val installerStore = repoAppInfo.getAppInfo().store
        when (installerStore) {
            StoreType.GOOGLE_PLAY_MARKET -> {
                view.showGoogleInAppUpdate()
            }
            StoreType.HUAWEI_APP_GALLERY,
            StoreType.XIAOMI_MI_STORE,
            StoreType.SAMSUNG_STORE,
            StoreType.RU_STORE -> {
                val storeIntentData = repoStoreIntentData.getAvailableStoreData(installerStore)
                if (storeIntentData != null) {
                    view.openStoreIntent(storeIntentData)
                } else {
                    view.showWebViewIntent(repoStoreIntentData.getGooglePlayMarketFallbackUrl())
                }

            }
            else -> Unit
        }
    }

    private fun getSimpleItems() = sdkVersionNames.map { ModelSimpleItem(it) }

    private fun needToShowAppUpdateBanner(): Boolean {
        val updateVersionsData = repoAppVersions.getAppVersions() ?: return false
        val currentAppInfo = repoAppInfo.getAppInfo()
        val storeVersions = updateVersionsData.getVersionsByStore(currentAppInfo.store)
        val matchedVersionData =
            storeVersions?.firstOrNull { it.isForSdk(currentAppInfo.osVersion.toInt()) }
        val latestVersionCodes =
            matchedVersionData?.latestAppVersion?.let(VersionCodeNums::initFromString)
        val currentAppVersionCodes = currentAppInfo.versionName.let(VersionCodeNums::initFromString)
        return if (matchedVersionData != null && latestVersionCodes != null && currentAppVersionCodes != null) {
            currentAppVersionCodes.compareTo(latestVersionCodes) < 0
        } else {
            false
        }
    }
}
