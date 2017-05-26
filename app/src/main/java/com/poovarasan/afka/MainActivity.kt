package com.poovarasan.afka

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.poovarasan.afka.activity.Home
import com.poovarasan.afka.activity.Login
import com.poovarasan.afka.core.Prefs
import com.poovarasan.afka.core.XMPPFactory
import com.poovarasan.afka.core.internetAvailable
import com.poovarasan.afka.core.isMyServiceRunning
import com.poovarasan.afka.service.OnTaskRemoveService
import com.poovarasan.afka.service.XMPPService
import com.poovarasan.afka.ui.MainActivityUI
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		MainActivityUI().setContentView(this)
		
		startService<OnTaskRemoveService>()
		
		if (!isMyServiceRunning(XMPPService::class.java)) {
			startService<XMPPService>()
		}
		if (Prefs.contains("username") && Prefs.contains("password")) {
			
			internetAvailable({
				doAsync {
					val xmpp = XMPPFactory.connect(applicationContext, "", "")
					
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
