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
import com.yarolegovich.mp.MaterialChoicePreference
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
		
		prefScreen = intent.getIntExtra(Config.SETTING_INTENT, 0)
		SettingsUI(prefScreen).setContentView(this)
		pref = find<View>(R.id.settingPrefId)
		super.setUp()
		initUI()
		when (prefScreen) {
			Config.SYNC_UI -> syncUI()
			Config.BACKUP_UI -> backupUI()
		}
	}
	
	fun initUI() {
		when (prefScreen) {
			Config.SYNC_UI -> {
				val sync_enable = pref.find<MaterialSwitchPreference>(R.id.sync_enable)
				val sync_contact_time: MaterialChoicePreference = pref.find<MaterialChoicePreference>(R.id.sync_contact_time)
				if (sync_enable.value) {
					sync_enable.setSummary(getString(R.string.sync_enabled))
					sync_contact_time.isEnabled = true
					sync_contact_time.setBackgroundColor(Color.WHITE)
				} else {
					sync_enable.setSummary(getString(R.string.sync_disabled))
					sync_contact_time.isEnabled = false
					sync_contact_time.setBackgroundColor(Color.parseColor("#EAEAEA"))
				}
			}
			
			Config.BACKUP_UI -> {
				
			}
		}
	}
	
	fun backupUI() {
		
	}
	
	fun syncUI() {
		val sync_enable = pref.find<MaterialSwitchPreference>(R.id.sync_enable)
		val sync_contact_time = pref.find<MaterialChoicePreference>(R.id.sync_contact_time)
		sync_enable.onClick {
			if (sync_enable.value) {
				sync_enable.setSummary(getString(R.string.sync_enabled))
				sync_contact_time.isEnabled = true
				sync_contact_time.setBackgroundColor(Color.WHITE)
				SnackbarUtils.showShort(it, "Contact Sync Enabled", Color.WHITE, color(R.color.md_green_400))
			} else {
				sync_enable.setSummary(getString(R.string.sync_disabled))
				sync_contact_time.isEnabled = false
				sync_contact_time.setBackgroundColor(Color.parseColor("#EAEAEA"))
				SnackbarUtils.showShort(it, "Contact Sync Disabled", Color.WHITE, color(R.color.md_red_400))
			}
		}
	}
}
