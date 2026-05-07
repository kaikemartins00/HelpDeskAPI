package com.kaike.br.HelpDesk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalledUpdateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String observation;

}
