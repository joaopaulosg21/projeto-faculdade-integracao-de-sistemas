package projeto.faculdade.dadosClimaticos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.faculdade.dadosClimaticos.service.DadosClimaticosService;

@RestController
@RequestMapping("/api/v1")
public class DadosClimaticosController {
    @Autowired
    private DadosClimaticosService dadosClimaticosService;

    @GetMapping("/clima/{nome_cidade}")
    public ResponseEntity<?> getDadosClimaticos(@PathVariable(name = "nome_cidade") String cidade) {
        return ResponseEntity.ok(dadosClimaticosService.execute(cidade));
    }
}
