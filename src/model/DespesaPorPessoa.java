package model;

import java.util.List;

public class DespesaPorPessoa extends Despesa {
    private static final long serialVersionUID = 1L;
    public static final String TIPO = "Por Pessoa";

    public DespesaPorPessoa(String descricao, int valor, List<Participante> participantes)
            throws PlanejamentoException {
        super(descricao, valor, participantes);
    }

    @Override
    public String getTipo() {
        return TIPO;
    }

    @Override
    public int calcularParteDe(Participante participante) {
        if (getParticipantes().contains(participante)) {
            return getValor();
        } else {
            return 0;
        }
    }

}