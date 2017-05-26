package com.poovarasan.afka.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.poovarasan.afka.R
import com.poovarasan.afka.core.isMyServiceRunning
import com.poovarasan.afka.service.XMPPService
import org.jetbrains.anko.find
import org.jetbrains.anko.startService


/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 9:11 AM
 */

open class BaseActivity : AppCompatActivity() {
	
	override fun onStart() {
		super.onStart()
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		LayoutInflaterCompat.setFactory(layoutInflater, IconicsLayoutInflater(delegate))
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onPostCreate(savedInstanceState, persistentState)
		
		setUp()
	}
	
	fun setUp() {
		if (find<Toolbar>(R.id.toolbar) != null) {
			setSupportActionBar(find<Toolbar>(R.id.toolbar))
			
			supportActionBar!!.setDisplayHomeAsUpEnabled(true)
			supportActionBar!!.setDisplayShowHomeEnabled(true)
		}
	}
	
	override fun onDestroy() {
		super.onDestroy()
				
		if (!isMyServiceRunning(XMPPService::class.java)) {
			startService<XMPPService>()
		}
	}
	
	override fun onPause() {
		super.onPause()
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == android.R.id.home) {
			finish()
		}
		
		return super.onOptionsItemSelected(item)
	}
	
	override fun onResume() {
		super.onResume()
	}
	
	
}
