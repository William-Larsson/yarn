package se.umu.yarn.model.interests;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room database table for the topics that the user can choose
 * as their interests.
 */
@Entity(tableName = "Tbl_Interest")
public class InterestEntity {

    @PrimaryKey(autoGenerate = true)
    private long interestId;
    private long foreignUserId; // Foreign key used in UserInterestEntity
    private String name;
    private String drawableResourceName; // The id of the image used in the listview.
    // Get the drawable resource ID by: application.getResources().getIdentifier("<name>", "drawable", "se.umu.yarn")


    public InterestEntity(String name, String drawableResourceName){
        this.name = name;
        this.drawableResourceName = drawableResourceName;
    }

    public long getInterestId() {
        return interestId;
    }

    public void setInterestId(long interestId) {
        this.interestId = interestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrawableResourceName() {
        return drawableResourceName;
    }

    public void setDrawableResourceName(String drawableResourceName) {
        this.drawableResourceName = drawableResourceName;
    }

    public long getForeignUserId() {
        return foreignUserId;
    }

    public void setForeignUserId(long foreignUserId) {
        this.foreignUserId = foreignUserId;
    }
}


