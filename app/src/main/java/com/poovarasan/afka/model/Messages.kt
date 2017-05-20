package com.poovarasan.afka.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by poovarasanv on 19/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 19/5/17 at 2:49 PM
 */

@Entity
class Messages {

    @PrimaryKey(autoGenerate = true)
    var messageId: Int = 0

    @ColumnInfo(name = "from_user")
    var from: String? = null

    @ColumnInfo(name = "to_user")
    var to: String? = null

    @ColumnInfo(name = "content_info")
    var content: String? = null
}
