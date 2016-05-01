package ukas.cheapnetwork.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;

import java.lang.reflect.Method;

/**
 * Created by usama on 4/29/16.
 */
public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static IntentFilter getNetworkStateChangeFilter() {
        IntentFilter networkIntentFilter = new IntentFilter();
        networkIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        networkIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        return networkIntentFilter;
    }

    @Nullable
    public static String getMacAddress(WifiManager wifiManager) {
        return wifiManager.getConnectionInfo().getMacAddress();
    }

    public static boolean isWifiConnected(Context context) {
        return isWifiConnected((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    public static boolean isWifiConnected(ConnectivityManager connectivityManager) {
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnectedOrConnecting();
    }

    public static boolean isWiFiHotspotOn(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(wifimanager);
        } catch (Exception e) {
        }

        return false;
    }

    public static boolean setWifiApEnabled(WifiConfiguration wifiConfig, boolean enabled, WifiManager mWifiManager) {
        try {
            if (enabled) {
                mWifiManager.setWifiEnabled(false);
            }

            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            return (Boolean) method.invoke(mWifiManager, wifiConfig, enabled);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the Wi-Fi AP Configuration.
     *
     * @return AP details in {@link WifiConfiguration}
     */
    public static WifiConfiguration getWifiApConfiguration(WifiManager mWifiManager) {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            return (WifiConfiguration) method.invoke(mWifiManager);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets the Wi-Fi AP Configuration.
     *
     * @return {@code true} if the operation succeeded, {@code false} otherwise
     */
    public static boolean setWifiApConfiguration(WifiConfiguration wifiConfig, WifiManager mWifiManager) {
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
            boolean invokation = (boolean) method.invoke(mWifiManager, wifiConfig);
            return invokation;
        } catch (Exception e) {
            return false;
        }
    }
}
