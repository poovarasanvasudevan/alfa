package com.poovarasan.afka.ui

import android.graphics.Color
import com.poovarasan.afka.R
import com.poovarasan.afka.core.btnRectangle
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.materialRecycularview
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:05 PM
 */

class ContactUI<T> : AnkoComponent<T> {
	override fun createView(ui: AnkoContext<T>) = with(ui) {
		relativeLayout {
			
			relativeLayout {
				id = R.id.contactListFillLayout
				val list = materialRecycularview {
					id = R.id.contactList
				}.lparams(width = matchParent, height = matchParent)
			}.lparams(width = matchParent, height = matchParent)
			
			verticalLayout {
				id = R.id.syncContactLayout
				
				themedTextView {
					text = "Allow Afka to Access Contacts"
					textSize = dip(5).toFloat()
				}.lparams {
					margin = dip(8)
				}
				btnRectangle {
					text = "Access Contacts"
					id = R.id.accessContactButton
					backgroundColor = context.color(R.color.colorPrimary)
					setTextColor(Color.WHITE)
				}
			}.lparams(width = wrapContent, height = wrapContent) {
				centerVertically()
				centerHorizontally()
			}
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
