package com.poovarasan.afka.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.poovarasan.afka.model.User

/**
 * Created by poovarasanv on 26/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 26/5/17 at 11:40 AM
 */

@Dao
interface UserDAO {
	
	@Query("select * from user where userId = :p0 limit 1")
	fun getUser(id: Int): User?
	
	@Query("select * from user where jid = :p0 limit 1")
	fun getUser(jid: String): User?
	
	@Insert
	fun insertUser(user: User)
}
