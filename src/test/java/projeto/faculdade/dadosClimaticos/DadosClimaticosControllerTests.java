package projeto.faculdade.dadosClimaticos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projeto.faculdade.dadosClimaticos.controller.DadosController;
import projeto.faculdade.dadosClimaticos.controller.contract.DadosClimaticosResponse;
import projeto.faculdade.dadosClimaticos.domain.exception.CidadeNotFoundException;
import projeto.faculdade.dadosClimaticos.service.DadosClimaticosService;
import projeto.faculdade.dadosClimaticos.service.DadosMunicipiosService;
import projeto.faculdade.dadosClimaticos.service.HealthService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(DadosController.class)
class DadosClimaticosControllerTests {
    @MockitoBean
    private DadosClimaticosService dadosClimaticosService;

    @MockitoBean
    private DadosMunicipiosService dadosMunicipiosService;

    @MockitoBean
    private HealthService healthService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getDadosClimaticos_cenarioSucesso() throws Exception {
        String nomeCidade = "Fortaleza";
        DadosClimaticosResponse response = createDadosClimaticosResponse();

        when(dadosClimaticosService.execute(nomeCidade))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/clima/{nome_cidade}",nomeCidade)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(response.nome()))
                .andExpect(jsonPath("$.estado").value(response.estado()))
                .andExpect(jsonPath("$.clima.temperatura_min").value(response.clima().temperatura_min()))
                .andExpect(jsonPath("$.clima.temperatura_max").value(response.clima().temperatura_max()))
                .andExpect(jsonPath("$.clima.condicao").value(response.clima().condicao()))
                .andExpect(jsonPath("$.clima.unidades[0].temperatura").value(response.clima().unidades().getFirst().temperatura()))
                .andExpect(jsonPath("$.consultado_em").exists());
    }

    @Test
    void getDadosClimatos_cenarioCidadeNotFound() throws Exception {
        String nomeCidade = "notFound";

        when(dadosClimaticosService.execute(nomeCidade)).thenThrow(new CidadeNotFoundException(nomeCidade));

        mockMvc.perform(get("/api/v1/clima/{nome_cidade}",nomeCidade)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value(true))
                .andExpect(jsonPath("$.codigo").value("CIDADE_NAO_ENCONTRADA"))
                .andExpect(jsonPath("$.mensagem").value("Nenhuma cidade encontrada com o nome informado"))
                .andExpect(jsonPath("$.nome_informado").value(nomeCidade));

    }

    private DadosClimaticosResponse createDadosClimaticosResponse() {
        return DadosClimaticosResponse.builder()
                .nome("Fortaleza")
                .estado("CE")
                .clima(DadosClimaticosResponse.ClimaResponse.builder()
                        .temperatura_min(23)
                        .temperatura_max(32)
                        .condicao("Parcialmente Nublado")
                        .unidades(List.of(new DadosClimaticosResponse.Unidade("C")))
                        .build())
                .consultado_em(LocalDateTime.now())
                .build();
    }

}
