package com.poovarasan.afka.core

import android.app.Activity
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.support.v4.app.Fragment
import java.util.*

/**
 * Created by poovarasanv on 12/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 12/5/17 at 10:02 AM
 */

class ShortcutHelper {
	private var mShortcutManager: ShortcutManager? = null

	fun createShortcut(shortLabel: CharSequence, longLabel: CharSequence, iconResource: Int, intent: Intent): ShortcutHelper {
		if (Build.VERSION.SDK_INT < 25) {
			return this
		}
		val shortcutId = shortLabel.toString().replace("\\s+".toRegex(), "").toLowerCase() + "_shortcut"
		val shortcut = ShortcutInfo.Builder(mActivity, shortcutId)
			.setShortLabel(shortLabel)
			.setLongLabel(longLabel)
			.setIcon(Icon.createWithResource(mActivity, iconResource))
			.setIntent(intent)
			.build()
		mShortcutInfos.add(shortcut)
		return this
	}

	fun createShortcut(shortLabel: CharSequence, longLabel: CharSequence, iconResource: Int, intents: Array<Intent>): ShortcutHelper {
		if (Build.VERSION.SDK_INT < 25) {
			return this
		}
		val shortcutId = shortLabel.toString().replace("\\s+".toRegex(), "").toLowerCase() + "_shortcut"
		val shortcut = ShortcutInfo.Builder(mActivity, shortcutId)
			.setShortLabel(shortLabel)
			.setLongLabel(longLabel)
			.setIcon(Icon.createWithResource(mActivity, iconResource))
			.setIntents(intents)
			.build()
		mShortcutInfos.add(shortcut)
		return this
	}

	fun go() {
		if (Build.VERSION.SDK_INT < 25) {
			return
		}
		mShortcutManager = mActivity!!.getSystemService(ShortcutManager::class.java)
		mShortcutManager!!.dynamicShortcuts = mShortcutInfos
	}

	companion object {

		private var mActivity: Activity? = null
		private val mShortcutInfos = ArrayList<ShortcutInfo>()

		fun with(activity: Activity): ShortcutHelper {
			mActivity = activity
			return ShortcutHelper()
		}

		fun with(fragment: Fragment): ShortcutHelper {
			mActivity = fragment.activity
			return ShortcutHelper()
		}
	}

}