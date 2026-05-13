package projeto.faculdade.dadosClimaticos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.faculdade.dadosClimaticos.client.DadosClimaticosClient;
import projeto.faculdade.dadosClimaticos.controller.contract.DadosClimaticosResponse;
import projeto.faculdade.dadosClimaticos.domain.Cidade;
import projeto.faculdade.dadosClimaticos.domain.DadosClimaticos;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DadosClimaticosService {

    private final DadosClimaticosClient client;

    private Cidade getCidadeByName(String cidade) {
        return client.getDadosCidade(cidade)
                .stream().filter(c -> c.nome().equalsIgnoreCase(cidade))
                .toList().getFirst();
    }

    public DadosClimaticosResponse execute(String nomeCidade) {
        Cidade cidade = getCidadeByName(nomeCidade);
        DadosClimaticos dadosClimaticos = client.getDadosClimaticos(cidade.id());
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
