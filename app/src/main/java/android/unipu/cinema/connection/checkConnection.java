package android.unipu.cinema.connection;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class checkConnection {

    private boolean connection = false;

    public boolean checkNetworkConnection(ConnectivityManager connectivityManager){

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            connection = true;
        }

        return connection;
    }
}
