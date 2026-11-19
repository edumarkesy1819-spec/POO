import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.HashSet;

public abstract class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descricao;
    private int valor;
    private List<Participante> participantes;

    public Despesa(String descricao, int valor, List<Participante> participantes) throws PlanejamentoException {
        validarDescricao(descricao);
        validarValor(valor);
        validarParticipantes(participantes);

        this.descricao = descricao;
        this.valor = valor;
        this.participantes = participantes;
    }

    public abstract int calcularParteDe(Participante participante);

    public String getDescricao() {
        return descricao;
    }

    public int getValor() {
        return valor;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    private void validarParticipantes(List<Participante> participantes) throws PlanejamentoException {
        if (participantes == null || participantes.isEmpty()) {
            throw new PlanejamentoException("A lista de participantes não pode ser nula ou vazia.");
        }

        Set<Participante> vistos = new HashSet<>();
        for (Participante p : participantes) {
            if (p == null) {
                throw new PlanejamentoException("A lista de participantes não pode conter elementos nulos.");
            }

            if (!vistos.add(p)) {
                throw new PlanejamentoException(
                        "Participante repetido na despesa " + p.getNome() + " (código " + p.getCodigo() + ")");
            }
        }
    }

    private void validarValor(int valor) throws PlanejamentoException {
        if (valor <= 0) {
            throw new PlanejamentoException("O valor da despesa não pode ser zero ou negativo.");
        }
    }

    private void validarDescricao(String descricao) throws PlanejamentoException {
        if (descricao == null || descricao.isBlank()) {
            throw new PlanejamentoException("A descrição da despesa não pode ser nula ou vazia.");
        }
    }
}