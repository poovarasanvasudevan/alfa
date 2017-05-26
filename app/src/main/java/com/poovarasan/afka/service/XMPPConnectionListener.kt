package com.poovarasan.afka.service

import android.content.Intent
import android.util.Log
import com.poovarasan.afka.core.CONTEXT
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import java.lang.Exception

/**
 * Created by poovarasanv on 25/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 25/5/17 at 2:35 PM
 */

class XMPPConnectionListener : ConnectionListener {
	override fun connected(connection: XMPPConnection?) {
		
	}
	
	override fun connectionClosed() {
		Log.i("XMPP", "Connection Closed")
		val intent = Intent("com.poovarasan.alfa.RECONNECT")
		CONTEXT.sendBroadcast(intent)
	}
	
	override fun connectionClosedOnError(e: Exception?) {
		Log.i("XMPP", e!!.localizedMessage)
		
		val intent = Intent("com.poovarasan.alfa.RECONNECT")
		CONTEXT.sendBroadcast(intent)
	}
	
	override fun reconnectionSuccessful() {
		Log.i("XMPP", "reconnect Connection success")
	}
	
	override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
		
	}
	
	override fun reconnectionFailed(e: Exception?) {
		Log.i("XMPP", "reConnection failed")
	}
	
	override fun reconnectingIn(seconds: Int) {
		Log.i("XMPP", "Reconnein ${seconds}")
	}
	
}
