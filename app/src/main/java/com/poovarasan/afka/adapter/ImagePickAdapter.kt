package com.poovarasan.afka.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ScreenUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.mikepenz.fastadapter.items.AbstractItem
import com.poovarasan.afka.R
import com.poovarasan.afka.core.ferescoHeirarchyWithoutProgress
import org.jetbrains.anko.find

/**
 * Created by poovarasanv on 19/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 5:19 PM
 */

class ImagePickAdapter(val pickName: String, val pickImage: String) : AbstractItem<ImagePickAdapter, ImagePickAdapter.ViewHolder>() {
	
	override fun getType(): Int = 87
	override fun getLayoutRes(): Int = R.layout.image_pick_layout
	override fun getViewHolder(v: View?): ViewHolder = ViewHolder(v)
	override fun bindView(holder: ViewHolder?, payloads: MutableList<Any>?) {
		super.bindView(holder, payloads)
		
		val width = ScreenUtils.getScreenWidth() /4
		Log.i("Screen Width","Screen ${width}")
//
		val layoutParams = holder!!.pickHolder.layoutParams
		layoutParams.width = width
		layoutParams.height = width
		holder.pickHolder.layoutParams = layoutParams
//
		holder.pickImage.hierarchy = holder.pickImage.context.ferescoHeirarchyWithoutProgress()
		holder.pickImage.setImageURI("file://${pickImage}")
	}
	
	class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
		val pickImage = view!!.find<SimpleDraweeView>(R.id.pick_photo)
		val pickHolder = view!!.find<RelativeLayout>(R.id.picker_holder)
	}
}
