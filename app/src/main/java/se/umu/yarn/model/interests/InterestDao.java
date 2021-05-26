package se.umu.yarn.model.interests;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * Data access object used for defining abstract interactions with
 * the database that will be automatically generated into concrete
 * implementations by Room.
 */
@Dao
public interface InterestDao {

    @Insert
    long insertInterest(InterestEntity interestEntity);

    @Update
    void updateInterest(InterestEntity interestEntity);

    @Delete
    void deleteInterest(InterestEntity interestEntity);

    @Query("DELETE FROM Tbl_Interest")
    void deleteAllInterests();

    @Query("SELECT * FROM Tbl_Interest")
    LiveData<List<InterestEntity>> getAllInterests();

}
