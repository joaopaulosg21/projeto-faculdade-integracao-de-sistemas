package projeto.faculdade.dadosClimaticos.controller.contract;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DadosClimaticosResponse(String nome, String estado,
                                      ClimaResponse clima, LocalDateTime consultado_em) {
    @Builder
    public record ClimaResponse(Integer temperatura_min, Integer temperatura_max,
                                String condicao, List<Unidade> unidades) {

    }
    @Builder
    public record Unidade(String temperatura) {}
}
