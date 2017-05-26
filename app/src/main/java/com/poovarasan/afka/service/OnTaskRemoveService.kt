package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Created by poovarasanv on 26/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 26/5/17 at 5:24 PM
 */

class OnTaskRemoveService : Service() {
	override fun onBind(intent: Intent?): IBinder? {
		return null
	}
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Log.i("TASKREMOVE","task started onStart")
		return START_NOT_STICKY
	}
	
	override fun onDestroy() {
		super.onDestroy()
		Log.i("TASKREMOVE","task removed des")
		
	}
	
	override fun onTaskRemoved(rootIntent: Intent?) {
		Log.i("TASKREMOVE","task removed ex")
		
		//todo add code {android.os.Intent}
		
	}
	
	
}
