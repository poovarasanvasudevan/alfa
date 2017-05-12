package com.poovarasan.afka.fragement

import android.Manifest
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.poovarasan.afka.R
import com.poovarasan.afka.adapter.ContactAdapter
import com.poovarasan.afka.core.processWithPermission
import com.poovarasan.afka.core.xmppConnect
import com.poovarasan.afka.ui.ContactUI
import com.poovarasan.afka.widget.material.ButtonRectangle
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.listeners.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import org.jivesoftware.smackx.iqregister.AccountManager
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import java.util.*


/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:11 PM
 */

class ContactFragment : Fragment() {
	
	var contactAccessBtnLayout: LinearLayout? = null
	var contactListFillLayout: RelativeLayout? = null
	var contactList: RecyclerView? = null
	var contactAdapter: FastItemAdapter<ContactAdapter>? = null
	val CONTACT_REQ_CODE = 1012
	
	
	val CONTACT_PERMISSION_CALLBACK = object : PermissionCallback {
		override fun permissionGranted() {
			accessContacts(initial = true)
			accessContacts(initial = false)
		}
		
		override fun permissionRefused() {
			toast("Unable to Read Contact : Permission denied")
		}
	}
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	fun getPhoto(contactId: Long): Bitmap? {
		val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(activity.contentResolver,
			ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId))
		var photo: Bitmap? = null
		if (inputStream != null) {
			photo = BitmapFactory.decodeStream(inputStream)
		}
		
		return photo
	}
	
	
	fun accessContacts(initial: Boolean = false): Unit {
		
		if (Nammu.checkPermission(Manifest.permission.READ_CONTACTS)) {
			
			activity.processWithPermission({
				contactAccessBtnLayout!!.visibility = View.GONE
				contactListFillLayout!!.visibility = View.VISIBLE
				doAsync {
					val accountManager = AccountManager.getInstance(xmppConnect())
					accountManager.sensitiveOperationOverInsecureConnection(true)
					val contactListAdapter = ArrayList<ContactAdapter>()
					val contactsUri = ContactsContract.Contacts.CONTENT_URI
					var condition = "";
					if (initial) {
						condition = " ASC LIMIT 0,100"
					} else {
						condition = " ASC LIMIT -1 OFFSET 100"
					}
					
					val contactsCursor = activity.contentResolver.query(contactsUri, null, null, null,
						ContactsContract.Contacts.DISPLAY_NAME + condition)
					if (contactsCursor.moveToFirst()) {
						while (contactsCursor.moveToNext()) {
							val contactId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"))
							val dataUri = ContactsContract.Data.CONTENT_URI
							
							
							val dataCursor = activity.contentResolver.query(dataUri, null,
								ContactsContract.Data.CONTACT_ID + "=" + contactId, null, null)
							
							var displayName = ""
							var homePhone = ""
							var mobilePhone = ""
							var workPhone = ""
							var photo: Bitmap? = null
							
							
							if (dataCursor.moveToFirst()) {
								// Getting Display Name
								displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
								while (dataCursor.moveToNext()) {
									
									// Getting Phone numbers
									if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")) == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE) {
										when (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
											ContactsContract.CommonDataKinds.Phone.TYPE_HOME   -> homePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"))
											ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> mobilePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"))
											ContactsContract.CommonDataKinds.Phone.TYPE_WORK   -> workPhone = dataCursor.getString(dataCursor.getColumnIndex("data1"))
										}
									}
								}
								
								
								
								photo = getPhoto(contactId)
								
								
								val contactListAdapterItem = ContactAdapter()
								contactListAdapterItem.name = displayName
								contactListAdapterItem.contactID = contactId
								contactListAdapterItem.image = photo
								contactListAdapterItem.phoneNumber = mutableMapOf("Home" to homePhone, "Mobile" to mobilePhone, "Work" to workPhone)
								//contactManager.addContact(contactListAdapterItem)
								contactListAdapter.add(contactListAdapterItem)
							}
							dataCursor.close()
						}
						
						contactsCursor.close()
					}
					
					uiThread {
						contactAdapter!!.add(contactListAdapter)
					}
				}
				
			}, Manifest.permission.READ_CONTACTS, CONTACT_PERMISSION_CALLBACK)
			
		} else {
			contactListFillLayout!!.visibility = View.GONE
		}
		
	}
	
	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = ContactUI<android.support.v4.app.Fragment>().createView(AnkoContext.create(ctx, this))
		contactAccessBtnLayout = view.find<LinearLayout>(R.id.syncContactLayout)
		contactListFillLayout = view.find<RelativeLayout>(R.id.contactListFillLayout)
		contactList = view.find<RecyclerView>(R.id.contactList)
		contactListFillLayout!!.visibility = View.GONE
		
		val llm = LinearLayoutManager(activity)
		llm.orientation = LinearLayoutManager.VERTICAL
		contactList!!.layoutManager = llm
		
		contactAdapter = FastItemAdapter()
		contactList!!.adapter = contactAdapter
		
		val accessContactButton = view.find<ButtonRectangle>(R.id.accessContactButton)
		accessContactButton.onClick {
			Nammu.askForPermission(activity, Manifest.permission.READ_CONTACTS, CONTACT_PERMISSION_CALLBACK)
		}
		
		accessContacts(initial = true)
		accessContacts(initial = false)
		return view
	}
	
	
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}
}
