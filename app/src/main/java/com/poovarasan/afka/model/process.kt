package com.poovarasan.afka.model

/**
 * Created by poovarasanv on 16/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 16/5/17 at 4:23 PM
 */

data class CMessage(
	var id: Int,
	var from: String,
	var message: String
)
