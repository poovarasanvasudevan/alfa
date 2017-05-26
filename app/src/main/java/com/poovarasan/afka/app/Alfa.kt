package com.poovarasan.afka.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.multidex.MultiDex
import android.util.Log
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService
import com.blankj.utilcode.util.Utils
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.poovarasan.afka.core.Prefs
import com.poovarasan.afka.core.lollipopOrNewer
import com.poovarasan.afka.receiver.ChatMessageReceiver
import com.poovarasan.afka.receiver.ConnectionMoniter
import com.poovarasan.afka.service.GCMJobSchedulerService
import com.poovarasan.afka.service.JobSchedulerService
import com.poovarasan.afka.storage.SimpleStorage
import com.poovarasan.afka.storage.Storage
import me.leolin.shortcutbadger.ShortcutBadger
import pl.tajchert.nammu.Nammu


/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 8:39 AM
 */
class Alfa : Application() {
	
	companion object {
		lateinit var storage: Storage
			private set
		
		lateinit var context: Context
			private set
		lateinit var jobManager: JobManager
			private set
	}
	
	override fun onTerminate() {
		super.onTerminate()
	}
	
	override fun onCreate() {
		super.onCreate()
		
		Prefs.initPrefs(this, "alfa_pref", Context.MODE_PRIVATE)
		Fresco.initialize(this)
		
		
		context = this
		Utils.init(this)
		
		if (ShortcutBadger.isBadgeCounterSupported(this)) {
			ShortcutBadger.applyCount(this, 4)
		}
		
		if (SimpleStorage.isExternalStorageWritable()) {
			storage = SimpleStorage.getExternalStorage()
		} else {
			storage = SimpleStorage.getInternalStorage(this)
		}
		
		
		val builder = Configuration.Builder(this)
			.customLogger(object : CustomLogger {
				private val TAG = "JOBS"
				override fun isDebugEnabled(): Boolean {
					return true
				}
				
				override fun d(text: String, vararg args: Any) {
					Log.d(TAG, String.format(text, *args))
				}
				
				override fun e(t: Throwable, text: String, vararg args: Any) {
					Log.e(TAG, String.format(text, *args), t)
				}
				
				override fun e(text: String, vararg args: Any) {
					Log.e(TAG, String.format(text, *args))
				}
				
				override fun v(text: String, vararg args: Any) {
					Log.e(TAG, String.format(text, *args))
				}
			})
			.minConsumerCount(1)//always keep at least one consumer alive
			.maxConsumerCount(3)//up to 3 consumers at a time
			.loadFactor(3)//3 jobs per consumer
			.consumerKeepAlive(120)//wait 2 minute
		
		if (lollipopOrNewer()) {
			builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(this, JobSchedulerService::class.java), true)
		} else {
			val enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
			if (enableGcm == ConnectionResult.SUCCESS)
				builder.scheduler(GcmJobSchedulerService.createSchedulerFor(this, GCMJobSchedulerService::class.java), true)
		}
		
		jobManager = JobManager(builder.build())
		
		Nammu.init(this)
		
		
		val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
		registerReceiver(ChatMessageReceiver(), filter)
		registerReceiver(ConnectionMoniter(), filter)
		
	}
	
	
	override fun attachBaseContext(base: Context?) {
		super.attachBaseContext(base)
		MultiDex.install(this)
	}
}
