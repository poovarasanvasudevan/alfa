package com.poovarasan.afka.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.NetworkUtils
import com.poovarasan.afka.core.XMPPFactory
import org.jetbrains.anko.doAsync

/**
 * Created by poovarasanv on 26/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 26/5/17 at 11:08 AM
 */

class ConnectionMoniter : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		if (NetworkUtils.isConnected()) {
			doAsync {
				XMPPFactory.connect(context, "", "")
			}
		}
	}
}
