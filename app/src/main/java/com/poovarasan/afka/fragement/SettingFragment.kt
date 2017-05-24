package com.poovarasan.afka.fragement

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.Settings
import com.poovarasan.afka.circularimage.BitmapDrawer
import com.poovarasan.afka.circularimage.CircularDrawable
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.core.*
import com.poovarasan.afka.job.ProfilePicUploaderJob
import com.poovarasan.afka.picker.Matisse
import com.poovarasan.afka.picker.MimeType
import com.poovarasan.afka.picker.engine.impl.FerescoEngine
import com.poovarasan.afka.ui.SettingUI
import com.wangjie.shadowviewhelper.ShadowProperty
import com.wangjie.shadowviewhelper.ShadowViewDrawable
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import com.yarolegovich.mp.MaterialStandardPreference
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.sdk25.listeners.onLongClick
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import org.jivesoftware.smackx.vcardtemp.VCardManager
import org.jivesoftware.smackx.vcardtemp.packet.VCard
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import java.io.File


/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:11 PM
 */

class SettingFragment : Fragment() {
	
	val GALLERY_PICK = 1520
	
	val WRITE_PERMISSION_CALLBACK = object : PermissionCallback {
		override fun permissionGranted() = storeMyProfilePic(profilePic)
		override fun permissionRefused() = toast("Unable to Store Images")
	}
	val READ_PERMISSION_CALLBACK = object : PermissionCallback {
		override fun permissionGranted() = activity.processWithPermission({ profileImage!!.syncProfilePic() }, Manifest.permission.READ_EXTERNAL_STORAGE, this)
		
		
		override fun permissionRefused() {
			profileImage!!.defaultImage()
			toast("Unable to Read Images")
		}
		
	}
	
	var profileImage: SimpleDraweeView? = null
	var vcardManager: VCardManager? = null
	var profilePic: Bitmap? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	
	fun dip2px(context: Context, dpValue: Float): Int {
		val scale = context.resources.displayMetrics.density
		return (dpValue * scale + 0.5f).toInt()
	}
	
	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view: View = SettingUI<android.support.v4.app.Fragment>().createView(AnkoContext.create(ctx, this))
		
		val accountHeader: RelativeLayout = view.find(R.id.accountHeader)
		profileImage = view.find(R.id.myProfileImage)
		
		val sp = ShadowProperty()
			.setShadowColor(0x77000000)
			.setShadowDy(dip2px(activity, 0.2f))
			.setShadowRadius(dip2px(activity, 0.5f))
			.setShadowSide(ShadowProperty.BOTTOM)
		
		val sd = ShadowViewDrawable(sp, Color.WHITE, 0f, 0f)
		ViewCompat.setBackground(accountHeader, sd)
		ViewCompat.setLayerType(accountHeader, ViewCompat.LAYER_TYPE_SOFTWARE, null)
		
		vcardManager = VCardManager.getInstanceFor(xmppConnect())
		profileImage!!.hierarchy = context.ferescoRoundedHeirarchy()
		
		if (Prefs.contains("profile_pic")) {
			activity.processWithPermission({ profileImage!!.syncProfilePic() }, Manifest.permission.READ_EXTERNAL_STORAGE, READ_PERMISSION_CALLBACK)
		} else {
			doAsync {
				context.internetAvailable({
					val vcard = VCard()
					vcard.load(xmppConnect())
					val avatar = vcard.avatar
					
					if (avatar != null) {
						val bmp = BitmapFactory.decodeByteArray(avatar, 0, avatar.size)
						
						uiThread {
							val cimage = CircularDrawable()
							
							val bImagge = BitmapDrawer()
							bImagge.bitmap = bmp
							cimage.setBitmapOrTextOrIcon(bImagge)
							
							
							profileImage!!.setImageDrawable(cimage)
						}
					} else {
						profileImage!!.setImageURI("http://1.bp.blogspot.com/-A61kc6tq7kw/Tpa1PN87EYI/AAAAAAAAAq4/bhsQHHCGmAg/s1600/dennis_ritchie_bw_square.jpg")
					}
					
				}, { profileImage!!.defaultImage() })
			}
		}
		
		val prefLayout = view.find<View>(R.id.contactPrefId)
		
		prefLayout.find<MaterialStandardPreference>(R.id.sync_settings).onClick {
			val intent = Intent(activity, Settings::class.java)
			intent.putExtra(Config.SETTING_INTENT, Config.SYNC_UI)
			startActivity(intent)
		}
		
		profileImage!!.onLongClick {
			
			
			BottomSheet
				.Builder(context)
				.setTitle("Choose Image")
				//.grid()
				.setSheet(R.menu.profile_pic_selection)
				.setListener(object : BottomSheetListener {
					override fun onSheetItemSelected(p0: BottomSheet, p1: MenuItem?) {
						when (p1!!.itemId) {
							R.id.camera_take  -> {
								
							}
							
							R.id.gallery_take -> {
								Matisse
									.from(act)
									.choose(MimeType.ofAll())
									.countable(true)
									.maxSelectable(1)
									.gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
									.restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
									.thumbnailScale(0.85f)
									.imageEngine(FerescoEngine())
									.forResult(GALLERY_PICK)
							}
						}
					}
					
					override fun onSheetDismissed(p0: BottomSheet, p1: Int) {
						
					}
					
					override fun onSheetShown(p0: BottomSheet) {
						
					}
					
				})
				.show()
			
			true
		}
		
		return view
	}
	
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK) {
			val mSelected = Matisse.obtainResult(data)
			
			val options = UCrop.Options()
			options.setStatusBarColor(context.color(R.color.colorPrimaryDark))
			options.setToolbarColor(context.color(R.color.colorPrimary))
			options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL)
			options.setImageToCropBoundsAnimDuration(666)
			options.setShowCropFrame(true)
			
			UCrop
				.of(mSelected[0], Uri.fromFile(File(context.cacheDir, "me.png")))
				.withAspectRatio(1f, 1f)
				.withOptions(options)
				.start(activity)
			
			
		}
		
		if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
			val resultUri = UCrop.getOutput(data!!)
			val bitmap = BitmapFactory.decodeStream(activity.contentResolver.openInputStream(resultUri))
			profilePic = bitmap
			storeMyProfilePic(bitmap)
			JOB.addJobInBackground(ProfilePicUploaderJob())
			
		} else if (resultCode == UCrop.RESULT_ERROR) {
			val cropError = UCrop.getError(data!!)
			if (cropError != null) {
				Log.e("CROP", "handleCropError: ", cropError)
			}
		}
		
	}
	
	private fun storeMyProfilePic(bitmap: Bitmap?) {
		if (bitmap != null) {
			activity.processWithPermission({
				STORAGE.createDirectory(Config.PROFILE_PIC_FOLDER)
				if (STORAGE.createFile(Config.PROFILE_PIC_FOLDER, Config.PROFILE_PIC_NAME, bitmap)) {
					Prefs.putString("profile_pic", "${Config.PROFILE_PIC_FOLDER}${File.separator}${Config.PROFILE_PIC_NAME}")
				}
				activity.processWithPermission({ profileImage!!.syncProfilePic() }, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_PERMISSION_CALLBACK)
			}, Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_PERMISSION_CALLBACK)
		}
	}
	
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}
	
}
