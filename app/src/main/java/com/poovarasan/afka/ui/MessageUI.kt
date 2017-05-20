package com.poovarasan.afka.ui

import android.view.View
import com.poovarasan.afka.R
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


			include<View>(R.layout.message_fab) {

			}.lparams(width= wrapContent,height = wrapContent) {
				alignParentBottom()
				alignParentRight()
				margin = dip(16)
			}
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
