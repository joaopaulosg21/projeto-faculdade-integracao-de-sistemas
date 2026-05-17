package projeto.faculdade.dadosClimaticos.domain.exception;

public class ServicoIndisponivelException extends RuntimeException{

    public ServicoIndisponivelException(String servico) {
        super(servico);
    }
}
