package com.poovarasan.afka.ui

import android.graphics.Color
import com.poovarasan.afka.R
import com.poovarasan.afka.core.ferescoImage
import com.poovarasan.afka.core.loaderText
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
				
				val userImage = ferescoImage {
					id = R.id.myProfileImage
					lparams(width = dip(80), height = dip(80)) {
						centerVertically()
					}
				}
				
				verticalLayout {
					
					loaderText {
						
						lparams(width = matchParent, height = matchParent) {
							weight = 1f
							topMargin = dip(8)
							bottomMargin = dip(8)
						}
					}
					
					
					loaderText {
						
						setLoaderHeight(dip(30))
						
						android.os.Handler().postDelayed({
							text = "Online"
							textColor = Color.BLACK
							textSize = dip(6).toFloat()
						}, 3000)
						
						lparams(width = matchParent, height = matchParent) {
							weight = 1f
							topMargin = dip(8)
							bottomMargin = dip(8)
							
						}
					}
					
					
				}.lparams(width = matchParent, height = matchParent) {
					rightOf(userImage)
					leftMargin = dip(10)
				}
				
				
				lparams(width = matchParent, height = dip(110)) {
					padding = dip(10)
				}
			}
			
			
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
