package com.poovarasan.afka.core

import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.ViewManager
import android.widget.Button
import com.balysv.materialripple.MaterialRippleLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import com.pnikosis.materialishprogress.ProgressWheel
import com.poovarasan.afka.widget.BadgeTabLayout
import com.poovarasan.afka.widget.CircleImageView
import com.poovarasan.afka.widget.LoaderTextView
import com.poovarasan.afka.widget.material.ButtonRectangle
import org.jetbrains.anko.custom.ankoView

/**
 * Created by poovarasanv on 3/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 3/5/17 at 3:48 PM
 */


fun ViewManager.materialProgressBar(theme: Int = 0) = materialProgressBar(theme) {}
fun ViewManager.materialProgressBar(theme: Int = 0, init: ProgressWheel.() -> Unit) = ankoView({ ProgressWheel(it) }, theme, init)


fun ViewManager.materialButton(theme: Int = 0) = materialButton(theme) {}
fun ViewManager.materialButton(theme: Int = 0, init: Button.() -> Unit) = ankoView({ Button(it) }, theme, init)

fun ViewManager.materialToolbar(theme: Int = 0) = materialToolbar(theme) {}
fun ViewManager.materialToolbar(theme: Int = 0, init: Toolbar.() -> Unit) = ankoView({ Toolbar(it) }, theme, init)

fun ViewManager.materialAppbar(theme: Int = 0) = materialAppbar(theme) {}
fun ViewManager.materialAppbar(theme: Int = 0, init: AppBarLayout.() -> Unit) = ankoView({ AppBarLayout(it) }, theme, init)


fun ViewManager.materialTabLayout(theme: Int = 0) = materialTabLayout(theme) {}
fun ViewManager.materialTabLayout(theme: Int = 0, init: TabLayout.() -> Unit) = ankoView({ BadgeTabLayout(it) }, theme, init)

fun ViewManager.materialViewPager(theme: Int = 0) = materialViewPager(theme) {}
fun ViewManager.materialViewPager(theme: Int = 0, init: ViewPager.() -> Unit) = ankoView({ ViewPager(it) }, theme, init)

fun ViewManager.materialFAB(theme: Int = 0) = materialFAB(theme) {}
fun ViewManager.materialFAB(theme: Int = 0, init: FloatingActionButton.() -> Unit) = ankoView({ FloatingActionButton(it) }, theme, init)

fun ViewManager.materialFABMenu(theme: Int = 0) = materialFABMenu(theme) {}
fun ViewManager.materialFABMenu(theme: Int = 0, init: FloatingActionMenu.() -> Unit) = ankoView({ FloatingActionMenu(it) }, theme, init)

fun ViewManager.materialCircleImageView(theme: Int = 0) = materialCircleImageView(theme) {}
fun ViewManager.materialCircleImageView(theme: Int = 0, init: CircleImageView.() -> Unit) = ankoView({ CircleImageView(it) }, theme, init)


fun ViewManager.ferescoImage(theme: Int = 0) = ferescoImage(theme) {}
fun ViewManager.ferescoImage(theme: Int = 0, init: SimpleDraweeView.() -> Unit) = ankoView({ SimpleDraweeView(it) }, theme, init)


fun ViewManager.btnRectangle(theme: Int = 0) = btnRectangle(theme) {}
fun ViewManager.btnRectangle(theme: Int = 0, init: ButtonRectangle.() -> Unit) = ankoView({ ButtonRectangle(it) }, theme, init)


fun ViewManager.materialRippleLayout(theme: Int = 0) = materialRippleLayout(theme) {}
fun ViewManager.materialRippleLayout(theme: Int = 0, init: MaterialRippleLayout.() -> Unit) = ankoView({ MaterialRippleLayout(it) }, theme, init)

fun ViewManager.materialRecycularview(theme: Int = 0) = materialRecycularview(theme) {}
fun ViewManager.materialRecycularview(theme: Int = 0, init: RecyclerView.() -> Unit) = ankoView({ RecyclerView(it) }, theme, init)


fun ViewManager.loaderText(theme: Int = 0) = loaderText(theme) {}
fun ViewManager.loaderText(theme: Int = 0, init: LoaderTextView.() -> Unit) = ankoView({ LoaderTextView(it) }, theme, init)

