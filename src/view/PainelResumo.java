package view;

import model.Participante;
import controller.PlanejamentoController;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class PainelResumo extends JPanel {
    private static final long serialVersionUID = 1L;

    private PlanejamentoController controller;
    private JTextArea resumoTextArea;
    private JScrollPane scrollPane;

    public PainelResumo(PlanejamentoController controller) {
        this.controller = controller;
        resumoTextArea = new JTextArea();
        resumoTextArea.setEditable(false);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(resumoTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void atualizarResumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("Resumo do Planejamento:\n\n");
        resumo.append("Participantes:\n");

        for (Participante p : controller.listarParticipantes()) {
            int total = controller.calcularTotalDe(p);
            int excedente = controller.calcularExcedenteDe(p);

            resumo.append(p.getNome()).append(" deve pagar ")
                    .append(String.format("R$ %d,%02d", total / 100, total % 100)).append(" — ");

            if (excedente > 0) {
                resumo.append("excede o orçamento em ")
                        .append(String.format("R$ %d,%02d", excedente / 100, excedente % 100)).append("\n");
            } else {
                resumo.append("dentro do orçamento\n");
            }
        }

        resumo.append("\nCusto total:\n");
        int custoTotal = controller.calcularCustoTotal();
        resumo.append(String.format("R$ %d,%02d", custoTotal / 100, custoTotal % 100));

        resumo.append("\n");
        resumoTextArea.setText(resumo.toString());
    }
}