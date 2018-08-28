package com.yaratech.yaratube.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.yaratech.yaratube.data.source.local.entity.User;

@Dao
public interface AppDao {
    @Query("Select user_table.token from user_table where id = 1")
    String getToken();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User users);

    @Query("delete from user_table where id = :id")
    void userDelete(int id);

}