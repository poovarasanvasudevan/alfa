package com.poovarasan.afka.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import com.poovarasan.afka.R
import com.poovarasan.afka.adapter.ContactAdapter
import com.poovarasan.afka.base.BaseActivity
import com.poovarasan.afka.ui.ChatUI
import com.poovarasan.afka.widget.model.UIAuthor
import com.poovarasan.afka.widget.model.UIMessage
import com.poovarasan.chatkit.commons.ImageLoader
import com.poovarasan.chatkit.messages.MessageInput
import com.poovarasan.chatkit.messages.MessagesList
import com.poovarasan.chatkit.messages.MessagesListAdapter
import com.poovarasan.chatkit.utils.DateFormatter
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import java.util.*


/**
 * Created by poovarasanv on 31/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 31/5/17 at 9:45 AM
 */

class Chat : BaseActivity(),
	MessageInput.InputListener,
	MessageInput.AttachmentsListener,
	DateFormatter.Formatter {
	
	override fun format(date: Date?): String {
		if (DateFormatter.isToday(date)) {
			return "Today"
		} else if (DateFormatter.isYesterday(date)) {
			return "Yesterday"
		} else {
			return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
		}
	}
	
	override fun onSubmit(input: CharSequence?): Boolean {
		adapter.addToStart(UIMessage(input.toString().trim(), UIAuthor("123455", "Poosan", "65465"), Date(), "987983"), true)
		return true
	}
	
	override fun onAddAttachments() {
		
	}
	
	lateinit var messageInclude: View
	lateinit var inputLayout: MessageInput
	lateinit var messageLayout: MessagesList
	lateinit var adapter: MessagesListAdapter<UIMessage>
	lateinit var contactInfo: ContactAdapter
	lateinit var toolbar: Toolbar
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		ChatUI().setContentView(this)
		super.setUp()
		
		toolbar = find(R.id.toolbar)
		contactInfo = intent.getParcelableExtra("userinfo")
		
		supportActionBar!!.title = contactInfo.name
		supportActionBar!!.subtitle = "Online"
		
		messageInclude = find(R.id.messageInclude)
		
		inputLayout = messageInclude.find(R.id.input)
		messageLayout = messageInclude.find(R.id.messagesList)
		
		adapter = MessagesListAdapter("123455", ImageLoader { imageView, url -> imageView!!.setImageURI(url) })
		adapter.setDateHeadersFormatter(this)
		messageLayout.setAdapter(adapter)
		
		inputLayout.setInputListener(this@Chat)
		
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.chat_menu, menu)
		return true
	}
	
	
}
