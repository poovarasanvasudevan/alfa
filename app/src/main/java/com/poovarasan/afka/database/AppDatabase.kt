package com.poovarasan.afka.database

import android.arch.persistence.room.*
import android.content.Context
import com.poovarasan.afka.dao.MessageDAO
import com.poovarasan.afka.dao.UserDAO
import com.poovarasan.afka.model.Messages
import com.poovarasan.afka.model.User
import java.util.*


/**
 * Created by poovarasanv on 19/5/17.
 
 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 3:01 PM
 */

@Database(entities = arrayOf(Messages::class, User::class), version = 1)
@TypeConverters(DateConverters::class)
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

class DateConverters {
	@TypeConverter
	fun fromTimestamp(value: Long?): Date? {
		return if (value == null) null else Date(value)
	}
	
	@TypeConverter
	fun dateToTimestamp(date: Date?): Long {
		return (if (date == null) null else date.getTime())!!.toLong()
	}
}


val Context.db: AppDatabase
	get() = AppDatabase.getDbBuilder(this)

