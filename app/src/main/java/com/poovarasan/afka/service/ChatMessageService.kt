package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.poovarasan.afka.core.isConnected
import com.poovarasan.afka.core.login
import com.poovarasan.afka.core.xmppConnect
import org.jetbrains.anko.toast
import org.jivesoftware.smack.chat.ChatManager
import org.jivesoftware.smack.chat.ChatManagerListener
import org.jivesoftware.smack.chat.ChatMessageListener

/**
 * Created by poovarasanv on 9/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 9/5/17 at 5:52 PM
 */

class ChatMessageService : Service() {
	
	override fun onCreate() {
		super.onCreate()
	}
	
	val chatListener = ChatManagerListener { chat, createdLocally -> chat.addMessageListener(messageListener) }
	val messageListener = ChatMessageListener { chat, message ->
		run {
			Handler(Looper.getMainLooper()).post({
				toast(message.subject  + "" +message.body)
			})
		}
	}
	
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		if (isConnected()) {
			login()
			ChatManager.getInstanceFor(xmppConnect()).addChatListener(chatListener)
		}
		return Service.START_STICKY
	}
	
	override fun onBind(intent: Intent): IBinder? {
		return null
	}
}
