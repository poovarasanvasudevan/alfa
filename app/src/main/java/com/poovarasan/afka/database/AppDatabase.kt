package com.poovarasan.afka.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.poovarasan.afka.dao.MessageDAO
import com.poovarasan.afka.dao.UserDAO
import com.poovarasan.afka.model.Messages
import com.poovarasan.afka.model.User


/**
 * Created by poovarasanv on 19/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 3:01 PM
 */

@Database(entities = arrayOf(Messages::class, User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun messageDao(): MessageDAO
	abstract fun userDao(): UserDAO
	
	
	companion object {
		private var INSTANCE: AppDatabase? = null
		@JvmStatic fun getDbBuilder(context: Context): AppDatabase {
			if (INSTANCE == null) {
				INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "ALFS_DB").allowMainThreadQueries().build()
			}
			return INSTANCE!!
		}
		
		@JvmStatic fun destroyInstance() {
			INSTANCE = null
		}
	}
}


val Context.db: AppDatabase
	get() = AppDatabase.getDbBuilder(this)

