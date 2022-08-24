package com.backend.disney.modelsDTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private String image;
    @NotBlank(message = "title cannot be blank")
    private String title;
    @NotBlank(message = "creation date cannot be blank")
    private Date creation_date;
}
