package com.poovarasan.afka.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.SnackbarUtils
import com.poovarasan.afka.R
import com.poovarasan.afka.base.BaseActivity
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.core.color
import com.poovarasan.afka.ui.SettingsUI
import com.yarolegovich.mp.MaterialSwitchPreference
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.setContentView

/**
 * Created by poovarasanv on 23/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 23/5/17 at 3:14 PM
 */

class Settings : BaseActivity() {
	lateinit var pref: View
	var prefScreen: Int = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		prefScreen = intent.getIntExtra(Config.SETTING_INTENT, Config.SYNC_UI)
		SettingsUI(prefScreen).setContentView(this)
		pref = find<View>(R.id.settingPrefId)
		super.setUp()
		initUI()
		when (prefScreen) {
			Config.SYNC_UI -> syncUI()
		}
	}
	
	fun initUI() {
		when (prefScreen) {
			Config.SYNC_UI -> {
				val sync_enable = pref.find<MaterialSwitchPreference>(R.id.sync_enable)
				if (sync_enable.value) sync_enable.setSummary("Sync is Enabled") else sync_enable.setSummary("Sync is Disabled")
				
				
			}
		}
	}
	
	fun syncUI() {
		
		val sync_enable = pref.find<MaterialSwitchPreference>(R.id.sync_enable)
		sync_enable.onClick {
			if (sync_enable.value) {
				sync_enable.setSummary("Sync is Enabled")
				SnackbarUtils.showShort(it,"Contact Sync Enabled", Color.WHITE,color(R.color.md_green_400))
			} else {
				sync_enable.setSummary("Sync is Disabled")
				SnackbarUtils.showShort(it,"Contact Sync Disabled", Color.WHITE,color(R.color.md_red_400))
			}
		}
	}
}
