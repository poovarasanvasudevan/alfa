package com.poovarasan.afka.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.poovarasan.afka.service.XMPPService

/**
 * Created by poovarasanv on 9/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 9/5/17 at 3:56 PM
 */

class ChatMessageReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		val intent1 = Intent(context, XMPPService::class.java)
		context.startService(intent1)
	}
}
