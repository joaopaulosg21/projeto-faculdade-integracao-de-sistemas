package projeto.faculdade.dadosClimaticos.controller.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosMunicipiosResponse(String uf, Integer quantidade_retornada,
                                      List<MunicipioResponse> cidades,
                                      LocalDateTime consultado_em) {
    @Builder
    public record MunicipioResponse(String nome){}
}
