package com.poovarasan.afka.ui

import android.graphics.Color
import com.poovarasan.afka.R
import com.poovarasan.afka.core.ferescoImage
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:05 PM
 */

class SettingUI<T> : AnkoComponent<T> {
	override fun createView(ui: AnkoContext<T>) = with(ui) {
		relativeLayout {
			
			relativeLayout {
				id = R.id.accountHeader
				backgroundColor = Color.WHITE
				
				ferescoImage {
					id = R.id.myProfileImage
					lparams(width = dip(90), height = dip(90)) {
						centerVertically()
					}
				}
				
				
				lparams(width = matchParent, height = dip(130)) {
					padding = dip(10)
				}
			}
			
			
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
