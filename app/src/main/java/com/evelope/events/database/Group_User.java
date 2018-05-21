package com.evelope.events.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "u_id",childColumns = "u_id",onDelete = CASCADE),
                        @ForeignKey(entity = Group.class,parentColumns = "g_id",childColumns = "g_id",onDelete = CASCADE)},
        primaryKeys = {"u_id","g_id"},
        indices = {@Index(value = {"g_id"}),@Index(value = "u_id")})
public class Group_User {

    //region Getter und Setter
    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public Long getG_id() {
        return g_id;
    }

    public void setG_id(Long g_id) {
        this.g_id = g_id;
    }
    //endregion

    @NonNull
    @ColumnInfo(name = "u_id")
    private Long u_id;

    @NonNull
    @ColumnInfo(name = "g_id")
    private Long g_id;
}
