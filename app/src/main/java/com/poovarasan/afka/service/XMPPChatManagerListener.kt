package com.poovarasan.afka.service

import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManagerListener

/**
 * Created by poovarasanv on 25/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 25/5/17 at 2:58 PM
 */

class XMPPChatManagerListener : ChatManagerListener {
	override fun chatCreated(chat: Chat?, createdLocally: Boolean) {
		
	}
	
}
