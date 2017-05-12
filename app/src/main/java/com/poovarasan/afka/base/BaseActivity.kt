package com.poovarasan.afka.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.poovarasan.afka.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper



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
		setSupportActionBar(findViewById(R.id.toolbar) as Toolbar?)
		supportActionBar?.setDisplayShowTitleEnabled(false)
	}
	
	override fun attachBaseContext(newBase: Context) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
	}
}
