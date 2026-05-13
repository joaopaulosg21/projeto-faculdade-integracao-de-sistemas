package projeto.faculdade.dadosClimaticos.domain;

import java.util.List;

public record DadosClimaticos(String atualizado_em, List<Clima> clima) {


    public record Clima(Integer min, Integer max, String condicao_desc) {

    }
}
