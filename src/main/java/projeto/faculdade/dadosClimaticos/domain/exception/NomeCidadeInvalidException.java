package projeto.faculdade.dadosClimaticos.domain.exception;

public class NomeCidadeInvalidException extends RuntimeException{


    public NomeCidadeInvalidException(String nome) {
        super(nome);
    }
}
