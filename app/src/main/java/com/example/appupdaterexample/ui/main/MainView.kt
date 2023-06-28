package com.example.appupdaterexample.ui.main

import com.example.appupdaterexample.data.models.StoreIntentData

interface MainView {
    fun setItems(items: List<Any>)
    fun openStoreIntent(storeIntentData: StoreIntentData)
    fun showGoogleInAppUpdate()
    fun showWebViewIntent(url: String)
}