package com.poovarasan.afka.core

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.flipkart.circularImageView.BitmapDrawer
import com.flipkart.circularImageView.CircularDrawable
import com.mikepenz.iconics.IconicsDrawable
import com.poovarasan.afka.R
import com.poovarasan.afka.app.Alfa
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.storage.Storage
import com.poovarasan.afka.widget.FerescoProgressBar
import org.jetbrains.anko.connectivityManager
import org.jetbrains.anko.telephonyManager
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import java.util.*


/**
 * Created by poovarasanv on 4/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 4/5/17 at 9:06 AM
 */
fun Context.color(@ColorRes color: Int): Int {
	return ContextCompat.getColor(this, color)
}

fun Context.getIcon(icon: com.mikepenz.google_material_typeface_library.GoogleMaterial.Icon, color: Int = Color.WHITE, size: Int = 24): Drawable {
	return IconicsDrawable(this)
		.icon(icon)
		.color(color)
		.sizeDp(size)
}

fun Context.ferescoHeirarchy(): GenericDraweeHierarchy {
	val builder = GenericDraweeHierarchyBuilder(resources)
	val hierarchy = builder
		.setFadeDuration(300)
		.setProgressBarImage(FerescoProgressBar())
		.build()
	
	return hierarchy
}

fun Context.ferescoRoundedHeirarchy(): GenericDraweeHierarchy {
	
	val color = Color.WHITE
	val roundingParams = RoundingParams.fromCornersRadius(5f)
	roundingParams.setBorder(color, 0.4f)
	roundingParams.roundAsCircle = true
	
	val hierarchy = ferescoHeirarchy()
	hierarchy.roundingParams = roundingParams
	
	return hierarchy
}

fun randomColor(): Int {
	
	val ran = Random()
	
	val r = (ran.nextInt(255) * ran.nextInt(255)) % 255
	val g = (ran.nextInt(255) * ran.nextInt(255)) % 255
	val b = (ran.nextInt(255) * ran.nextInt(255)) % 255
	
	return Color.rgb(r, g, b)
}


val STORAGE: Storage
	get() = Alfa.storage


val JOB: JobManager
	get() = Alfa.jobManager


fun xmppConnect(): XMPPTCPConnection {
	return Alfa.xmppConnection
}

val NETWORK_PARAMS: Params =
	Params(1)
		.requireNetwork()
		.persist()


fun Context.login() {
	
	if (Prefs.contains("username") && Prefs.contains("password")) {
		val username = Prefs.getString("username", "")
		val password = Prefs.getString("password", "")
		
		try {
			val xmpp = xmppConnect()
			
			if (isConnected()) {
				//if (xmpp.isConnected) xmpp.disconnect()
				if (!xmpp.isConnected) xmpp.connect()
				if (!xmpp.isAuthenticated) xmppConnect().login(username, password)
			}
		} catch (e: Exception) {
			if (e is SmackException.AlreadyConnectedException) {
				
			}
			
			if (e is SmackException.AlreadyLoggedInException) {
				
			}
		}
	}
}


fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
	val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
	for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
		if (serviceClass.name == service.service.className) {
			return true
		}
	}
	return false
}

fun Context.reconnect() {
	
	if (Prefs.contains("username") && Prefs.contains("password")) {
		val username = Prefs.getString("username", "")
		val password = Prefs.getString("password", "")
		
		val xmpp = xmppConnect()
		
		if (isConnected()) {
			if (xmpp.isConnected) xmpp.disconnect()
			if (!xmpp.isConnected) xmpp.connect()
			if (!xmpp.isAuthenticated) xmppConnect().login(username, password)
		}
	}
}


fun Activity.processWithPermission(f: () -> Unit, premission: String, callback: PermissionCallback) {
	if (Nammu.checkPermission(premission)) {
		f()
	} else {
		Nammu.askForPermission(this, premission, callback)
	}
}

fun Context.internetAvailable(f: () -> Unit, n: () -> Unit) {
	if (isConnected()) {
		f()
	} else {
		n()
	}
}


fun SimpleDraweeView.syncProfilePic() {
	if (Prefs.contains("profile_pic")) {
		val uri = Uri.fromFile(STORAGE.getFile(Config.PROFILE_PIC_FOLDER, Config.PROFILE_PIC_NAME))
		
		val image_stream = context.contentResolver.openInputStream(uri)
		val bitmap = BitmapFactory.decodeStream(image_stream)
		
		val circularDrawable = CircularDrawable()
		val bmp = BitmapDrawer()
		bmp.bitmap = bitmap
		circularDrawable.setBitmapOrTextOrIcon(bmp)
		this.setImageDrawable(circularDrawable)
	}
}

fun Context.drawable(@DrawableRes id: Int): Drawable {
	return ContextCompat.getDrawable(this, id)
}

fun SimpleDraweeView.defaultImage() {
	this.setImageDrawable(context.drawable(R.drawable.default_pic))
}

//cursor
fun Cursor.intValue(columnName: String): Int {
	return getInt(getColumnIndexOrThrow(columnName))
}

fun Cursor.shortValue(columnName: String): Short {
	return getShort(getColumnIndexOrThrow(columnName))
}

fun Cursor.longValue(columnName: String): Long {
	return getLong(getColumnIndexOrThrow(columnName))
}

fun Cursor.doubleValue(columnName: String): Double {
	return getDouble(getColumnIndexOrThrow(columnName))
}

fun Cursor.floatValue(columnName: String): Float {
	return getFloat(getColumnIndexOrThrow(columnName))
}


fun Cursor.stringValue(columnName: String): String {
	return getString(getColumnIndexOrThrow(columnName))
}

fun Cursor.booleanValue(columnName: String): Boolean {
	return getInt(getColumnIndexOrThrow(columnName)) != 0
}

inline fun <T> Cursor?.map(transform: Cursor.() -> T): MutableCollection<T> {
	return mapTo(arrayListOf<T>(), transform)
}

inline fun <T, R : MutableCollection<T>> Cursor?.mapTo(result: R, transform: Cursor.() -> T): R {
	return if (this == null) result else {
		if (moveToFirst())
			do {
				result.add(transform())
			} while (moveToNext())
		result
	}
}

inline fun <T> Cursor?.mapAndClose(transform: Cursor.() -> T): MutableCollection<T> {
	try {
		return map(transform)
	} finally {
		this?.close()
	}
}

//platform

fun beforeMarshmallow(): Boolean = isOlderVersionThen(23)

fun marshmallowOrNewer(): Boolean = isOnVersionOrNewer(23)
fun beforeLollipop(): Boolean = isOlderVersionThen(21)
fun lollipopOrNewer(): Boolean = isOnVersionOrNewer(21)
fun beforeKitkat(): Boolean = isOlderVersionThen(19)
fun kitkatOrNewer(): Boolean = isOnVersionOrNewer(19)
fun beforeIcs(): Boolean = isOlderVersionThen(14)
fun icsOrNewer(): Boolean = isOnVersionOrNewer(14)
fun beforeVersion(version: Int): Boolean = isOlderVersionThen(version)
fun versionOrNewer(version: Int): Boolean = isOnVersionOrNewer(version)
fun currentVersion(): Int = Build.VERSION.SDK_INT
private fun isOlderVersionThen(version: Int) = Build.VERSION.SDK_INT < version
private fun isOnVersionOrNewer(version: Int) = Build.VERSION.SDK_INT >= version

//network
enum class NetworkType {
	WIFI, MOBILE, OTHER, NONE
}

fun Context.networkTypeName(): String {
	var result = "(No Network)"
	try {
		val cm = this.connectivityManager
		val info = cm.activeNetworkInfo
		if (info == null || !info.isConnectedOrConnecting) {
			return result
		}
		result = info.typeName
		if (info.type == ConnectivityManager.TYPE_MOBILE) {
			result += info.subtypeName
		}
	} catch (ignored: Throwable) {
	}
	return result
}

fun Context.networkOperator(): String {
	val tm = this.telephonyManager
	return tm.networkOperator
}

fun Context.networkType(): NetworkType {
	val cm = this.connectivityManager
	val info = cm.activeNetworkInfo
	if (info == null || !info.isConnectedOrConnecting) {
		return NetworkType.NONE
	}
	val type = info.type
	if (ConnectivityManager.TYPE_WIFI == type) {
		return NetworkType.WIFI
	} else if (ConnectivityManager.TYPE_MOBILE == type) {
		return NetworkType.MOBILE
	} else {
		return NetworkType.OTHER
	}
}

fun Context.isWifi(): Boolean {
	return networkType() == NetworkType.WIFI
}

fun Context.isMobile(): Boolean {
	return networkType() == NetworkType.MOBILE
}

fun Context.isConnected(): Boolean {
	val cm = this.connectivityManager
	val info = cm.activeNetworkInfo
	return info != null && info.isConnectedOrConnecting
}