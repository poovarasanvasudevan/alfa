package com.poovarasan.afka.core

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import android.provider.MediaStore
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.Log
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.mikepenz.iconics.IconicsDrawable
import com.poovarasan.afka.R
import com.poovarasan.afka.adapter.ImagePickAdapter
import com.poovarasan.afka.app.Alfa
import com.poovarasan.afka.circularimage.BitmapDrawer
import com.poovarasan.afka.circularimage.CircularDrawable
import com.poovarasan.afka.config.Config
import com.poovarasan.afka.receiver.RosterListener
import com.poovarasan.afka.service.XMPPChatManagerListener
import com.poovarasan.afka.service.XMPPChatMessageListener
import com.poovarasan.afka.service.XMPPConnectionListener
import com.poovarasan.afka.ssl.Factory
import com.poovarasan.afka.storage.Storage
import com.poovarasan.afka.widget.FerescoProgressBar
import org.jetbrains.anko.connectivityManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.telephonyManager
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.chat.ChatManager
import org.jivesoftware.smack.filter.StanzaTypeFilter
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.ping.PingManager
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager
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

@JvmOverloads fun Context.reboot(restartIntent: Intent = this.packageManager.getLaunchIntentForPackage(this.packageName)) {
	restartIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
	if (this is Activity) {
		this.startActivity(restartIntent)
		finishAffinity(this)
	} else {
		restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		this.startActivity(restartIntent)
	}
}

private fun finishAffinity(activity: Activity) {
	activity.setResult(Activity.RESULT_CANCELED)
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		activity.finishAffinity()
	} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
		activity.runOnUiThread { activity.finishAffinity() }
	}
}

fun Context.ferescoHeirarchyWithoutProgress(): GenericDraweeHierarchy {
	val builder = GenericDraweeHierarchyBuilder(resources)
	val hierarchy = builder
		.setFadeDuration(100)
		.build()
	
	return hierarchy
}

fun Context.ferescoRoundedHeirarchy(): GenericDraweeHierarchy {
	
	val color = color(R.color.colorPrimary)
	val roundingParams = RoundingParams.fromCornersRadius(5f)
	roundingParams.setBorder(color, 1f)
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
	return XMPPFactory.getXmpptcpConnection(CONTEXT)
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
				if (!xmpp.isAuthenticated) xmppConnect().login()
			}
		} catch (e: Exception) {
			if (e is SmackException.AlreadyConnectedException) {
				
			}
			
			if (e is SmackException.AlreadyLoggedInException) {
				
			}
		}
	}
}

val CONTEXT = Alfa.context
fun rui(f: () -> Unit) {
	val handler = android.os.Handler(Looper.getMainLooper());
	handler.post({
		f()
	})
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

fun reconnect() {
	
	if (Prefs.contains("username") && Prefs.contains("password")) {
		XMPPFactory.connect(CONTEXT, "", "")
	}
}

fun Context.getAllImages(): MutableList<ImagePickAdapter> {
	val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
	val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
	val cursor = this.contentResolver.query(uri, projection, null, null, null)
	val column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
	val column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
	
	val listModel: MutableList<ImagePickAdapter> = mutableListOf()
	while (cursor.moveToNext()) {
		
		Log.i("Here", cursor.getString(column_index_data))
		listModel.add(ImagePickAdapter(cursor.getString(column_index_folder_name), cursor.getString(column_index_data)))
	}
	cursor.close()

//    val int_uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
//    val int_cursor = this.contentResolver.query(int_uri, projection, null, null, null)
//    val int_column_index_data = int_cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//    val int_column_index_folder_name = int_cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
//
//    while (int_cursor.moveToNext()) {
//        listModel.add(ImagePickAdapter(int_cursor.getString(int_column_index_folder_name), int_cursor.getString(int_column_index_data)))
//    }
//    int_cursor.close()
	
	Log.i("PHOTO", "total Image ${listModel.count()}")
	
	return listModel
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

inline fun <reified T : Parcelable> createParcel(crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
	object : Parcelable.Creator<T> {
		override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
		override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
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


inline fun <T, R : MutableCollection<T>> Cursor?.mapTo(result: R, transform: Cursor.() -> T): R {
	return if (this == null) result else {
		if (moveToFirst())
			do {
				result.add(transform())
			} while (moveToNext())
		result
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

fun Context.setOnline() {
	doAsync {
		val xmpp = xmppConnect()
		if (xmpp.isConnected) {
			val pres = Presence(Presence.Type.available)
			xmppConnect().sendPacket(pres)
		}
	}
}

fun Context.setOffline() {
	doAsync {
		val xmpp = xmppConnect()
		if (xmpp.isConnected) {
			val pres = Presence(Presence.Type.unavailable)
			xmppConnect().sendPacket(pres)
		}
	}
}


fun buildXMPPConnection(): XMPPTCPConnection {
	val config1 = XMPPTCPConnectionConfiguration.builder()
		//.setServiceName("localhost")
		.setCompressionEnabled(true)
		.setXmppDomain("localhost")
		.setHost("10.0.2.2")
		.setDebuggerEnabled(true)
		.setConnectTimeout(10000000)
		.setCustomSSLContext(Factory.sslContext(CONTEXT))
		.setHostnameVerifier { hostname, session -> true }
	//.setPort(7555)
	
	
	if (Prefs.contains("username") && Prefs.contains("password")) {
		config1.setUsernameAndPassword(Prefs.getString("username", ""), Prefs.getString("password", ""))
	}
	val config = config1.build()
	
	
	XMPPTCPConnection.setUseStreamManagementResumptiodDefault(true)
	XMPPTCPConnection.setUseStreamManagementDefault(true)
	
	
	val xmppConnection = XMPPTCPConnection(config)
	xmppConnection.packetReplyTimeout = 10000000
	xmppConnection.addConnectionListener(XMPPConnectionListener())
	
	xmppConnection.addAsyncStanzaListener(XMPPChatMessageListener(), StanzaTypeFilter(Message::class.java))
	
	ReconnectionManager.getInstanceFor(xmppConnection).enableAutomaticReconnection()
	ServerPingWithAlarmManager.onCreate(CONTEXT)
	
	ServerPingWithAlarmManager.getInstanceFor(xmppConnection).isEnabled = true
	ReconnectionManager.setEnabledPerDefault(true)
	
	ChatManager.getInstanceFor(xmppConnection).addChatListener(XMPPChatManagerListener())
	
	Roster.getInstanceFor(xmppConnection).addRosterListener(RosterListener())
	Roster.getInstanceFor(xmppConnection).subscriptionMode = Roster.SubscriptionMode.accept_all
	
	val pingManager = PingManager.getInstanceFor(xmppConnection)
	pingManager.pingInterval = 20
	pingManager.registerPingFailedListener({
		reconnect()
	})
	
	val manager = ReconnectionManager.getInstanceFor(xmppConnection)
	manager.setFixedDelay(2)
	manager.enableAutomaticReconnection()
	
	
	if (Prefs.contains("username") && Prefs.contains("password")) {
		CONTEXT.doAsync {
			if (!xmppConnection.isConnected) xmppConnection.connect()
			if (!xmppConnection.isAuthenticated) xmppConnection.login()
		}
		
	}
	return xmppConnection
}

fun contactExists(number: String): Boolean {
	val lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number))
	val mPhoneNumberProjection = arrayOf(ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME)
	val cur = CONTEXT.contentResolver.query(lookupUri, mPhoneNumberProjection, null, null, null)
	if (cur.moveToNext()) {
		return true
	}
	return false
}
