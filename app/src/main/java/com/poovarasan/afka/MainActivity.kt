package com.poovarasan.afka

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.poovarasan.afka.activity.Home
import com.poovarasan.afka.activity.Login
import com.poovarasan.afka.core.Prefs
import com.poovarasan.afka.core.internetAvailable
import com.poovarasan.afka.core.isMyServiceRunning
import com.poovarasan.afka.core.xmppConnect
import com.poovarasan.afka.service.ChatMessageService
import com.poovarasan.afka.ui.MainActivityUI
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		MainActivityUI().setContentView(this)
		
		if (!isMyServiceRunning(ChatMessageService::class.java)) {
			startService<ChatMessageService>()
			//toast("Service Started")
		}
		if (Prefs.contains("username") && Prefs.contains("password")) {
			
			internetAvailable({
				
				val xmpp = xmppConnect()
				
				val username = Prefs.getString("username", "");
				val password = Prefs.getString("password", "")
				
				doAsync {
					if (!xmpp.isConnected)
						xmpp.connect()
					
					if (!xmpp.isAuthenticated) {
						
						try {
							xmpp.login(username, password)
							
							
							if (xmpp.isAuthenticated) {
								uiThread {
									startActivity<Home>()
									finish()
								}
								
								
							} else {
								
								uiThread {
									startActivity<Login>()
									finish()
								}
								
							}
						} catch (e: Exception) {
							uiThread {
								startActivity<Login>()
								finish()
							}
						}
						
					} else {
						
						uiThread {
							startActivity<Home>()
							finish()
						}
						
					}
				}
				
			}, {
				startActivity<Home>()
				finish()
			})
		} else {
			startActivity<Login>()
			finish()
		}
		
	}
}
