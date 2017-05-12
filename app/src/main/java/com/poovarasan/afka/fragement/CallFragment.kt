package com.poovarasan.afka.fragement

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.poovarasan.afka.ui.CallUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by poovarasanv on 4/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 4/5/17 at 3:11 PM
 */

class CallFragment : Fragment() {
	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = CallUI<android.support.v4.app.Fragment>().createView(AnkoContext.create(ctx, this))
}
