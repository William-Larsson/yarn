package se.umu.yarn.EntityClass;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "interestList")
public class InterestListModel {

        @Embedded
        public UserModel testModel;

        @Relation(parentColumn = "id", entityColumn = "testModelId", entity = InterestModel.class)
        public List<String> interestList;


    public UserModel getTestModel() {
        return testModel;
    }

    public void setTestModel(UserModel testModel) {
        this.testModel = testModel;
    }

    public List<String> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<String> interestList) {
        this.interestList = interestList;
    }
}


