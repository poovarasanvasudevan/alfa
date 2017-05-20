package com.poovarasan.afka.ssl;

import android.content.Context;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * Created by poovarasanv on 18/5/17.
 *
 * @author poovarasanv
 * @project Afka
 * @on 18/5/17 at 10:20 AM
 */

public class Factory {

    public static SSLContext sslContext(Context context) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext             sc  = SSLContext.getInstance("TLS");
        MemorizingTrustManager mtm = new MemorizingTrustManager(context);
        sc.init(null, new X509TrustManager[]{mtm}, new java.security.SecureRandom());

        return sc;
    }
}
