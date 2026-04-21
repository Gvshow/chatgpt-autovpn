package com.example.v2bridgelauncher

import android.content.Context
import android.content.Intent
import android.widget.Toast

object AppLauncher {
    fun launchTarget(context: Context): Boolean {
        val pm = context.packageManager
        val intent = pm.getLaunchIntentForPackage(LauncherConfig.TARGET_APP_PACKAGE)
        if (intent == null) {
            Toast.makeText(
                context,
                "未找到目标应用：${LauncherConfig.TARGET_APP_PACKAGE}",
                Toast.LENGTH_LONG,
            ).show()
            return false
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return true
    }
}
