package com.poovarasan.afka.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by poovarasanv on 26/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 26/5/17 at 11:31 AM
 */

@Entity
class User() : Parcelable {
	
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "userId")
	var userId: Int = 0
	
	@ColumnInfo(name = "jid", index = true)
	var JID: String = ""
	
	@ColumnInfo
	var isInContactList: Boolean = false
	
	@ColumnInfo
	var createdDate: Long = System.currentTimeMillis()
	
	constructor(parcel: Parcel) : this() {
		userId = parcel.readInt()
		JID = parcel.readString()
		isInContactList = if (parcel.readByte() == 0.toByte()) false else true
		createdDate = parcel.readLong()
	}
	
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(userId)
		parcel.writeString(JID)
		parcel.writeByte((if (isInContactList) 1 else 0))
		parcel.writeLong(createdDate)
	}
	
	override fun describeContents(): Int {
		return 0
	}
	
	companion object CREATOR : Parcelable.Creator<User> {
		override fun createFromParcel(parcel: Parcel): User {
			return User(parcel)
		}
		
		override fun newArray(size: Int): Array<User?> {
			return arrayOfNulls(size)
		}
	}
	
	
}
