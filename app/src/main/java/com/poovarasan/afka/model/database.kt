package com.poovarasan.afka.model

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
	val createdDate: Timestamp = Timestamp(System.currentTimeMillis())
)

data class Messages(
	val _id: Int,
	val fromUser: User,
	val messageStorage: MessageStorage,
	val createdDate: Timestamp = Timestamp(System.currentTimeMillis())
)

data class MessageStorage(
	val _id: Int,
	val filePath: String,
	val createdDate: Timestamp = Timestamp(System.currentTimeMillis())
)

//data class
