package persistencia;

import model.Planejamento;
import model.PlanejamentoException;

public interface RepositorioPlanejamento {
    void salvar(Planejamento planejamento) throws PlanejamentoException;

    Planejamento carregar() throws PlanejamentoException;

}