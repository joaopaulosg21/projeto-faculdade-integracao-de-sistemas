package projeto.faculdade.dadosClimaticos.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.faculdade.dadosClimaticos.client.DadosCidadesFeignClient;
import projeto.faculdade.dadosClimaticos.controller.contract.DadosClimaticosResponse;
import projeto.faculdade.dadosClimaticos.domain.Cidade;
import projeto.faculdade.dadosClimaticos.domain.DadosClimaticos;
import projeto.faculdade.dadosClimaticos.domain.exception.CidadeNotFoundException;
import projeto.faculdade.dadosClimaticos.domain.exception.NomeCidadeInvalidException;
import projeto.faculdade.dadosClimaticos.domain.exception.ServicoIndisponivelException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DadosClimaticosService {

    private final DadosCidadesFeignClient client;

    private Cidade getCidadeByName(String cidade) {
        if(cidade.length() < 2) {
            throw new NomeCidadeInvalidException(cidade);
        }
        try {
            return client.getDadosCidade(cidade)
                    .stream().filter(c -> c.nome().equalsIgnoreCase(cidade))
                    .toList().getFirst();
        }catch (NoSuchElementException | FeignException.FeignClientException.NotFound exc) {
            throw new CidadeNotFoundException(cidade);
        }catch (FeignException exc) {
            throw new ServicoIndisponivelException("CPTEC");
        }
    }

    public DadosClimaticos getDadosClimaticos(Integer id) {
        try {
            return client.getDadosClimaticos(id);
        }catch (FeignException exc) {
            throw new ServicoIndisponivelException("CPTEC");
        }

    }

    public DadosClimaticosResponse execute(String nomeCidade) {
        Cidade cidade = getCidadeByName(nomeCidade);
        DadosClimaticos dadosClimaticos = getDadosClimaticos(cidade.id());
        DadosClimaticosResponse.ClimaResponse climaResponse = DadosClimaticosResponse.ClimaResponse.builder()
                .temperatura_min(dadosClimaticos.clima().getFirst().min())
                .temperatura_max(dadosClimaticos.clima().getFirst().max())
                .condicao(dadosClimaticos.clima().getFirst().condicao_desc())
                .unidades(List.of(new DadosClimaticosResponse.Unidade("C")))
                .build();
        return  DadosClimaticosResponse.builder()
                .nome(cidade.nome())
                .estado(cidade.estado())
                .clima(climaResponse)
                .consultado_em(LocalDateTime.now())
                .build();
    }
}
