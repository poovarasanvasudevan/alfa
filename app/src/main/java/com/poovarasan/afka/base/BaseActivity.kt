package com.poovarasan.afka.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.poovarasan.afka.R
import org.jetbrains.anko.find


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
		Log.i("Service", "Service Stopped Main")
		val broadcastIntent = Intent("com.poovarasan.afka.RestartService")
		sendBroadcast(broadcastIntent)
	}
	
	override fun onPause() {
		super.onPause()
		
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// handle arrow click here
		if (item.itemId == android.R.id.home) {
			finish() // close this activity and return to preview activity (if there is any)
		}
		
		return super.onOptionsItemSelected(item)
	}
	
	override fun onResume() {
		super.onResume()
		Log.i("Service123", "resume")
	}
	
	
}
