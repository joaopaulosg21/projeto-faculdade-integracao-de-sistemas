package projeto.faculdade.dadosClimaticos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.faculdade.dadosClimaticos.client.DadosCidadesFeignClient;
import projeto.faculdade.dadosClimaticos.controller.contract.HealthResponse;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HealthService {
    private final DadosCidadesFeignClient client;

    public HealthResponse execute() {
        try {
            return HealthResponse.builder()
                    .status("healthy")
                    .versao("1.0.0")
                    .timestamp(LocalDateTime.now())
                    .build();
        }catch (Exception exc) {
            return HealthResponse.builder()
                    .status("degraded")
                    .versao("1.0.0")
                    .timestamp(LocalDateTime.now())
                    .motivo("Serviço externo indisponível")
                    .build();
        }
    }
}
