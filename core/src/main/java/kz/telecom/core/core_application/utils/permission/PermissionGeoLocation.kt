package kz.telecom.core.core_application.utils.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kz.telecom.core.core_application.R
import kz.telecom.core.core_application.modal.CoreAlertDialog


class PermissionGeoLocation(private val activity: AppCompatActivity) {
    fun askPermissionsWithConditions(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions.isEmpty()) {
            return
        }
        var allPermissionsGranted = true
        if (grantResults.isNotEmpty()) {
            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    allPermissionsGranted = false
                    break
                }
            }
        }
        if (allPermissionsGranted) {
            getLocation()
        } else {
            checkPermissionAlwaysDenied(permissions)
        }
    }

    private fun checkPermissionAlwaysDenied(permissions: Array<out String>) {
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                && ActivityCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                val action = {
                    val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(settingsIntent)
                }
                with(activity) {
                    CoreAlertDialog(
                        context = this,
                        title = getString(R.string.text_request_location_permission),
                        subTitle = getString(R.string.text_request_location_permission_text),
                        mainActionText = getString(R.string.text_ok),
                        mainAction = action,
                        secondaryActionText = getString(R.string.text_cancel)
                    )
                }
                break
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    fun getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
            && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_CODE_LOCATION
            )
        } else {
//            LocationFinderUtils().requestLocationUpdates(activity)
        }
    }

    companion object {
        const val REQUEST_CODE_LOCATION = 1568
    }

}