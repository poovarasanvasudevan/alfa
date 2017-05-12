package com.poovarasan.afka.ui

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:05 PM
 */

class CallUI<T> : AnkoComponent<T> {
	override fun createView(ui: AnkoContext<T>) = with(ui) {
		relativeLayout {
			
			
			lparams(width = matchParent, height = matchParent)
		}
	}
}
