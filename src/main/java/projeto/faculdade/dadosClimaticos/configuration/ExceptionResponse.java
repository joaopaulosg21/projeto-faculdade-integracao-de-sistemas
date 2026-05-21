package projeto.faculdade.dadosClimaticos.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(Boolean erro, String codigo, String mensagem, String nome_informado, String servico,
                                String sigla_uf_informada) {
}
