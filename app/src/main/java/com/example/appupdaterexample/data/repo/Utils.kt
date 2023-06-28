package com.example.appupdaterexample.data.repo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.example.appupdaterexample.common.AppClass
import java.io.BufferedReader

val sdkVersionNames = listOf(
    "Cupcake",
    "Donut",
    "Eclair",
    "Froyo",
    "Gingerbread",
    "Honeycomb",
    "Ice Cream Sandwich",
    "Jelly Bean",
    "KitKat",
    "Lollipop",
    "Marshmallow",
    "Nougat",
    "Oreo",
    "Pie",
    "Quince Tart",
    "Red Velvet Cake",
    "Snow Cone",
    "Snow Cone v2",
    "Tiramisu",
    "Upside Down Cake",
    "Vanilla Ice Cream",
)

fun readAssetsFile(path: String): String? = try {
    AppClass.app
        .assets
        .open(path)
        .bufferedReader()
        .use(BufferedReader::readText)
} catch (e: Exception) {
    null
}

fun isAppInstalled(context: Context, packageName: String, flags: Int = 0): Boolean {
    try {
        val packageManager = context.packageManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(flags.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, flags)
        }
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
}
