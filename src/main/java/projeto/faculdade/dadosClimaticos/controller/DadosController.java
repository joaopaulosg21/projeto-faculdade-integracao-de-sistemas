package projeto.faculdade.dadosClimaticos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.faculdade.dadosClimaticos.controller.contract.DadosMunicipiosResponse;
import projeto.faculdade.dadosClimaticos.service.DadosMunicipiosService;
import projeto.faculdade.dadosClimaticos.service.DadosClimaticosService;
import projeto.faculdade.dadosClimaticos.service.HealthService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DadosController {
    private final DadosClimaticosService dadosClimaticosService;

    private final DadosMunicipiosService dadosMunicipiosService;

    private final HealthService healthService;

    @GetMapping("/clima/{nome_cidade}")
    public ResponseEntity<?> getDadosClimaticos(@PathVariable(name = "nome_cidade") String cidade) {
        return ResponseEntity.ok(dadosClimaticosService.execute(cidade));
    }

    @GetMapping("/cidades/{sigla_uf}")
    public ResponseEntity<DadosMunicipiosResponse> getCidades(@PathVariable(name = "sigla_uf") String siglaUf, @RequestParam(value = "limite",required = false) Integer limite) {
        return ResponseEntity.ok(dadosMunicipiosService.execute(siglaUf, limite));
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealthStatus() {
        return ResponseEntity.ok(healthService.execute());
    }
}
