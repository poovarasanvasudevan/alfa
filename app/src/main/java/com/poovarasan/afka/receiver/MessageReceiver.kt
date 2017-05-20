package com.poovarasan.afka.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast

/**
 * Created by poovarasanv on 16/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 16/5/17 at 12:26 PM
 */

class MessageReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		context.toast(intent.getStringExtra("message"))
	}
}
