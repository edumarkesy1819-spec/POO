package model;

import java.util.List;

public class DespesaFixa extends Despesa {
    private static final long serialVersionUID = 1L;

    public DespesaFixa(String descricao, int valor, List<Participante> participantes) throws PlanejamentoException {
        super(descricao, valor, participantes);
    }

    @Override
    public int calcularParteDe(Participante participante) {
        if (getParticipantes().contains(participante)) {
            int base = getValor() / getParticipantes().size();
            int resto = getValor() % getParticipantes().size();
            int menores = 0;

            for (Participante outro : getParticipantes()) {
                if (participante.getCodigo() > outro.getCodigo()) {
                    menores++;
                }
            }

            if (menores < resto) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            return 0;
        }
    }
}