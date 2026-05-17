package projeto.faculdade.dadosClimaticos.controller.contract;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DadosMunicipiosResponse(String uf, Integer quantidade_retornada,
                                      List<MunicipioResponse> cidades,
                                      LocalDateTime consultado_em) {
    @Builder
    public record MunicipioResponse(String nome){}
}
