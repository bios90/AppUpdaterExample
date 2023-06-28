package com.example.appupdaterexample.data.models

data class ModelAppUpdateVersions(
    val googlePlayMarket: List<AppVersionData>,
    val huaweiAppGallery: List<AppVersionData>,
    val xiaomiMiStore: List<AppVersionData>,
    val samsungStore: List<AppVersionData>,
    val ruStore: List<AppVersionData>,
)