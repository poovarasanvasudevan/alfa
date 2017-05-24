package com.poovarasan.afka.job

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.RetryConstraint
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.core.*
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smackx.vcardtemp.packet.VCard
import java.io.ByteArrayOutputStream



/**
 * Created by poovarasanv on 12/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 12/5/17 at 12:43 PM
 */

class ProfilePicUploaderJob : Job(NETWORK_PARAMS) {
	
	init {
		
	}
	
	
	override fun onRun() {
		
		if (Prefs.contains("profile_pic")) {
			
			try {
				applicationContext.login()
				val xmpp = xmppConnect()
				
				
				val uri = Uri.fromFile(STORAGE.getFile(Config.PROFILE_PIC_FOLDER, Config.PROFILE_PIC_NAME))
				
				
				val image_stream = applicationContext.contentResolver.openInputStream(uri)
				val bitmap = BitmapFactory.decodeStream(image_stream)
				
				val vcard = VCard()
				vcard.load(xmpp)
				
				val stream = ByteArrayOutputStream()
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
				vcard.avatar = stream.toByteArray()
				vcard.save(xmpp)
				
				Log.i("ProfilePic","Profile PIc Updated")
				
			}catch (e:Exception) {
				if (e is SmackException.NoResponseException) {
					applicationContext.reconnect()
				}
			}
			
		}
		
	}
	
	override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint = RetryConstraint.RETRY
	
	
	override fun onAdded() {
		
	}
	
	override fun onCancel(cancelReason: Int, throwable: Throwable?) {
		
	}
}
