package com.poovarasan.afka.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.poovarasan.afka.core.isMyServiceRunning
import com.poovarasan.afka.service.ChatMessageService
import org.jetbrains.anko.startService
import org.jetbrains.anko.toast

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
		
		if (!context.isMyServiceRunning(ChatMessageService::class.java)) {
			context.startService<ChatMessageService>()
			context.toast("Service Started")
		}
		
	}
}
