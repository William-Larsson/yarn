package se.umu.yarn.DaoClass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import se.umu.yarn.EntityClass.InterestModel;
import se.umu.yarn.EntityClass.UserModel;

@Dao
public interface DaoClass {

    @Insert
    void insertAllData(InterestModel interestModel);

    @Query("select * from interest")
    List<InterestModel> getAllData();

    @Query("DELETE FROM interest")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(InterestModel interestModel);


    @Query("SELECT * FROM interest ORDER BY name ASC")
    LiveData<List<InterestModel>> getAlphabetUsers();




}

