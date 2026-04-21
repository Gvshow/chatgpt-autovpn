package com.example.v2bridgelauncher

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object VpnState {
    fun isVpnConnected(context: Context): Boolean {
        val cm = context.getSystemService(ConnectivityManager::class.java) ?: return false
        return cm.allNetworks.any { network ->
            cm.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true
        }
    }
}
