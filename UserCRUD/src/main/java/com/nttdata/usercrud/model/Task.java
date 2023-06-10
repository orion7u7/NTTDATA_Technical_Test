package com.nttdata.usercrud.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter @Getter
@NoArgsConstructor
public class Task {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int idUser;
}
