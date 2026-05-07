package com.kaike.br.HelpDesk.dto;

import com.kaike.br.HelpDesk.database.model.CalledPriority;
import com.kaike.br.HelpDesk.database.model.CalledStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalledDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private CalledPriority priority;
    private CalledStatus status;
    private Instant openingDate;
    @NotBlank
    private String observation;

}
