package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.poovarasan.afka.core.SyncAdapter



/**
 * Created by poovarasanv on 11/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 11/5/17 at 1:04 PM
 */

class SyncService : Service() {
	
	private val sSyncAdapterLock = Any()
	private var mSyncAdapter: SyncAdapter? = null
	
	override fun onCreate() {
		Log.i("SYNC","Sync Service created.")
		synchronized(sSyncAdapterLock) {
			if (mSyncAdapter == null) {
				mSyncAdapter = SyncAdapter(applicationContext,
					true)
			}
		}
	}
	override fun onBind(intent: Intent?): IBinder {
		return mSyncAdapter!!.getSyncAdapterBinder();
	}
	
}
