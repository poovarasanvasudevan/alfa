package com.poovarasan.afka.ui

import com.poovarasan.afka.MainActivity
import com.poovarasan.afka.R
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.materialProgressBar
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 3/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 3/5/17 at 3:41 PM
 */

class MainActivityUI : AnkoComponent<MainActivity> {
	override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
		relativeLayout {
			materialProgressBar {
				spin()
				barColor = context.color(R.color.colorPrimary)
				circleRadius = dip(100)
				barWidth = dip(3)
			}.lparams(width = dip(40), height = dip(40)) {
				alignParentBottom()
				centerHorizontally()
				bottomMargin = dip(50)
			}
			lparams(width = matchParent, height = matchParent)
		}
	}
}
