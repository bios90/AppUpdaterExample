package com.example.appupdaterexample.data.models

data class VersionCodeNums(val major: Int, val minor: Int, val patch: Int) {
    companion object {
        fun initFromString(version: String?): VersionCodeNums? {
            version ?: return null
            val asNums = version.split(".").mapNotNull { it.toIntOrNull() }
            val major = asNums.getOrNull(0)
            val minor = asNums.getOrNull(1)
            val patch = asNums.getOrNull(2) ?: 0
            return if (major != null && minor != null) {
                VersionCodeNums(major, minor, patch)
            } else {
                null
            }
        }
    }
}

fun VersionCodeNums.compareTo(version: VersionCodeNums): Int = when {
    major > version.major -> 1
    major < version.major -> -1
    minor > version.minor -> 1
    minor < version.minor -> -1
    patch > version.patch -> 1
    patch < version.patch -> -1
    else -> 0
}