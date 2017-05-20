package com.poovarasan.afka.activity.util

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter_extensions.ActionModeHelper
import com.poovarasan.afka.R
import com.poovarasan.afka.adapter.ImagePickAdapter
import com.poovarasan.afka.adapter.PhotoPickCallback
import com.poovarasan.afka.core.getAllImages
import com.poovarasan.afka.ui.PhotoPickerUI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.uiThread


/**
 * Created by poovarasanv on 19/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 3:40 PM
 */

class PhotoPicker : AppCompatActivity() {
	lateinit var fastAdapter: FastItemAdapter<ImagePickAdapter>
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		PhotoPickerUI().setContentView(this)
		
		fastAdapter = FastItemAdapter()
		fastAdapter.setHasStableIds(true);
		fastAdapter.withSelectable(true);
		fastAdapter.withMultiSelect(true);
		fastAdapter.withSelectOnLongClick(true);
		
		val actionModeHelper = ActionModeHelper(fastAdapter, R.menu.photo_pick_menu, PhotoPickCallback())
		val photoList = find<RecyclerView>(R.id.pickImageList)
		
		
		fastAdapter.withOnPreClickListener { v, adapter, item, position ->
			val res = actionModeHelper.onClick(item)
			res ?: false
		}
		
		fastAdapter.withOnPreLongClickListener { v, adapter, item, position ->
			val actionMode = actionModeHelper.onLongClick(this@PhotoPicker, position)
			if (actionMode != null) {
				//findViewById(R.id.action_mode_bar).setBackgroundColor(Color.GRAY)
			}
			actionMode != null
		}
		doAsync {
			val imageList = getAllImages()
			
			uiThread {
				
				
				photoList.layoutManager = GridLayoutManager(this@PhotoPicker, 4)
				photoList.adapter = fastAdapter
				fastAdapter.add(imageList)
			}
		}
	}
}
