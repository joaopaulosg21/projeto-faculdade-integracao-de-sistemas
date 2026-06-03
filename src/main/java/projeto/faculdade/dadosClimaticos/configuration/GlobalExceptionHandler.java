package projeto.faculdade.dadosClimaticos.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projeto.faculdade.dadosClimaticos.domain.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CidadeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> cidadeNotFoundHandler(CidadeNotFoundException exc) {
        return ResponseEntity.status(404)
                .body(new ExceptionResponse(
                        true
                        , "CIDADE_NAO_ENCONTRADA",
                        "Nenhuma cidade encontrada com o nome informado",
                        exc.getMessage(),
                        null,null)
                );
    }

    @ExceptionHandler(NomeCidadeInvalidException.class)
    public ResponseEntity<ExceptionResponse> nomeInvalidoHandler(NomeCidadeInvalidException exc) {
        return ResponseEntity.status(404)
                .body(new ExceptionResponse(
                        true
                        , "NOME_INVALIDO",
                        "O nome da cidade deve conter pelo menos 2 caracteres",
                        exc.getMessage(),
                        null,null)
                );
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<ExceptionResponse> serviceIndisponivelHandler(ServicoIndisponivelException exc) {
        return ResponseEntity.status(404)
                .body(new ExceptionResponse(
                        true
                        , "SERVICO_EXTERNO_INDISPONIVEL",
                        "Não foi possível obter dados do serviço externo. Tente novamente em alguns instantes",
                        null,
                        exc.getMessage(),null)
                );
    }

    @ExceptionHandler(SiglaNaoEncontradaException.class)
    public ResponseEntity<ExceptionResponse> ufNaoEncontradaHandler(SiglaNaoEncontradaException exc) {
        return ResponseEntity.status(404)
                .body(new ExceptionResponse(true,
                        "UF_NAO_ENCONTRADA",
                        "Estado com a sigla informada não foi encontrado",
                        null,
                        null,
                        exc.getMessage()));
    }

    @ExceptionHandler(SiglaInvalidaException.class)
    public ResponseEntity<ExceptionResponse> siglaInvalidaHandler(SiglaInvalidaException exc) {
        return ResponseEntity.status(400)
                .body(new ExceptionResponse(true,
                        "SIGLA_UF_INVALIDA",
                        "A sigla do estado deve conter exatamente 2 letras",
                        null,
                        null,
                        exc.getMessage()));
    }
}
