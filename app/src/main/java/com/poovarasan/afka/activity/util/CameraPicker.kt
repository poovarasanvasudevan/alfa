package com.poovarasan.afka.activity.util

import android.os.Bundle
import com.poovarasan.afka.base.BaseActivity
import com.poovarasan.afka.ui.CameraPickerUI
import org.jetbrains.anko.setContentView

/**
 * Created by poovarasanv on 24/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 24/5/17 at 3:52 PM
 */

class CameraPicker : BaseActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		CameraPickerUI().setContentView(this)
	}
}
