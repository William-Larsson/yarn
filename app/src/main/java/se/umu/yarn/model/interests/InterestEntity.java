package se.umu.yarn.model.interests;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room database table for the topics that the user can choose
 * as their interests.
 */
@Entity(tableName = "Tbl_Interest")
public class InterestEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    // The id of the image used in the listview.
    @ColumnInfo(name = "image_resource")
    private String drawableResourceName;
    // Get the drawable resource ID by: application.getResources().getIdentifier("<name>", "drawable", "se.umu.yarn")

    public InterestEntity(String name, String drawableResourceName){
        this.name = name;
        this.drawableResourceName = drawableResourceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}


