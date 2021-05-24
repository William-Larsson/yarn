package se.umu.yarn.EntityClass;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "user")

public class UserModel {

    /*@PrimaryKey(autoGenerate = true)
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
    }*/
    String name;
    ArrayList interests;

    public UserModel(){

    }
    public UserModel(String name, ArrayList interests) {
        this.name = name;
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getInterests() {
        return interests;
    }

    public void setInterests(ArrayList interests) {
        this.interests = interests;
    }
}


