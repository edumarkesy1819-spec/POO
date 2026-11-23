package view;

import model.Participante;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import controller.PlanejamentoController;
import model.PlanejamentoException;

public class PainelParticipantes extends JPanel {
    private PlanejamentoController controller;

    private JTextField nomeField;
    private JTextField codigoField;
    private JTextField orcamentoField;
    private JTextArea listaParticipantesArea;

    public PainelParticipantes(PlanejamentoController controller) {
        this.controller = controller;
        this.codigoField = new JTextField(10);
        this.nomeField = new JTextField(20);
        this.orcamentoField = new JTextField(10);
        this.listaParticipantesArea = new JTextArea(10, 30);
        this.listaParticipantesArea.setEditable(false);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        JPanel camposPanel = new JPanel(new FlowLayout());
        JLabel codigoLabel = new JLabel("Código:");
        camposPanel.add(codigoLabel);
        camposPanel.add(codigoField);
        JLabel nomeLabel = new JLabel("Nome:");
        camposPanel.add(nomeLabel);
        camposPanel.add(nomeField);
        JLabel orcamentoLabel = new JLabel("Orçamento:");
        camposPanel.add(orcamentoLabel);
        camposPanel.add(orcamentoField);
        JScrollPane scrollParticipantes = new JScrollPane(listaParticipantesArea);

        JPanel botoesPanel = new JPanel(new FlowLayout());
        JButton adicionarButton = new JButton("Cadastrar Participante");
        adicionarButton.addActionListener(e -> cadastrarParticipante());
        botoesPanel.add(adicionarButton);
        JButton alterarButton = new JButton("Alterar Participante");
        alterarButton.addActionListener(e -> alterarParticipante());
        botoesPanel.add(alterarButton);

        JPanel formularioPanel = new JPanel(new GridLayout(2, 1));
        formularioPanel.add(camposPanel);
        formularioPanel.add(botoesPanel);

        add(formularioPanel, BorderLayout.NORTH);
        add(scrollParticipantes, BorderLayout.CENTER);
    }

    public void atualizarListaParticipantes() {
        StringBuilder sb = new StringBuilder();
        for (Participante p : controller.listarParticipantes()) {
            sb.append(p.getCodigo()).append(" - ").append(p.getNome()).append(" - ")
                    .append(String.format("R$ %d,%02d", p.getOrcamento() / 100, p.getOrcamento() % 100)).append("\n");
        }
        listaParticipantesArea.setText(sb.toString());
    }

    private void cadastrarParticipante() {
        String codigoTexto = codigoField.getText();
        String nome = nomeField.getText();
        String orcamentoTexto = orcamentoField.getText();

        try {
            controller.cadastrarParticipante(codigoTexto, nome, orcamentoTexto);
            atualizarListaParticipantes();
            limparCampos();
        } catch (PlanejamentoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarParticipante() {
        String codigoTexto = codigoField.getText();
        String novoNome = nomeField.getText();
        String novoOrcamentoTexto = orcamentoField.getText();

        try {
            controller.alterarParticipante(codigoTexto, novoNome, novoOrcamentoTexto);
            atualizarListaParticipantes();
            limparCampos();
        } catch (PlanejamentoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        codigoField.setText("");
        nomeField.setText("");
        orcamentoField.setText("");
    }

}