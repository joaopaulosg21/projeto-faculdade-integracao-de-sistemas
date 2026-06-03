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
import projeto.faculdade.dadosClimaticos.controller.contract.DadosMunicipiosResponse;
import projeto.faculdade.dadosClimaticos.domain.exception.CidadeNotFoundException;
import projeto.faculdade.dadosClimaticos.domain.exception.SiglaInvalidaException;
import projeto.faculdade.dadosClimaticos.domain.exception.SiglaNaoEncontradaException;
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
public class MunicipiosControllerTests {

    @MockitoBean
    private DadosClimaticosService dadosClimaticosService;

    @MockitoBean
    private DadosMunicipiosService dadosMunicipiosService;

    @MockitoBean
    private HealthService healthService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getCidades_cenarioSucesso() throws Exception {
        DadosMunicipiosResponse response = createDadosMunicipiosResponse();

        when(dadosMunicipiosService.execute("CE",3))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/cidades/CE?limite=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uf").value(response.uf()))
                .andExpect(jsonPath("$.quantidade_retornada").value(response.quantidade_retornada()))
                .andExpect(jsonPath("$.cidades[0].nome").value(response.cidades().getFirst().nome()))
                .andExpect(jsonPath("$.cidades[1].nome").value(response.cidades().get(1).nome()))
                .andExpect(jsonPath("$.cidades[2].nome").value(response.cidades().get(2).nome()))
                .andExpect(jsonPath("$.consultado_em").exists());
    }

    @Test
    void getCidades_cenarioUFNotFound() throws Exception {
        String uf = "XX";

        when(dadosMunicipiosService.execute(uf,3)).thenThrow(new SiglaNaoEncontradaException(uf));

        mockMvc.perform(get("/api/v1/cidades/XX?limite=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value(true))
                .andExpect(jsonPath("$.codigo").value("UF_NAO_ENCONTRADA"))
                .andExpect(jsonPath("$.mensagem").value("Estado com a sigla informada não foi encontrado"))
                .andExpect(jsonPath("$.sigla_uf_informada").value(uf));

    }

    @Test
    void getCidades_cenarioUFInvalida() throws Exception {
        String uf = "Ceara";

        when(dadosMunicipiosService.execute(uf,3)).thenThrow(new SiglaInvalidaException(uf));

        mockMvc.perform(get("/api/v1/cidades/Ceara?limite=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value(true))
                .andExpect(jsonPath("$.codigo").value("SIGLA_UF_INVALIDA"))
                .andExpect(jsonPath("$.mensagem").value("A sigla do estado deve conter exatamente 2 letras"))
                .andExpect(jsonPath("$.sigla_uf_informada").value(uf));

    }

    private DadosMunicipiosResponse createDadosMunicipiosResponse() {
        return DadosMunicipiosResponse.builder()
                .uf("CE")
                .quantidade_retornada(3)
                .cidades(List.of(new DadosMunicipiosResponse.MunicipioResponse("Abaiara"),
                        new DadosMunicipiosResponse.MunicipioResponse("Acarape"),
                        new DadosMunicipiosResponse.MunicipioResponse("Acaraú")))
                .consultado_em(LocalDateTime.now())
                .build();
    }
}
