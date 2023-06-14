package com.accolite.alertMessenger.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Message {

@Id
@GeneratedValue(generator = "UUID")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
@Column(name = "messageId", updatable = false, nullable = false)
@Type(type = "org.hibernate.type.UUIDCharType")
    private UUID messageId;
    @NotBlank(message = "Please enter the value")
    private String aircraftRegistration;
    @NotBlank(message = "Please enter the value")
    private String flight;
    @NotBlank(message = "Please enter the value")
    private String desk;
    @NotBlank(message = "Please enter the value")
    private String deskCategory;
    @NotBlank(message = "Please enter the value")
    private String escalated;
//    @Column(columnDefinition = "varchar(255) default NO")
//    @ColumnDefault("NO")
    private String acknowledge = "NO";
    @NotBlank(message = "Please enter the value")
    private String acknowledgedBy;
    @NotBlank(message = "Please enter the value")
    private String received;
    @NotBlank(message = "Please enter the value")
    private String priority;
    @Min(0)
    @Max(1)
//    @Column(columnDefinition = "int default 1")
//    @ColumnDefault("0")
    private int isPublished = 0;

}
