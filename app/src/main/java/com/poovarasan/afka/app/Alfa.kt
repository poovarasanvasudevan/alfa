package com.poovarasan.afka.app

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.facebook.drawee.backends.pipeline.Fresco
import com.poovarasan.afka.R
import com.poovarasan.afka.core.Prefs
import com.poovarasan.afka.core.isConnected
import com.poovarasan.afka.core.isMyServiceRunning
import com.poovarasan.afka.service.ChatMessageService
import com.poovarasan.afka.storage.SimpleStorage
import com.poovarasan.afka.storage.Storage
import org.jetbrains.anko.startService
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.ping.PingManager
import pl.tajchert.nammu.Nammu
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


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
		lateinit var xmppConnection: XMPPTCPConnection
			private set
		lateinit var jobManager: JobManager
			private set
	}
	
	
	override fun onCreate() {
		super.onCreate()
		
		Prefs.initPrefs(this, "alfa_pref", Context.MODE_PRIVATE)
		Fresco.initialize(this);
		CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
			.setDefaultFontPath("font/font.ttf")
			.setFontAttrId(R.attr.fontPath)
			.build()
		)
		
		if (SimpleStorage.isExternalStorageWritable()) {
			storage = SimpleStorage.getExternalStorage()
		} else {
			storage = SimpleStorage.getInternalStorage(this)
		}
		val config: XMPPTCPConnectionConfiguration = XMPPTCPConnectionConfiguration.builder()
			.setServiceName("localhost")
			.setHost("10.0.2.2")
			.setDebuggerEnabled(true)
			.setConnectTimeout(10000000)
			//	.setCustomSSLContext(createSSLContext(this))
			//	.setSocketFactory(createSSLContext(this).socketFactory)
			.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
			//	.setKeystorePath("src/main/resources/raw/mykey")
			.setPort(5222)
			.build()
		
		
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
		
		jobManager = JobManager(builder.build())
		
		Nammu.init(this)
		
		xmppConnection = XMPPTCPConnection(config)
		//xmppConnection.setPacketReplyTimeout(10000000);
		val pingManager = PingManager.getInstanceFor(xmppConnection)
		pingManager.pingInterval = 300
		
		
		val networkStateReceiver = object : BroadcastReceiver() {
			override fun onReceive(context: Context?, intent: Intent?) {
				if (isConnected()) {
					if (!isMyServiceRunning(ChatMessageService::class.java)) {
						startService<ChatMessageService>()
						//toast("Service Started")
					}
				}
			}
		}
		
		val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(networkStateReceiver, filter);
	}
	
	@Throws(KeyStoreException::class, NoSuchAlgorithmException::class, KeyManagementException::class, IOException::class, CertificateException::class)
	private fun createSSLContext(context: Context): SSLContext {
		
		val trustStore: KeyStore = KeyStore.getInstance("BKS")
		
		val ins = context.resources.openRawResource(R.raw.bog)
		
		trustStore.load(ins, "poosan".toCharArray())
		
		val trustManagerFactory = TrustManagerFactory
			.getInstance(KeyManagerFactory.getDefaultAlgorithm())
		trustManagerFactory.init(trustStore)
		val sslContext = SSLContext.getInstance("TLS")
		sslContext.init(null, trustManagerFactory.getTrustManagers(), SecureRandom())
		return sslContext
	}
}
