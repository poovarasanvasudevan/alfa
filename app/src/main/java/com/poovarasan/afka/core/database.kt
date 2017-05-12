package com.poovarasan.afka.core

import android.content.Context
import com.poovarasan.afka.database.*
import io.requery.android.database.sqlite.SQLiteDatabase

/**
 * Created by poovarasanv on 9/5/17.
 * @author poovarasanv
 * @project Afka
 * @on 9/5/17 at 11:44 AM
 */


class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "alfa", null, 1) {
	companion object {
		private var instance: MyDatabaseOpenHelper? = null
		
		@Synchronized
		fun getInstance(ctx: Context): MyDatabaseOpenHelper {
			if (instance == null) {
				instance = MyDatabaseOpenHelper(ctx.applicationContext)
			}
			return instance!!
		}
	}
	
	override fun onCreate(db: SQLiteDatabase) {
		// Here you create tables
		db.createTable(
			"User",
			true,
			"_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
			"contactId" to INTEGER,
			"name" to TEXT,
			"createdDate" to TEXT
		)
		
		db.createTable(
			"MessageStorage",
			true,
			"_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
			"filePath" to TEXT,
			"createdDate" to TEXT
		)
		
		
		db.createTable(
			"Messages",
			true,
			"_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
			"fromUser" to INTEGER,
			"messageStorage" to INTEGER,
			"createdDate" to TEXT,
			FOREIGN_KEY("fromUser", "User", "_id"),
			FOREIGN_KEY("messageStorage", "MessageStorage", "_id")
		)
		
		
		db.createTable(
			"Status",
			true,
			"_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
			"statusText" to TEXT,
			"active" to INTEGER,
			"createdDate" to TEXT
		)
	}
	
	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		
	}
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
	get() = MyDatabaseOpenHelper.getInstance(this)