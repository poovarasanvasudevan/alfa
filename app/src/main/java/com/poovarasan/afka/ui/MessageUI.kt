package com.poovarasan.afka.ui

import android.graphics.Color
import com.github.clans.fab.FloatingActionButton
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.poovarasan.afka.R
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.getIcon
import com.poovarasan.afka.core.materialFAB
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 4/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 4/5/17 at 3:04 PM
 */

class MessageUI<T> : AnkoComponent<T> {
	override fun createView(ui: AnkoContext<T>) = with(ui) {
		relativeLayout {
			
			
			materialFAB(theme = R.style.ThemeOverlay_AppCompat_Dark) {
				setBackgroundColor(context.color(R.color.colorPrimary))
				colorNormal = context.color(R.color.colorPrimary)
				colorPressed = context.color(R.color.colorPrimaryDark)
				colorRipple = context.color(R.color.colorPrimaryDark)
				setShowShadow(true)
				buttonSize = FloatingActionButton.SIZE_NORMAL
				setImageDrawable(context.getIcon(icon = GoogleMaterial.Icon.gmd_add, color = Color.WHITE, size = 15))
				lparams(width = dip(55), height = dip(55)) {
					alignParentBottom()
					alignParentRight()
					margin = dip(16)
				}
			}
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
