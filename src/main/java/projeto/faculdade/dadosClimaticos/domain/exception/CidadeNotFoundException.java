package projeto.faculdade.dadosClimaticos.domain.exception;

public class CidadeNotFoundException extends RuntimeException{

    public CidadeNotFoundException(String nome) {
        super(nome);
    }
}
