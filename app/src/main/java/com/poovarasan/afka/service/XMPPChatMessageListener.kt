package com.poovarasan.afka.service

import android.content.Intent
import android.util.Log
import com.poovarasan.afka.core.CONTEXT
import com.poovarasan.afka.core.contactExists
import com.poovarasan.afka.database.db
import com.poovarasan.afka.model.Messages
import com.poovarasan.afka.model.User
import org.jetbrains.anko.doAsync
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Stanza

/**
 * Created by poovarasanv on 25/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 25/5/17 at 2:54 PM
 */

class XMPPChatMessageListener : StanzaListener {
	override fun processStanza(packet: Stanza?) {
		
		if (packet is Message) {
			val message = packet as Message
			
			doAsync {
				val user = CONTEXT.db.userDao().getUser(message.from.toString().split("@")[0])
				if (user == null) {
					val inUser = User()
					inUser.JID = message.from.toString()
					inUser.createdDate = System.currentTimeMillis()
					inUser.isInContactList = contactExists(message.from.toString().split("@")[0])
					CONTEXT.db.userDao().insertUser(inUser)
					Log.i("DATABASE","User Inserted")
				}
				
				val user1 = CONTEXT.db.userDao().getUser(message.from.toString().split("@")[0])
				val dbMessage = Messages()
				dbMessage.from = user1!!.userId
				dbMessage.content = message.body
				
				CONTEXT.db.messageDao().insertMessage(dbMessage)
				
				Log.i("DATABASE","User Inserted")
			}
			val intent: Intent = Intent("com.poovarasan.afka.MESSAGE_RECEIVED")
			intent.putExtra("message", message.body)
			CONTEXT.sendBroadcast(intent)
		}
		
	}
	
	
}
