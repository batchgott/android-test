package com.evelope.events.database.write.createEntity;

import android.content.Context;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Group_User;
import com.evelope.events.database.write.AWrite;

public class CreateEntityGroup_User extends AWrite {

    private Long groupID;
    private Long userID;

    public CreateEntityGroup_User(Long groupID, Long userID, Context context) {
        this.groupID = groupID;
        this.userID = userID;
        this.context=context;

        db= AppDatabase.getAppDatabase(context);

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        Group_User group_user=new Group_User();
        group_user.setG_id(groupID);
        group_user.setU_id(userID);

        db.group_userDao().insertGroup_User(group_user);
    }
}
