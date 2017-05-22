package com.poovarasan.afka.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.blankj.utilcode.util.Utils
import com.facebook.drawee.backends.pipeline.Fresco
import com.poovarasan.afka.R
import com.poovarasan.afka.core.Prefs
import com.poovarasan.afka.core.reconnect
import com.poovarasan.afka.receiver.ChatMessageReceiver
import com.poovarasan.afka.receiver.RosterListener
import com.poovarasan.afka.service.XMPPService
import com.poovarasan.afka.ssl.Factory
import com.poovarasan.afka.storage.SimpleStorage
import com.poovarasan.afka.storage.Storage
import me.leolin.shortcutbadger.ShortcutBadger
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.chat.ChatManager
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.ping.PingManager
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager
import pl.tajchert.nammu.Nammu
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
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

        lateinit var context: Context
            private set
        lateinit var jobManager: JobManager
            private set
    }


    override fun onTerminate() {
        super.onTerminate()

        Log.i("XMPP", "Application Terminated")
    }

    override fun onCreate() {
        super.onCreate()

        Prefs.initPrefs(this, "alfa_pref", Context.MODE_PRIVATE)
        Fresco.initialize(this)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("font/font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

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
        val config: XMPPTCPConnectionConfiguration = XMPPTCPConnectionConfiguration.builder()
                //.setServiceName("localhost")
                .setCompressionEnabled(true)
                .setXmppDomain("localhost")
                .setHost("10.0.2.2")
                .setDebuggerEnabled(true)
                .setConnectTimeout(10000000)
                .setCustomSSLContext(Factory.sslContext(this))
                .setHostnameVerifier { hostname, session -> true }
                //.setPort(7555)
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

        XMPPTCPConnection.setUseStreamManagementResumptiodDefault(true)
        XMPPTCPConnection.setUseStreamManagementDefault(true)

        xmppConnection = XMPPTCPConnection(config)
        xmppConnection.packetReplyTimeout = 10000000
        xmppConnection.addConnectionListener(XMPPService())

        ReconnectionManager.getInstanceFor(xmppConnection).enableAutomaticReconnection()
        ServerPingWithAlarmManager.onCreate(this)
        ServerPingWithAlarmManager.getInstanceFor(xmppConnection).isEnabled = true
        ReconnectionManager.setEnabledPerDefault(true)

        ChatManager.getInstanceFor(xmppConnection).addChatListener(XMPPService())

        Roster.getInstanceFor(xmppConnection).addRosterListener(RosterListener())
        Roster.getInstanceFor(xmppConnection).subscriptionMode = Roster.SubscriptionMode.accept_all

        val pingManager = PingManager.getInstanceFor(xmppConnection)
        pingManager.pingInterval = 30
        pingManager.registerPingFailedListener {
            reconnect()
        }
    
        val manager = ReconnectionManager.getInstanceFor(xmppConnection)
        manager.enableAutomaticReconnection()
    
    
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(ChatMessageReceiver(), filter)

    }

    private fun configureSSLSocketFactory(): SSLSocketFactory {
        val ssf = configureSSLContext().getSocketFactory();
        return ssf


    }

    private fun configureSSLContext(): SSLContext {
        val ks = KeyStore.getInstance("BKS");
        val jksInputStream = context.resources.openRawResource(R.raw.mqttclient)
        ks.load(jksInputStream, "password".toCharArray());

        val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, "password".toCharArray());

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        val sc = SSLContext.getInstance("TLS");
        val trustManagers = tmf.getTrustManagers();
        sc.init(kmf.getKeyManagers(), trustManagers, null);


        return sc;
    }

}
