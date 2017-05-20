package com.poovarasan.afka.model

import android.graphics.drawable.Drawable

/**
 * Created by poovarasanv on 5/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 5/5/17 at 2:47 PM
 */


data class ContactModel(
        val contactID: Long,
        val name: String,
        val phoneNumber: List<String>,
        val image: Drawable?
)

class ImageModel(
        /**
         * @return the imageTitle
         */
        /**
         * @param imageTitle the imageTitle to set
         */
        var imageTitle: String?,
        /**
         * @return the imagePath
         */
        /**
         * @param imagePath the imagePath to set
         */
        var imagePath: String?)
