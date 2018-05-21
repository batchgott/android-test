package com.evelope.events.database.write.createEntity;

import android.content.Context;

import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Categorie_Group;
import com.evelope.events.database.write.AWrite;

public class CreateEntityCategorie_Group extends AWrite {

    private Long categorieID;
    private Long groupID;

    public CreateEntityCategorie_Group(Long categorieID, Long groupID, Context context){
        this.categorieID=categorieID;
        this.groupID=groupID;
        this.context=context;

        db= AppDatabase.getAppDatabase(context);

        alterDatabase();
    }

    @Override
    protected void alterDatabase() {
        Categorie_Group categorie_group=new Categorie_Group();
        categorie_group.setC_id(categorieID);
        categorie_group.setG_id(groupID);

        db.categorie_groupDao().insertCategorie_Group(categorie_group);
    }
}
