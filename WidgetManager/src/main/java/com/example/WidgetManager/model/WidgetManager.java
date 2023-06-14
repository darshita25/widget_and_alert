package com.example.WidgetManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WidgetManager {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "widgetId", updatable = false, nullable = false)
    private UUID widgetId;
    @NotBlank
    @NotNull(message = "Widget Name is required")
    private String widgetName;
    @NotNull(message = "Tag can not be null")
    private String widgetTag;

    @NotNull
    @Pattern(regexp = "^Enabled|Disabled$", message = "Invalid status field, enter Enabled or Disabled")
    private String widgetStatus;
    @NotNull
    @Pattern(regexp = "^1 x|2 x|3 x|4 x$", message = "invalid size, enter {1 x, 2 x, 3 x,4 x}")
    private String widgetSize;
    private String description;


}
