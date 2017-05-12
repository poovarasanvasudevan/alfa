package com.poovarasan.afka.widget

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by poovarasanv on 12/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 12/5/17 at 10:13 AM
 */

class StartOffsetItemDecoration
/**
 * Sole constructor. Takes in the size of the offset to be added to the
 * start of the RecyclerView.
 
 * @param offsetPx The size of the offset to be added to the start of the
 * *                 RecyclerView in pixels
 */
(private val mOffsetPx: Int) : RecyclerView.ItemDecoration() {
	
	/**
	 * Determines the size and location of the offset to be added to the start
	 * of the RecyclerView.
	 
	 * @param outRect The [Rect] of offsets to be added around the child view
	 * *
	 * @param view The child view to be decorated with an offset
	 * *
	 * @param parent The RecyclerView onto which dividers are being added
	 * *
	 * @param state The current RecyclerView.State of the RecyclerView
	 */
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
		super.getItemOffsets(outRect, view, parent, state)
		
		if (parent.getChildAdapterPosition(view) < 1) {
			val orientation = (parent.layoutManager as LinearLayoutManager).orientation
			if (orientation == LinearLayoutManager.HORIZONTAL) {
				outRect.left = mOffsetPx
			} else if (orientation == LinearLayoutManager.VERTICAL) {
				outRect.top = mOffsetPx
			}
		}
	}
}
