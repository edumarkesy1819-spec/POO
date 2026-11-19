public interface RepositorioPlanejamento {
    void salvar(Planejamento planejamento) throws PlanejamentoException;

    Planejamento carregar() throws PlanejamentoException;

}
