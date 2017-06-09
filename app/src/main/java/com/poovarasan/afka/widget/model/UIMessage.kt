package com.poovarasan.afka.widget.model

import com.poovarasan.chatkit.commons.models.IMessage
import com.poovarasan.chatkit.commons.models.IUser
import com.poovarasan.chatkit.commons.models.MessageContentType
import java.util.*

/**
 * Created by poovarasanv on 7/6/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 7/6/17 at 6:05 PM
 */

open class UIMessage(internal var message: String, internal var iUser: IUser, internal var createdAt: Date, internal var messageId: String,internal var image: String = "") : IMessage , MessageContentType.Image {
	override fun getImageUrl(): String? {
		return if (image.isEmpty()) null else image
	}
	
	override fun getId(): String {
		return messageId
	}

	override fun getText(): String {
		return message
	}

	override fun getUser(): IUser {
		return iUser
	}

	override fun getCreatedAt(): Date {
		return createdAt
	}
}
