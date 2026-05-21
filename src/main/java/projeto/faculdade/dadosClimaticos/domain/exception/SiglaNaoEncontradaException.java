package projeto.faculdade.dadosClimaticos.domain.exception;

public class SiglaNaoEncontradaException extends RuntimeException{

    public SiglaNaoEncontradaException(String siglaUf) {
        super(siglaUf);
    }
}
