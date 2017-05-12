package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.poovarasan.afka.core.Authenticator

/**
 * Created by poovarasanv on 11/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 11/5/17 at 12:59 PM
 */

class AuthenticationService:Service() {
	
	private val TAG = "AuthenticationService"
	private var mAuthenticator: Authenticator? = null
	override fun onBind(intent: Intent?): IBinder {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.v(TAG,
				"getBinder()...  returning the AccountAuthenticator binder for intent "
					+ intent);
		}
		return mAuthenticator!!.getIBinder();
	}
	
	
	override fun onCreate() {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.v(TAG, "SampleSyncAdapter Authentication Service started.")
		}
		mAuthenticator = Authenticator(this)
	}
	
	override fun onDestroy() {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.v(TAG, "SampleSyncAdapter Authentication Service stopped.")
		}
	}
}
