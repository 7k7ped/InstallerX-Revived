package com.rosan.installer.data.common.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat
import timber.log.Timber

val PackageInfo.compatVersionCode: Long
    get() = PackageInfoCompat.getLongVersionCode(this)

// Compat function to run below SDK 33
fun PackageManager.getCompatInstalledPackages(flags: Int): List<PackageInfo> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // For Android 13 (API 33) and above
        getInstalledPackages(PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        // For older versions
        getInstalledPackages(flags)
    }
}

/**
 * Compatibility extension function on PackageManager to safely check if a package is archived.
 *
 * @param packageName The package name to check.
 * @return True if the package exists and is archived, false otherwise.
 */
fun PackageManager.isPackageArchivedCompat(packageName: String): Boolean {
    // Archiving is only supported on Android 14 (API 34) and above.
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        return false
    }

    return try {
        val appInfo: ApplicationInfo = this.getApplicationInfo(
            packageName,
            PackageManager.ApplicationInfoFlags.of(
                PackageManager.MATCH_UNINSTALLED_PACKAGES.toLong()
            )
        )
        // Perform the check directly inside this function.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            Timber.tag("IBinderInstallerRepoImpl")
                .d("isPackageArchivedCompat: Checking if package $packageName archived: ${appInfo.isArchived}")
            appInfo.isArchived
        } else {
            // For Android 14 (API 34), check the flag manually.
            val FLAG_ARCHIVED = 1 shl 30
            (appInfo.flags and FLAG_ARCHIVED) != 0
        }
    } catch (e: PackageManager.NameNotFoundException) {
        // If the package is not found, it cannot be archived.
        Timber.tag("IBinderInstallerRepoImpl").w(e, "isPackageArchivedCompat: Package $packageName not found.")
        false
    }
}