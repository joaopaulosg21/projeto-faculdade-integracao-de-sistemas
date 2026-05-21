package projeto.faculdade.dadosClimaticos.controller.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HealthResponse(String status, String versao, LocalDateTime timestamp,
                             String motivo) {
}
