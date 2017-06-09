package com.poovarasan.afka.ui

import android.os.Build
import android.view.View
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.Chat
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.materialAppbar
import com.poovarasan.afka.core.materialToolbar
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 31/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 31/5/17 at 9:46 AM
 */

class ChatUI : AnkoComponent<Chat> {
	override fun createView(ui: AnkoContext<Chat>) = with(ui) {
		verticalLayout {
			lparams(width = matchParent, height = matchParent)
			
			val appbar = materialAppbar {
				lparams(width = matchParent, height = dip(58))
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					elevation = dip(4).toFloat()
				}
				
				materialToolbar(theme = R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
					lparams(width = matchParent, height = matchParent)
					backgroundColor = context.color(R.color.colorPrimary)
					id = R.id.toolbar
					popupTheme = R.style.ThemeOverlay_AppCompat_Light
				}
			}
			include<View>(R.layout.message_layout) {
				id=R.id.messageInclude
				lparams(width = matchParent,height = matchParent)
			}
		}
	}
}
