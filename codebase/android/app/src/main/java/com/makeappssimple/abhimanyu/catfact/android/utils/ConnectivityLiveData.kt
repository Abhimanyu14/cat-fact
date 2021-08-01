package com.makeappssimple.abhimanyu.catfact.android.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

enum class NetworkState {
    CONNECTED, DISCONNECTED
}

// Source - https://medium.com/@kiitvishal89/android-listening-to-connectivity-changes-the-correct-way-a614f0d6d2af
class ConnectivityLiveData(context: Context) : LiveData<NetworkState>() {
    private var connectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
        
        }
        
        override fun onAvailable(network: Network) {
            postValue(NetworkState.CONNECTED)
        }
        
        override fun onLost(network: Network) {
            postValue(NetworkState.DISCONNECTED)
        }
    }
    
    override fun onActive() {
        super.onActive()
        notifyInitialNetworkStatus()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        } else {
            lollipopAndAboveRegisterNetworkCallback()
        }
    }
    
    override fun onInactive() {
        super.onInactive()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopAndAboveRegisterNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            .build()
        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
    }
    
    // Source - https://stackoverflow.com/a/57284789/9636037 - This method and dependant ones
    private fun notifyInitialNetworkStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            marshmallowAndAboveNotifyInitialNetworkStatus()
        } else {
            belowMarshmallowNotifyInitialNetworkStatus()
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.M)
    private fun marshmallowAndAboveNotifyInitialNetworkStatus() {
        var networkState = NetworkState.DISCONNECTED
        connectivityManager?.activeNetwork?.let { activeNetwork ->
            connectivityManager?.getNetworkCapabilities(activeNetwork)?.let { networkCapabilities ->
                // Source - https://stackoverflow.com/a/41063539/9636037
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                ) {
                    networkState = NetworkState.CONNECTED
                }
            }
        }
        postValue(networkState)
    }
    
    private fun belowMarshmallowNotifyInitialNetworkStatus() {
        if (connectivityManager?.activeNetworkInfo?.isConnected == true) {
            postValue(NetworkState.CONNECTED)
        } else {
            postValue(NetworkState.DISCONNECTED)
        }
    }
}
