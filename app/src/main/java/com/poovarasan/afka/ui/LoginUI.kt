package com.poovarasan.afka.ui

import android.graphics.Color
import android.os.Build
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.Home
import com.poovarasan.afka.activity.Login
import com.poovarasan.afka.core.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jivesoftware.smackx.iqregister.AccountManager
import org.jxmpp.jid.parts.Localpart

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 8:57 AM
 */

class LoginUI : AnkoComponent<Login> {
	override fun createView(ui: AnkoContext<Login>) = with(ui) {
		verticalLayout {
			val appbar = materialAppbar {
				lparams(width = matchParent, height = dip(56))
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					elevation = dip(4).toFloat()
				}
				
				
				materialToolbar(theme = R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
					lparams(width = matchParent, height = matchParent)
					backgroundColor = context.color(R.color.colorPrimary)
					id = R.id.toolbar
					popupTheme = R.style.ThemeOverlay_AppCompat_Light
					title = "Login"
				}
			}
			
			relativeLayout {
				cardView {
					verticalLayout {
						
						materialButton {
							textColor = Color.WHITE
							backgroundColor = Color.parseColor("#3b5998")
							//	setBackgroundTint(Color.parseColor("#3b5998"))
							text = "Login with facebook"
							
							onClick {
								
								doAsync {
									
									context.internetAvailable({
										try {
											val xmpp = XMPPFactory.connect(context, "poosan", "poosan")
											
										} catch(e: Exception) {
											val xmpp = XMPPFactory.getXmpptcpConnection(context)
											val AccManager = AccountManager.getInstance(xmpp)
											AccManager.createAccount(Localpart.from("poosan"), "poosan")
											xmpp.login("poosan", "poosan")
										}
										uiThread {
											val xmpp = XMPPFactory.getXmpptcpConnection(context)
											if (xmpp.isAuthenticated) {
												startActivity<Home>();
											} else {
												toast("Not Authenticated")
											}
										}
										
									}, {
										toast("Internet Not Available")
									})
									
								}
								
							}
						}.lparams(width = matchParent, height = wrapContent) {
							margin = dip(5)
						}
						
						materialButton {
							textColor = Color.WHITE
							backgroundColor = Color.parseColor("#D34836")
							text = "Login with Google+"
						}.lparams(width = matchParent, height = wrapContent) {
							margin = dip(5)
						}
					}
				}.lparams(width = matchParent, height = wrapContent) {
					alignParentBottom()
				}
				
				
				lparams(width = matchParent, height = wrapContent) {
					below(appbar)
				}
			}
			
			lparams {
				width = matchParent
				height = matchParent
			}
		}
	}
}
