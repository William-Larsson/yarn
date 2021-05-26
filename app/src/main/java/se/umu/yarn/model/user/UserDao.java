package se.umu.yarn.model.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);

    @Query("DELETE FROM Tbl_User")
    void deleteAllUsers();

    @Query("select * from Tbl_User")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM Tbl_User ORDER BY name ASC")
    LiveData<List<UserEntity>> getAlphabetUsers();

}

