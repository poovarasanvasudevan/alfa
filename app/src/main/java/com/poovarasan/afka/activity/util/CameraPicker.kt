package com.poovarasan.afka.activity.util

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.github.clans.fab.FloatingActionButton
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.poovarasan.afka.R
import com.poovarasan.afka.base.BaseActivity
import com.poovarasan.afka.core.getIcon
import com.poovarasan.afka.ui.CameraPickerUI
import net.bozho.easycamera.DefaultEasyCamera
import net.bozho.easycamera.EasyCamera
import org.jetbrains.anko.find
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk25.listeners.onClick
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
	
	
	lateinit var cameraViewTakePic: FloatingActionButton
	lateinit var cameraViewRemove: FloatingActionButton
	lateinit var cameraView: SurfaceView
	var cameraObj: android.hardware.Camera? = null
	lateinit var actions: EasyCamera.CameraActions
	var imageClicked = false
	var imageByte: ByteArray? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		CameraPickerUI().setContentView(this)
		
		cameraView = find(R.id.cameraView)
		val camera = DefaultEasyCamera.open()
		
		cameraView.holder.addCallback(object : SurfaceHolder.Callback {
			override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
				actions = camera.startPreview(cameraView.holder)
			}
			
			override fun surfaceDestroyed(holder: SurfaceHolder?) {
				
			}
			
			override fun surfaceCreated(holder: SurfaceHolder?) {
				
			}
			
		})
		
		val callback = EasyCamera.PictureCallback { data, action ->
			imageClicked = true
			imageByte = data
			
		}
		
		cameraObj = camera.rawCamera
		
		cameraViewTakePic = find(R.id.cameraViewTakePic)
		cameraViewRemove = find(R.id.cameraViewPicReject)
		cameraViewTakePic.onClick {
			if (imageClicked) {
				if (imageByte != null) {
					val intent = Intent()
					intent.putExtra("IMAGE_BYTE", imageByte)
					setResult(Activity.RESULT_OK, intent)
					finish()
				}
			} else {
				actions.takePicture(EasyCamera.Callbacks.create().withJpegCallback(callback))
				cameraViewTakePic.image = getIcon(GoogleMaterial.Icon.gmd_check, Color.WHITE, 24)
				cameraViewRemove.visibility = View.VISIBLE
				imageClicked = true
			}
		}
		
		cameraViewRemove.onClick {
			if (imageClicked) {
				actions = camera.startPreview(cameraView.holder)
			}
			imageClicked = false
			cameraViewRemove.visibility = View.GONE
		}
		
	}
	
	override fun onStart() {
		super.onStart()
	}
	
	override fun onStop() {
		super.onStop()
	}
	
	
	override fun onResume() {
		super.onResume()
	}
	
	override fun onPause() {
		super.onPause()
	}
	
	override fun onDestroy() {
		super.onDestroy()
	}
}
