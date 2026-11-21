package controller;

import persistencia.RepositorioPlanejamento;
import model.Planejamento;
import model.Participante;
import model.PlanejamentoException;
import java.math.BigDecimal;
import java.util.List;

public class PlanejamentoController {
  private Planejamento planejamentoUso;
  private RepositorioPlanejamento repositorio;

  public PlanejamentoController(RepositorioPlanejamento repositorio) {
    this.repositorio = repositorio;
    planejamentoUso = new Planejamento();
  }

  public void cadastrarParticipante(String codigoTexto, String nome, String orcamentoTexto)
      throws PlanejamentoException {
    int codigo = converterCodigo(codigoTexto);
    int orcamento = converterParaCentavos(orcamentoTexto);
    planejamentoUso.adicionarParticipante(new Participante(nome, orcamento, codigo));
  }

  public void alterarParticipante(String codigoTexto, String novoNome, String novoOrcamentoTexto)
      throws PlanejamentoException {
    int codigo = converterCodigo(codigoTexto);
    Participante participante = planejamentoUso.buscarParticipante(codigo);
    int novoOrcamento = converterParaCentavos(novoOrcamentoTexto);
    participante.alterarDados(novoNome, novoOrcamento);
  }

  public List<Participante> listarParticipantes() {
    return planejamentoUso.getParticipantes();
  }

  private int converterCodigo(String texto) throws PlanejamentoException {
    if (texto == null || texto.trim().isEmpty()) {
      throw new PlanejamentoException("O código do participante é obrigatório.");
    }
    try {
      return Integer.parseInt(texto.trim());
    } catch (NumberFormatException e) {
      throw new PlanejamentoException("O código do participante deve ser um número inteiro.");
    }
  }

  private int converterParaCentavos(String texto) throws PlanejamentoException {
    if (texto == null || texto.trim().isEmpty()) {
      throw new PlanejamentoException("O valor é obrigatório.");
    }
    try {
      BigDecimal reais = new BigDecimal(texto.trim().replace(",", "."));
      return reais.movePointRight(2).intValueExact();
    } catch (NumberFormatException | ArithmeticException e) {
      throw new PlanejamentoException("Valor inválido: use números com até duas casas decimais, como 70,00.");
    }
  }

}