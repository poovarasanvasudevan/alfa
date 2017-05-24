package com.poovarasan.afka.ui

import android.os.Build
import android.view.View
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.Settings
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.materialAppbar
import com.poovarasan.afka.core.materialToolbar
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 23/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 23/5/17 at 3:15 PM
 */

class SettingsUI(val uiType: Int) : AnkoComponent<Settings> {
	override fun createView(ui: AnkoContext<Settings>) = with(ui) {
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
				}
			}
			
			relativeLayout {
				when (uiType) {
					Config.SYNC_UI -> {
						include<View>(R.layout.sync_settings) {
							id = R.id.settingPrefId
						}.lparams(width = matchParent, height = matchParent)
					}
					
				}
			}
		}
	}
}
