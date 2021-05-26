package se.umu.yarn.model.user;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

import se.umu.yarn.model.interests.InterestEntity;
import se.umu.yarn.model.user.UserEntity;

/**
 * Dataclass for defining a one-to-many relationship between
 * UserEntity (one) and InterestEntity (many).
 * TODO: THIS HAS NOT BEEN TESTED YET!
 */
@Entity(tableName = "Tbl_UserInterest")
public class UserInterestEntity {

    // ------------------ Define the relationship ------------------ //
    @Embedded
    public UserEntity userEntity;

    @Relation(
            parentColumn = "userId",
            entityColumn = "foreignUserId"
    )
    public List<InterestEntity> interestList;


    // --------- Functions for getting and setting data etc --------- //


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<InterestEntity> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<InterestEntity> interestList) {
        this.interestList = interestList;
    }
}