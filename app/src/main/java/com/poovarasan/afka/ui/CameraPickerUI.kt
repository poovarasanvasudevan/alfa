package com.poovarasan.afka.ui

import android.graphics.Color
import android.view.View
import com.github.clans.fab.FloatingActionButton
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.util.CameraPicker
import com.poovarasan.afka.core.cameraView
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
			cameraView {
				id = R.id.cameraView
			}.lparams(width = matchParent, height = matchParent) {
				topOf(R.id.cameraButtonLayout)
			}
			
			
			relativeLayout {
				id = R.id.cameraButtonLayout
				
				linearLayout {
					materialFAB {
						image = context.getIcon(GoogleMaterial.Icon.gmd_camera, Color.WHITE, 24)
						buttonSize = FloatingActionButton.SIZE_NORMAL
						id = R.id.cameraViewTakePic
						colorNormal = context.color(R.color.colorPrimary)
						colorPressed = context.color(R.color.colorPrimaryDark)
					}.lparams(width = wrapContent, height = wrapContent)
					
					materialFAB {
						image = context.getIcon(GoogleMaterial.Icon.gmd_close, Color.WHITE, 24)
						buttonSize = FloatingActionButton.SIZE_NORMAL
						id = R.id.cameraViewPicReject
						colorNormal = context.color(R.color.md_red_400)
						colorPressed = context.color(R.color.md_red_800)
						visibility = View.GONE
					}.lparams(width = wrapContent, height = wrapContent)
										
				}.lparams(width = wrapContent, height = wrapContent) {
					centerHorizontally()
					centerVertically()
				}
				
				backgroundColor = Color.WHITE
			}.lparams(width = matchParent, height = dip(80)) {
				alignParentBottom()
			}
			
			lparams(width = matchParent, height = matchParent)
		}
	}
	
}
