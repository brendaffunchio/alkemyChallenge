package com.backend.disney.modelsDTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private String image;
    @NotBlank(message = "title cannot be blank")
    private String title;
    private Date creation_date;
}
