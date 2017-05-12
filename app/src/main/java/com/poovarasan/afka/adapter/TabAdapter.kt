package com.poovarasan.afka.adapter

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.poovarasan.afka.fragement.*

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:27 PM
 */

class TabAdapter(fm: FragmentManager, val tabCount: Int) : FragmentStatePagerAdapter(fm) {
	override fun getItem(position: Int): Fragment {
		
		when (position) {
			0 -> return MessageFragment()
			1 -> return CallFragment()
			2 -> return ContactFragment()
			3 -> return GroupFragment()
			4 -> return SettingFragment()
		}
		return null!!
	}

	override fun getCount(): Int {
		return tabCount;
	}
	
}
