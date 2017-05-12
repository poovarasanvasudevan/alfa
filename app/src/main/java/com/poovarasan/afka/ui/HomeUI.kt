package com.poovarasan.afka.ui

import android.graphics.Color
import android.os.Build
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.Home
import com.poovarasan.afka.core.color
import com.poovarasan.afka.core.materialAppbar
import com.poovarasan.afka.core.materialTabLayout
import com.poovarasan.afka.core.materialViewPager
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 2:59 PM
 */

class HomeUI : AnkoComponent<Home> {
	override fun createView(ui: AnkoContext<Home>) = with(ui) {
		verticalLayout {
			val appbar = materialAppbar(theme = R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
				lparams(width = matchParent, height = dip(56))
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					elevation = dip(4).toFloat()
				}
				
				materialTabLayout(theme = R.style.ThemeOverlay_AppCompat_Dark) {
					id = R.id.tabLayout
					setSelectedTabIndicatorColor(Color.WHITE)
					backgroundColor = context.color(R.color.colorPrimary)
					lparams(width = matchParent, height = matchParent)
				}
			}
			
			relativeLayout {
				materialViewPager {
					id = R.id.viewPager
					lparams(width = matchParent, height = wrapContent)
				}
				lparams(width = matchParent, height = wrapContent) {
					below(appbar)
				}
			}
			
			lparams {
				width = matchParent
				height = matchParent
			}
		}
	}
}
