package com.example.v2bridgelauncher

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            executeFlow()
            finish()
        }
    }

    private suspend fun executeFlow() {
        if (LauncherConfig.SKIP_TOGGLE_WHEN_VPN_ALREADY_UP && VpnState.isVpnConnected(this)) {
            AppLauncher.launchTarget(this)
            return
        }

        val rootCheck = withContext(Dispatchers.IO) { RootShell.run("id") }
        if (!rootCheck.ok) {
            showToast("未拿到 root，无法控制 v2rayNG")
            return
        }

        val broadcastCmd = buildString {
            append("am broadcast ")
            append("-a ")
            append(LauncherConfig.V2RAYNG_WIDGET_ACTION)
            append(' ')
            append("-n ")
            append(LauncherConfig.V2RAYNG_WIDGET_RECEIVER)
        }

        val toggleResult = withContext(Dispatchers.IO) { RootShell.run(broadcastCmd) }
        if (!toggleResult.ok) {
            showToast("触发 v2rayNG 失败：${toggleResult.stderr.ifBlank { toggleResult.stdout }}")
            return
        }

        val started = waitForVpnOrTimeout()
        if (!started && !LauncherConfig.LAUNCH_TARGET_IF_VPN_TIMEOUT) {
            showToast("VPN 未建立，已取消启动目标应用")
            return
        }

        AppLauncher.launchTarget(this)
    }

    private suspend fun waitForVpnOrTimeout(): Boolean {
        val deadline = System.currentTimeMillis() + LauncherConfig.VPN_WAIT_TIMEOUT_MS
        while (System.currentTimeMillis() < deadline) {
            if (VpnState.isVpnConnected(this)) return true
            delay(LauncherConfig.VPN_POLL_INTERVAL_MS)
        }
        return VpnState.isVpnConnected(this)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
