package projeto.faculdade.dadosClimaticos.domain.exception;

public class SiglaInvalidaException extends RuntimeException{

    public SiglaInvalidaException(String siglaUf) {
        super(siglaUf);
    }
}
