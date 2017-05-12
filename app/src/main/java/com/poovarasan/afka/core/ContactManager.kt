package com.poovarasan.afka.core

import android.content.ContentProviderOperation
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import android.provider.SyncStateContract
import com.poovarasan.afka.adapter.ContactAdapter


/**
 * Created by poovarasanv on 11/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 11/5/17 at 12:48 PM
 */

class ContactManager(val context: Context) {
	private val MIMETYPE = "vnd.android.cursor.item/${context.applicationInfo.packageName}"
	
	fun addContact(contact: ContactAdapter) {
		val resolver = context.contentResolver
		val ops = ArrayList<ContentProviderOperation>()
		
		ops.add(ContentProviderOperation
			.newInsert(addCallerIsSyncAdapterParameter(
				ContactsContract.RawContacts.CONTENT_URI, true))
			.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
				SyncStateContract.Constants.ACCOUNT_NAME)
			.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
				SyncStateContract.Constants.ACCOUNT_TYPE)
			.withValue(ContactsContract.RawContacts.AGGREGATION_MODE,
				ContactsContract.RawContacts.AGGREGATION_MODE_DEFAULT)
			.build())
		
		ops.add(ContentProviderOperation
			.newInsert(addCallerIsSyncAdapterParameter(
				ContactsContract.Data.CONTENT_URI, true))
			.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			.withValue(ContactsContract.Data.MIMETYPE,
				ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
			.withValue(CommonDataKinds.StructuredName.DISPLAY_NAME,
				contact.name)
			.build())
		
		ops.add(ContentProviderOperation
			.newInsert(addCallerIsSyncAdapterParameter(
				ContactsContract.Data.CONTENT_URI, true))
			.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
			.withValue(ContactsContract.Data.MIMETYPE, MIMETYPE)
			.withValue(ContactsContract.Data.DATA1, "Alfa")
			.build())
		
		try {
			resolver.applyBatch(ContactsContract.AUTHORITY, ops)
		} catch (e: Exception) {
			e.printStackTrace()
		}
		
	}
	
	private fun addCallerIsSyncAdapterParameter(uri: Uri,
	                                            isSyncOperation: Boolean): Uri {
		if (isSyncOperation) {
			return uri.buildUpon()
				.appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER,
					"true").build()
		}
		return uri
	}
}
