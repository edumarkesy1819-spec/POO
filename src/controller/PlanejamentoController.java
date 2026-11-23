package controller;

import persistencia.RepositorioPlanejamento;
import model.Planejamento;
import model.Despesa;
import model.DespesaPorPessoa;
import model.DespesaFixa;
import model.Participante;
import model.PlanejamentoException;
import java.math.BigDecimal;
import java.util.List;

public class PlanejamentoController {
  public static final String TIPO_FIXA = DespesaFixa.TIPO;
  public static final String TIPO_POR_PESSOA = DespesaPorPessoa.TIPO;

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

  public List<Despesa> listarDespesas() {
    return planejamentoUso.getDespesas();
  }

  public void incluirDespesa(String descricao, String valorTexto, String tipo, List<Participante> participantes)
      throws PlanejamentoException {
    Despesa despesa = criarDespesa(descricao, valorTexto, tipo, participantes);
    planejamentoUso.adicionarDespesa(despesa);
  }

  public void editarDespesa(Despesa despesaAtual, String descricao, String valorTexto, String tipo,
      List<Participante> participantes)
      throws PlanejamentoException {
    Despesa despesaNova = criarDespesa(descricao, valorTexto, tipo, participantes);
    planejamentoUso.editarDespesa(despesaAtual, despesaNova);
  }

  public void removerDespesa(Despesa despesa) {
    planejamentoUso.removerDespesa(despesa);
  }

  private Despesa criarDespesa(String descricao, String valorTexto, String tipo, List<Participante> participantes)
      throws PlanejamentoException {
    int valor = converterParaCentavos(valorTexto);

    if (TIPO_FIXA.equals(tipo)) {
      return new DespesaFixa(descricao, valor, participantes);
    } else if (TIPO_POR_PESSOA.equals(tipo)) {
      return new DespesaPorPessoa(descricao, valor, participantes);
    } else {
      throw new PlanejamentoException("Tipo de despesa inválido.");
    }
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