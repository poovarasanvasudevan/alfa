package com.poovarasan.afka.activity

//import carbon.widget.ViewPager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.poovarasan.afka.R
import com.poovarasan.afka.adapter.TabAdapter
import com.poovarasan.afka.base.BaseActivity
import com.poovarasan.afka.core.getIcon
import com.poovarasan.afka.ui.HomeUI
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by poovarasanv on 4/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 4/5/17 at 2:58 PM
 */

class Home : BaseActivity(), TabLayout.OnTabSelectedListener {
	override fun onTabReselected(tab: TabLayout.Tab?) {
		
	}
	
	override fun onTabUnselected(tab: TabLayout.Tab?) {
		
	}
	
	override fun onTabSelected(tab: TabLayout.Tab?) {
		viewPager.currentItem = tab!!.position
	}
	
	protected override fun attachBaseContext(newBase: Context) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
	}
	
	lateinit var tabLayout: TabLayout
	lateinit var viewPager: ViewPager
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		HomeUI().setContentView(this)
				
//		val chatManager = ChatManager.getInstanceFor(xmppConnect()).addChatListener { chat, createdLocally ->
//			run {
//				chat.addMessageListener { chat, message ->
//					this@Home.runOnUiThread {
//						toast(message.body + message.type.name)
//					}
//				}
//			}
//		}
		
		tabLayout = find(R.id.tabLayout)
		viewPager = find(R.id.viewPager)
		
		val icon = arrayOf<Drawable>(
			getIcon(icon = GoogleMaterial.Icon.gmd_chat_bubble, color = Color.WHITE, size = 20),
			getIcon(icon = GoogleMaterial.Icon.gmd_call, color = Color.WHITE, size = 20),
			getIcon(icon = GoogleMaterial.Icon.gmd_contacts, color = Color.WHITE, size = 20),
			getIcon(icon = GoogleMaterial.Icon.gmd_group, color = Color.WHITE, size = 20),
			getIcon(icon = GoogleMaterial.Icon.gmd_account_circle, color = Color.WHITE, size = 20)
		)
		
		tabLayout.addTab(tabLayout.newTab())
		tabLayout.addTab(tabLayout.newTab())
		tabLayout.addTab(tabLayout.newTab())
		tabLayout.addTab(tabLayout.newTab())
		tabLayout.addTab(tabLayout.newTab())
		tabLayout.tabGravity = TabLayout.GRAVITY_FILL
		
		
		
		val pageAdapter: TabAdapter = TabAdapter(supportFragmentManager, tabLayout.tabCount)
		viewPager.adapter = pageAdapter
		tabLayout.setOnTabSelectedListener(this)
		tabLayout.setupWithViewPager(viewPager)
		viewPager.offscreenPageLimit = tabLayout.tabCount - 1
		for (i in 0..tabLayout.tabCount - 1) {
			tabLayout.getTabAt(i)!!.icon = icon[i]
		}
	}
	
	
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		val fragments = supportFragmentManager.fragments
		if (fragments != null) {
			for (fragment in fragments) {
				fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
			}
		}
	}
}
