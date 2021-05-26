package se.umu.yarn.model.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tbl_User")

public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private long userId;
    String name;
    String interests;

    public UserEntity(){ }

    public UserEntity(String name, String interests) {
        this.name = name;
        this.interests = interests;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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


