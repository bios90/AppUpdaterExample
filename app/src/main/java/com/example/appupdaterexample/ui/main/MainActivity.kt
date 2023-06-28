package com.example.appupdaterexample.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appupdaterexample.R
import com.example.appupdaterexample.data.models.StoreIntentData
import com.example.appupdaterexample.ui.adapters.RvAdapter
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : AppCompatActivity(), MainView {

    private val mainActivityDiComponent = MainActivityDiComponent()
    private val presenter: MainPresenter by lazy { mainActivityDiComponent.createPresenter(view = this) }
    private val adapter by lazy { RvAdapter() }
    private val RQ_GOOGLE_IN_APP_UPDATE_REQUEST = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycler()
        presenter.onViewCreated()
    }

    private fun setUpRecycler() {
        with(findViewById(R.id.rv) as RecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            this@MainActivity.adapter.onUpdateClicked = { presenter.onUpdateClicked() }
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun setItems(items: List<Any>) {
        adapter.setItems(items)
    }

    override fun openStoreIntent(storeIntentData: StoreIntentData) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(storeIntentData.storeAppUri))
            intent.setPackage(storeIntentData.storePackageName)
            startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(storeIntentData.storeWebFallBackUrl))
            startActivity(intent)
        }
    }

    override fun showGoogleInAppUpdate() {
        Toast.makeText(this, "Here Google In App Update will be started.", Toast.LENGTH_LONG).show()
        return
//        Real code for application from Google Play Market
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    requireNotNull(this),
                    RQ_GOOGLE_IN_APP_UPDATE_REQUEST
                )
            }
        }
    }

    override fun showWebViewIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

