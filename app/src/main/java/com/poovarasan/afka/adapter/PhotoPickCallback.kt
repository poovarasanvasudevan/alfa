package com.poovarasan.afka.adapter

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem

/**
 * Created by poovarasanv on 20/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 20/5/17 at 1:21 PM
 */

class PhotoPickCallback : ActionMode.Callback {
	override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = true
	
	override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean = true
	
	override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true
	
	override fun onDestroyActionMode(mode: ActionMode?) {
		
	}
	
}
