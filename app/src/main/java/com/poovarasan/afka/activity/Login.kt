package com.poovarasan.afka.activity

import android.os.Bundle
import com.poovarasan.afka.base.BaseActivity
import com.poovarasan.afka.ui.LoginUI
import org.jetbrains.anko.setContentView

/**
 * Created by poovarasanv on 4/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 8:57 AM
 */

class Login : BaseActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		LoginUI().setContentView(this)
	}
	
	
}
