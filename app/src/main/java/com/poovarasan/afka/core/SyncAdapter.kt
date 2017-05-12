package com.poovarasan.afka.core

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.util.Log

/**
 * Created by poovarasanv on 11/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 11/5/17 at 1:01 PM
 */

class SyncAdapter(context: Context, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {
	
	override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?, provider: ContentProviderClient?, syncResult: SyncResult?) {
		Log.i("SYNC","Sync Adapter called.");
	}
	
}
