package com.poovarasan.afka.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by poovarasanv on 19/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 2:49 PM
 */

@Entity(
	foreignKeys = arrayOf<ForeignKey>(
		ForeignKey(entity = User::class, parentColumns = arrayOf("userId"), childColumns = arrayOf("from_user")),
		ForeignKey(entity = User::class, parentColumns = arrayOf("userId"), childColumns = arrayOf("to_user"))
	)
)
class Messages() : Parcelable {
	
	@PrimaryKey(autoGenerate = true)
	var messageId: Int = 0
	
	@ColumnInfo(name = "from_user")
	var from: Int? = null
	
	@ColumnInfo(name = "to_user")
	var to: Int? = null
	
	@ColumnInfo(name = "content_info")
	var content: String? = null
	
	constructor(parcel: Parcel) : this() {
		messageId = parcel.readInt()
		from = parcel.readValue(Int::class.java.classLoader) as? Int
		to = parcel.readValue(Int::class.java.classLoader) as? Int
		content = parcel.readString()
	}
	
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(messageId)
		parcel.writeValue(from)
		parcel.writeValue(to)
		parcel.writeString(content)
	}
	
	override fun describeContents(): Int {
		return 0
	}
	
	companion object CREATOR : Parcelable.Creator<Messages> {
		override fun createFromParcel(parcel: Parcel): Messages {
			return Messages(parcel)
		}
		
		override fun newArray(size: Int): Array<Messages?> {
			return arrayOfNulls(size)
		}
	}
	
	
}


