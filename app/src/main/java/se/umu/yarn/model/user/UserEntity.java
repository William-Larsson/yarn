package se.umu.yarn.model.user;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Tbl_User")

public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int key;
    String name;
    String interests;

    public UserEntity(){ }

    public UserEntity(String name, String interests) {
        this.name = name;
        this.interests = interests;
    }

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


