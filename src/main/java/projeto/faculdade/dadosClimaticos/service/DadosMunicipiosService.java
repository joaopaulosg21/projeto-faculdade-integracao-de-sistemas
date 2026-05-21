package projeto.faculdade.dadosClimaticos.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.faculdade.dadosClimaticos.client.DadosCidadesFeignClient;
import projeto.faculdade.dadosClimaticos.controller.contract.DadosMunicipiosResponse;
import projeto.faculdade.dadosClimaticos.domain.exception.SiglaInvalidaException;
import projeto.faculdade.dadosClimaticos.domain.exception.SiglaNaoEncontradaException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DadosMunicipiosService {

    private final DadosCidadesFeignClient client;

    public DadosMunicipiosResponse execute(String siglaUf, Integer limite)  {
        return DadosMunicipiosResponse.builder()
                .uf(siglaUf.toUpperCase())
                .quantidade_retornada(limite)
                .consultado_em(LocalDateTime.now())
                .cidades(getMunicipios(siglaUf,limite))
                .build();
    }

    public List<DadosMunicipiosResponse.MunicipioResponse> getMunicipios(String siglaUf, Integer limite) {
        if(siglaUf.length() > 2) {
            throw new SiglaInvalidaException(siglaUf);
        }

        try {
            return client.getMunicipios(siglaUf)
                    .stream()
                    .limit(limite)
                    .map(municipio -> new DadosMunicipiosResponse.MunicipioResponse(municipio.nome()))
                    .toList();
        }catch (FeignException.NotFound exc) {
            throw new SiglaNaoEncontradaException(siglaUf);
        }
    }
}
