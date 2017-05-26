package com.poovarasan.afka.ui

import android.graphics.Color
import com.github.clans.fab.FloatingActionButton
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.util.CameraPicker
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.getIcon
import com.poovarasan.afka.core.materialFAB
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 24/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 24/5/17 at 3:54 PM
 */

class CameraPickerUI : AnkoComponent<CameraPicker> {
	override fun createView(ui: AnkoContext<CameraPicker>) = with(ui) {
		relativeLayout {
			surfaceView {
				
				
			}.lparams(width = matchParent, height = matchParent)
			
			
			relativeLayout {
				materialFAB {
					image = context.getIcon(GoogleMaterial.Icon.gmd_camera, Color.WHITE, 24)
					backgroundColor = context.color(R.color.colorPrimary)
					buttonSize = FloatingActionButton.SIZE_NORMAL
				}.lparams(width = wrapContent, height = wrapContent) {
					centerHorizontally()
				}
			}.lparams(width = matchParent, height = dip(100)) {
				alignParentBottom()
			}
			
			lparams(width = matchParent, height = matchParent)
		}
	}
	
}
