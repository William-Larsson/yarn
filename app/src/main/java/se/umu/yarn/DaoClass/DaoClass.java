package se.umu.yarn.DaoClass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import se.umu.yarn.EntityClass.UserModel;

@Dao
public interface DaoClass {

    @Insert
    void insertAllData(UserModel user);

    @Query("select * from user")
    List<UserModel> getAllData();

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserModel user);


    @Query("SELECT * FROM user ORDER BY name ASC")
    LiveData<List<UserModel>> getAlphabetUsers();




}

