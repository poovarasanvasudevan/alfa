package com.poovarasan.afka.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.os.Message;

import java.util.List;

/**
 * Created by poovarasanv on 19/5/17.
 *
 * @author poovarasanv
 * @project Afka
 * @on 19/5/17 at 2:51 PM
 */

@Dao
public interface MessageDAO {

    @Query("select * from messages")
    public List<Message> getAllMessages();
}
