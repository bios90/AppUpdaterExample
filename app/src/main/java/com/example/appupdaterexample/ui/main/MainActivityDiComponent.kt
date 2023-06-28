package com.example.appupdaterexample.ui.main

import com.example.appupdaterexample.data.repo.RepoAppInfo
import com.example.appupdaterexample.data.repo.RepoAppVersions
import com.example.appupdaterexample.data.repo.RepoStoresIntentData

private const val appPackageName = "ru.auto.ara"
private const val huaweiAppId = "xxxyyyzzz"

class MainActivityDiComponent {

    fun createPresenter(view: MainView): MainPresenter = MainPresenter(
        view = view,
        repoAppVersions = RepoAppVersions(),
        repoAppInfo = RepoAppInfo(),
        repoStoreIntentData = RepoStoresIntentData(appPackageName, huaweiAppId)
    )
}