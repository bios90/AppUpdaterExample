package com.example.appupdaterexample.data

enum class StoreType {
    NONE,
    GOOGLE_PLAY_MARKET,
    HUAWEI_APP_GALLERY,
    XIAOMI_MI_STORE,
    SAMSUNG_STORE,
    RU_STORE;

    fun getStorePackageName(): String? = when (this) {
        GOOGLE_PLAY_MARKET -> "com.android.vending"
        HUAWEI_APP_GALLERY -> "com.huawei.appmarket"
        XIAOMI_MI_STORE -> "com.xiaomi.mipicks"
        SAMSUNG_STORE -> "com.sec.android.app.samsungapps"
        RU_STORE -> "ru.vk.store"
        NONE -> null
    }

    companion object {
        fun initFromPackageName(packageName: String): StoreType? = when (packageName) {
            GOOGLE_PLAY_MARKET.getStorePackageName() -> GOOGLE_PLAY_MARKET
            HUAWEI_APP_GALLERY.getStorePackageName() -> HUAWEI_APP_GALLERY
            XIAOMI_MI_STORE.getStorePackageName() -> XIAOMI_MI_STORE
            SAMSUNG_STORE.getStorePackageName() -> SAMSUNG_STORE
            RU_STORE.getStorePackageName() -> RU_STORE
            else -> null
        }
    }
}