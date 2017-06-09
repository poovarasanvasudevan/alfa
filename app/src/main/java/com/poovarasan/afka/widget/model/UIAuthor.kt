package com.poovarasan.afka.widget.model

import com.poovarasan.chatkit.commons.models.IUser

/**
 * Created by poovarasanv on 7/6/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 7/6/17 at 6:05 PM
 */

open class UIAuthor(internal var authorId: String, internal var authorName: String, internal var avatar: String) : IUser {

	override fun getId(): String? {
		return authorId
	}

	override fun getName(): String? {
		return authorName
	}

	override fun getAvatar(): String? {
		return avatar
	}
}
