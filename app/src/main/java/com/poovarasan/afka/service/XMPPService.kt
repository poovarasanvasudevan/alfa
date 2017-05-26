package com.poovarasan.afka.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.poovarasan.afka.core.reconnect
import com.poovarasan.afka.core.xmppConnect
import org.jetbrains.anko.toast
import org.jivesoftware.smack.filter.StanzaTypeFilter
import org.jivesoftware.smack.packet.Message

/**
 * Created by poovarasanv on 16/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 16/5/17 at 10:08 AM
 */

class XMPPService : Service() {
	
	override fun onBind(intent: Intent): IBinder? {
		return null
	}
	
	
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		toast("Service started")
//		reconnect()
//
//		xmppConnect().addAsyncStanzaListener(XMPPChatMessageListener(), StanzaTypeFilter(Message::class.java))
//
		return Service.START_STICKY
	}
	
	override fun onDestroy() {
		super.onDestroy()
	}
}
