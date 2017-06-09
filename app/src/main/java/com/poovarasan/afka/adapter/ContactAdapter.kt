package com.poovarasan.afka.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.balysv.materialripple.MaterialRippleLayout
import com.mikepenz.fastadapter.items.AbstractItem
import com.poovarasan.afka.R
import com.poovarasan.afka.activity.Chat
import com.poovarasan.afka.circularimage.BitmapDrawer
import com.poovarasan.afka.circularimage.CircularDrawable
import com.poovarasan.afka.circularimage.TextDrawer
import com.poovarasan.afka.core.randomColor
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.listeners.onLongClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.startActivity

/**
 * Created by poovarasanv on 5/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 5/5/17 at 5:30 PM
 */

class ContactAdapter() : AbstractItem<ContactAdapter, ContactAdapter.ViewHolder>(),Parcelable {

//	var displayName = ""
//	var nickName = ""
//	var homePhone = ""
//	var mobilePhone = ""
//	var workPhone = ""
//	var photoPath = ""
//	var photo: Bitmap? = null
//	var homeEmail = ""
//	var workEmail = ""
//	var companyName = ""
//	var title = ""
	
	var contactID: Long? = null
	var name: String? = null
	var phoneNumber: Map<String, String>? = null
	var email: Map<String, String>? = null
	var image: Bitmap? = null
	
	constructor(parcel: Parcel) : this() {
		contactID = parcel.readValue(Long::class.java.classLoader) as? Long
		name = parcel.readString()
	}
	
	override fun getLayoutRes(): Int {
		return R.layout.contact_list;
	}
	
	override fun getViewHolder(p0: View?): ViewHolder {
		return ViewHolder(p0)
	}
	
	override fun getType(): Int {
		return R.integer.contactListType
	}
	
	override fun bindView(holder: ViewHolder?, payloads: MutableList<Any>?) {
		super.bindView(holder, payloads)
		holder!!.contactName.text = name
		
		val circleDrawer = CircularDrawable()
		
		if (image != null) {
			val bmpDrawer = BitmapDrawer()
			bmpDrawer.bitmap = image
			bmpDrawer.scaleType = ImageView.ScaleType.CENTER_CROP
			circleDrawer.setBitmapOrTextOrIcon(bmpDrawer)
			holder.contactImage.setImageDrawable(circleDrawer)
			
		} else {
			
			val txtDrawer = TextDrawer()
			txtDrawer.text = if (name!!.isNotEmpty()) name!![0].toString() else "#"
			txtDrawer.backgroundColor = randomColor()
			txtDrawer.textColor = Color.WHITE
			circleDrawer.setBitmapOrTextOrIcon(txtDrawer)
			holder.contactImage.setImageDrawable(circleDrawer)
		}
		
		holder.contactAdapterRipple.onLongClick {
			val selectorList = ArrayList<String>()
			selectorList.add("View ${name}");
			selectorList.add("Message ${name}");
			selectorList.add("Call ${name}");
			selectorList.add("Invite ${name}");
			holder.contactImage.context.selector(Appcompat, "Do : ${name}", selectorList) { dialogInterface, charSequence, i ->
				when (i) {
					1    ->  {
						val intent1 = Intent(holder.contactImage.context,Chat::class.java)
						intent1.putExtra("userinfo",this)
						holder.contactImage.context.startActivity(intent1)
					}
					else -> holder.contactImage.context.startActivity<Chat>()
				}
			}
			
			true
		}
	}
	
	class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
		val contactImage = view!!.find<ImageView>(R.id.contactAdapterImage)
		val contactName = view!!.find<TextView>(R.id.contactAdapterName)
		val contactAdapterRipple = view!!.find<MaterialRippleLayout>(R.id.contactAdapterRipple)
	}
	
	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(contactID)
		parcel.writeString(name)
	}
	
	override fun describeContents(): Int {
		return 0
	}
	
	companion object CREATOR : Parcelable.Creator<ContactAdapter> {
		override fun createFromParcel(parcel: Parcel): ContactAdapter {
			return ContactAdapter(parcel)
		}
		
		override fun newArray(size: Int): Array<ContactAdapter?> {
			return arrayOfNulls(size)
		}
	}
	
}