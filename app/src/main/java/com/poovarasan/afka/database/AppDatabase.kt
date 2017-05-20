package com.poovarasan.afka.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.poovarasan.afka.dao.MessageDAO
import com.poovarasan.afka.model.Messages


/**
 * Created by poovarasanv on 19/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 3:01 PM
 */

@Database(entities = arrayOf(Messages::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDAO
}


val Context.db: AppDatabase
    get() = Room.databaseBuilder(this, AppDatabase::class.java, "alfs_db_room").build()


