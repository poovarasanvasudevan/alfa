package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.poovarasan.afka.core.xmppConnect
import org.jetbrains.anko.toast
import org.jivesoftware.smack.chat.ChatManager

/**
 * Created by poovarasanv on 9/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 9/5/17 at 5:52 PM
 */

class ChatMessageService : Service() {
	
	
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		Log.i("XMPP","Servic connected 1")
		if (xmppConnect().isConnected && xmppConnect().isAuthenticated) {
			
			Log.i("XMPP","Servic connected")
			val chatManager = ChatManager.getInstanceFor(xmppConnect()).addChatListener { chat, createdLocally ->
				run {
					chat.addMessageListener { chat, message ->
						applicationContext.toast(message.body)
					}
				}
			}
		}
		return Service.START_STICKY
	}
	
	override fun onBind(intent: Intent): IBinder? {
		return null
	}
}
