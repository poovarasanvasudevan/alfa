package com.poovarasan.afka.model

import android.os.Parcel
import android.os.Parcelable
import com.poovarasan.afka.core.createParcel
import java.sql.Timestamp


/**
 * Created by poovarasanv on 9/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 9/5/17 at 9:28 AM
 */

data class User(
	val _id: Int,
	val contactId: Long,
	val name: String,
	val createdDate: String = Timestamp(System.currentTimeMillis()).toString()
) : Parcelable {
	override fun writeToParcel(dest: Parcel?, flags: Int) {
		dest!!.writeInt(_id)
		dest.writeLong(contactId)
		dest.writeString(name)
		dest.writeString(createdDate)
	}
	
	protected constructor(p: Parcel) : this(_id = p.readInt(), contactId = p.readLong(), name = p.readString(), createdDate = p.readString()) {}
	
	companion object {
		// using createParcel
		@JvmField val CREATOR = createParcel { User(it) }
	}
	
	override fun describeContents(): Int = 0
	
}

data class Messages1(
	val _id: Int,
	val fromUser: User,
	val messageStorage: MessageStorage,
	val createdDate: String = Timestamp(System.currentTimeMillis()).toString()
) : Parcelable {
	override fun writeToParcel(dest: Parcel?, flags: Int) {
		
	}
	
	protected constructor(p: Parcel) : this(_id = p.readInt(), fromUser = p.readParcelable<User>(User::class.java.classLoader), messageStorage = p.readParcelable<MessageStorage>(MessageStorage::class.java.classLoader), createdDate = p.readString()) {}
	
	companion object {
		// using createParcel
		@JvmField val CREATOR = createParcel { Messages1(it) }
	}
	
	override fun describeContents(): Int = 0
	
}

data class MessageStorage(
	val _id: Int,
	val filePath: String,
	val createdDate: String = Timestamp(System.currentTimeMillis()).toString()
) : Parcelable {
	override fun writeToParcel(dest: Parcel?, flags: Int) {
		dest!!.writeInt(_id)
		dest.writeString(filePath)
		dest.writeString(createdDate)
	}
	
	protected constructor(p: Parcel) : this(_id = p.readInt(), filePath = p.readString(), createdDate = p.readString()) {}
	
	companion object {
		// using createParcel
		@JvmField val CREATOR = createParcel { MessageStorage(it) }
	}
	
	override fun describeContents(): Int = 0
}

//data class
