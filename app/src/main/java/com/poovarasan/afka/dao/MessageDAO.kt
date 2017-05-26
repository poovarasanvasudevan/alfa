package com.poovarasan.afka.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.poovarasan.afka.model.Messages

/**
 * Created by poovarasanv on 19/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 19/5/17 at 2:51 PM
 */

@Dao
interface MessageDAO {
	
	@Query("select * from messages")
	fun getAllMessages(): List<Messages>
	
	
	@Insert
	fun insertMessage(message: Messages)
	
}
