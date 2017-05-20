package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.blankj.utilcode.util.AppUtils
import com.poovarasan.afka.core.CONTEXT
import com.poovarasan.afka.core.reconnect
import com.poovarasan.afka.core.rui
import org.jetbrains.anko.toast
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.MessageListener
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManagerListener
import org.jivesoftware.smack.chat.ChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smackx.muc.InvitationListener
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.packet.MUCUser
import org.jxmpp.jid.EntityJid

/**
 * Created by poovarasanv on 16/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 16/5/17 at 10:08 AM
 */

class XMPPService : Service(), ConnectionListener, InvitationListener, MessageListener, ChatMessageListener, ChatManagerListener {
	override fun invitationReceived(conn: XMPPConnection?, room: MultiUserChat?, inviter: EntityJid?, reason: String?, password: String?, message: Message?, invitation: MUCUser.Invite?) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun chatCreated(chat: Chat?, createdLocally: Boolean) {
		chat!!.addMessageListener(this)
	}
	
	override fun processMessage(chat: Chat?, message: Message?) {
		sendMessage(message = message)
		Log.d("XMPP", "group message : " + message!!.getBody());
	}
	
	override fun processMessage(message: Message?) {
		
		sendMessage(message)
		Log.d("XMPP", "group message : " + message!!.getBody());
	}
	
	fun sendMessage(message: Message?) {
		val intent = Intent("com.poovarasan.afka.Message")
		
		
		intent.putExtra("message", message!!.body)
		CONTEXT.sendBroadcast(intent)
	}
	
	override fun onBind(intent: Intent): IBinder? {
		return null
	}
	
	
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		//if (isConnected()) {
		reconnect()
		//	}
		if(AppUtils.isAppForeground()) {
			toast("foreground")
		} else {
			toast("background")
		}
		return Service.START_STICKY
	}
	
	override fun connected(connection: XMPPConnection) {
		rui { Log.i("XMPP", "Connected") }
	}
	
	override fun authenticated(connection: XMPPConnection, resumed: Boolean) {
		rui { Log.i("XMPP", "Authenticated") }
		//Thread({
		//toast("Authenticated")
		//}).start()
	}
	
	override fun connectionClosed() {
		rui { Log.i("XMPP", "Connection Closed") }
	}
	
	override fun connectionClosedOnError(e: Exception) {
		
	}
	
	override fun reconnectionSuccessful() {
		rui { Log.i("XMPP", "Reconnect Success") }
	}
	
	override fun reconnectingIn(seconds: Int) {
		
	}
	
	override fun reconnectionFailed(e: Exception) {
		
	}
	

}
