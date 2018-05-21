package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "u_id",childColumns = "u_id",onDelete = CASCADE),
        @ForeignKey(entity = Event.class,parentColumns = "e_id",childColumns = "e_id",onDelete = CASCADE)},
        primaryKeys = {"u_id","e_id"},
        indices = {@Index(value = {"e_id"}),@Index(value = "u_id")})
public class User_Event {

    //region Getter und Setter

    @NonNull
    public Long getU_id() {
        return u_id;
    }

    public void setU_id(@NonNull Long u_id) {
        this.u_id = u_id;
    }

    @NonNull
    public Long getE_id() {
        return e_id;
    }

    public void setE_id(@NonNull Long e_id) {
        this.e_id = e_id;
    }

    //endregion

    @NonNull
    @ColumnInfo(name = "u_id")
    private Long u_id;

    @NonNull
    @ColumnInfo(name = "e_id")
    private Long e_id;

}
