package se.umu.yarn.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")

public class UserModel {

    @PrimaryKey(autoGenerate = true)
    private int key;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "interests")
    private String interests;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
