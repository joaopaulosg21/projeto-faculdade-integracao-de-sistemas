package projeto.faculdade.dadosClimaticos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projeto.faculdade.dadosClimaticos.domain.Cidade;
import projeto.faculdade.dadosClimaticos.domain.DadosClimaticos;
import projeto.faculdade.dadosClimaticos.domain.Municipio;

import java.util.List;

@FeignClient(name = "dadosClimaticosAPI", url = "${api.base}")
public interface DadosCidadesFeignClient {

    @GetMapping("/cptec/v1/cidade/{cidade}")
    List<Cidade> getDadosCidade(@PathVariable String cidade);

    @GetMapping("/cptec/v1/clima/previsao/{cityCode}")
    DadosClimaticos getDadosClimaticos(@PathVariable Integer cityCode);

    @GetMapping("/ibge/municipios/v1/{siglaUf}")
    List<Municipio> getMunicipios(@PathVariable String siglaUf);
}
