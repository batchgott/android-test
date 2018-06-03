package com.evelope.events.apiModel;


import java.io.Serializable;


public class SGroupUserID implements Serializable{


    public SGroupUserID(SUser u, SGroup g)
    {
        user = u;
        group = g;
    }

    public SUser user;

    public SGroup group;

}
