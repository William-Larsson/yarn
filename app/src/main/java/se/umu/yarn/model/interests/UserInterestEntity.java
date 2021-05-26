package se.umu.yarn.model.interests;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

import se.umu.yarn.model.user.UserEntity;

@Entity(tableName = "Tbl_UserInterest")
public class UserInterestEntity {

    @Embedded
    public UserEntity userEntity;

    // TODO: rename and cleanup with finished.
    @Relation(parentColumn = "id", entityColumn = "testModelId", entity = InterestEntity.class)
    public List<String> interestList;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<String> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<String> interestList) {
        this.interestList = interestList;
    }
}