package com.poovarasan.afka.fragement

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.flipkart.circularImageView.BitmapDrawer
import com.flipkart.circularImageView.CircularDrawable
import com.poovarasan.afka.R
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.core.*
import com.poovarasan.afka.job.ProfilePicUploaderJob
import com.poovarasan.afka.ui.SettingUI
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.enums.EPickType
import com.wangjie.shadowviewhelper.ShadowProperty
import com.wangjie.shadowviewhelper.ShadowViewDrawable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.listeners.onLongClick
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
	
	
	val WRITE_PERMISSION_CALLBACK = object : PermissionCallback {
		override fun permissionGranted() {
			storeMyProfilePic(profilePic)
		}
		
		override fun permissionRefused() {
			toast("Unable to Store Images")
		}
	}
	val READ_PERMISSION_CALLBACK = object : PermissionCallback {
		override fun permissionGranted() {
			activity.processWithPermission(
				{ profileImage!!.syncProfilePic() },
				Manifest.permission.READ_EXTERNAL_STORAGE,
				this
			)
		}
		
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
			.setShadowDy(dip2px(activity, 0.5f))
			.setShadowRadius(dip2px(activity, 1f))
			.setShadowSide(ShadowProperty.BOTTOM)
		val sd = ShadowViewDrawable(sp, Color.WHITE, 0f, 0f)
		ViewCompat.setBackground(accountHeader, sd)
		ViewCompat.setLayerType(accountHeader, ViewCompat.LAYER_TYPE_SOFTWARE, null)
		
		vcardManager = VCardManager.getInstanceFor(xmppConnect())
		profileImage!!.hierarchy = context.ferescoRoundedHeirarchy()
		
		if (Prefs.contains("profile_pic")) {
			activity.processWithPermission(
				{ profileImage!!.syncProfilePic() },
				Manifest.permission.READ_EXTERNAL_STORAGE,
				READ_PERMISSION_CALLBACK
			)
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
				}, {
					profileImage!!.defaultImage()
				})
				
			}
		}
		
		
		profileImage!!.onLongClick {
			
			val setup = PickSetup()
				.setTitle("Choose Profile Image")
				.setTitleColor(Color.WHITE)
				.setBackgroundColor(Color.WHITE)
				.setProgressText("Loading")
				.setProgressTextColor(Color.BLACK)
				.setCancelText("Cancel")
				.setFlip(true)
				.setMaxSize(500)
				.setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
				.setCameraButtonText("Camera")
				.setGalleryButtonText("Gallery")
				.setButtonOrientation(LinearLayoutCompat.HORIZONTAL)
			
			
			
			PickImageDialog.build(setup)
				.setOnPickResult { r ->
					profilePic = r.bitmap
					storeMyProfilePic(r.bitmap)
					JOB.addJobInBackground(ProfilePicUploaderJob(NETWORK_PARAMS))
				}.show(activity.supportFragmentManager)
			
			
			true
		}
		
		return view
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
		Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
	
}
