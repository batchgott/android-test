package com.evelope.events.apiModel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

public class SEvent implements Serializable {

    public Long id;

    public String location;

    public String description;

    public Date startDate;

    public Date endDate;

    public String name;

    public SPicturepath picturepath;

    public Integer paritcipants;

    
}
