package com.poovarasan.afka.ui

import android.os.Build
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.util.PhotoPicker
import com.poovarasan.afka.core.materialAppbar
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by poovarasanv on 19/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 3:41 PM
 */

class PhotoPickerUI : AnkoComponent<PhotoPicker> {
	override fun createView(ui: AnkoContext<PhotoPicker>) = with(ui) {
		verticalLayout {
			val appbar = materialAppbar(theme = R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
				lparams(width = matchParent, height = dip(56))
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					elevation = dip(4).toFloat()
				}
			}
			
			relativeLayout {
				recyclerView {
					id = R.id.pickImageList
					lparams(width = matchParent, height = matchParent)
				}
				
				
				lparams(width = matchParent, height = matchParent)
				//backgroundColor = Color.BLACK
			}
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
