package projeto.faculdade.dadosClimaticos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projeto.faculdade.dadosClimaticos.domain.Cidade;
import projeto.faculdade.dadosClimaticos.domain.DadosClimaticos;

import java.util.List;

@FeignClient(name = "dadosClimaticosAPI", url = "${api.base}")
public interface DadosClimaticosClient {

    @GetMapping("/cidade/{cidade}")
    List<Cidade> getDadosCidade(@PathVariable String cidade);

    @GetMapping("/clima/previsao/{cityCode}")
    DadosClimaticos getDadosClimaticos(@PathVariable Integer cityCode);
}
