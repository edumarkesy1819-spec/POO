package view;

import model.Participante;
import model.Despesa;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import controller.PlanejamentoController;
import model.PlanejamentoException;

public class PainelDespesas extends JPanel {
    private static final long serialVersionUID = 1L;

    private PlanejamentoController controller;
    private JTextField descricaoField;
    private JTextField valorField;
    private JComboBox<String> tipoField;
    private JList<Despesa> listaDespesasList;
    private JList<Participante> listaParticipantesList;

    public void atualizarListas() {
        // Atualiza a lista de despesas
        Vector<Despesa> despesas = new Vector<>(controller.listarDespesas());
        listaDespesasList.setListData(despesas);
        // Atualiza a lista de participantes
        Vector<Participante> participantes = new Vector<>(controller.listarParticipantes());
        listaParticipantesList.setListData(participantes);
    }

    public PainelDespesas(PlanejamentoController controller) {
        this.controller = controller;
        this.descricaoField = new JTextField(20);
        this.valorField = new JTextField(10);
        this.tipoField = new JComboBox<String>(
                new String[] { PlanejamentoController.TIPO_FIXA, PlanejamentoController.TIPO_POR_PESSOA });
        this.tipoField.setSelectedIndex(0);
        this.listaParticipantesList = new JList<Participante>();
        this.listaDespesasList = new JList<Despesa>();

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        JPanel camposPanel = new JPanel(new FlowLayout());
        JLabel descricaoLabel = new JLabel("Descrição:");
        camposPanel.add(descricaoLabel);
        camposPanel.add(this.descricaoField);
        JLabel valorLabel = new JLabel("Valor:");
        camposPanel.add(valorLabel);
        camposPanel.add(this.valorField);
        JLabel tipoLabel = new JLabel("Tipo:");
        camposPanel.add(tipoLabel);
        camposPanel.add(this.tipoField);

        JPanel botoesPanel = new JPanel(new FlowLayout());
        JButton adicionarButton = new JButton("Incluir Despesa");
        adicionarButton.addActionListener(e -> incluirDespesa());
        botoesPanel.add(adicionarButton);
        JButton alterarButton = new JButton("Editar Despesa");
        alterarButton.addActionListener(e -> editarDespesa());
        listaDespesasList.addListSelectionListener(e -> preencherFormulario());
        botoesPanel.add(alterarButton);
        JButton excluirButton = new JButton("Excluir Despesa");
        excluirButton.addActionListener(e -> excluirDespesa());
        botoesPanel.add(excluirButton);

        JPanel formularioPanel = new JPanel(new GridLayout(2, 1));
        formularioPanel.add(camposPanel);
        formularioPanel.add(botoesPanel);

        JScrollPane scrollDespesas = new JScrollPane(this.listaDespesasList);
        JScrollPane scrollParticipantes = new JScrollPane(this.listaParticipantesList);

        add(scrollDespesas, BorderLayout.CENTER);
        add(formularioPanel, BorderLayout.NORTH);
        add(scrollParticipantes, BorderLayout.WEST);
    }

    private void incluirDespesa() {
        String descricao = descricaoField.getText();
        String valorTexto = valorField.getText();
        String tipo = (String) tipoField.getSelectedItem();
        List<Participante> participantesSelecionados = listaParticipantesList.getSelectedValuesList();

        try {
            controller.incluirDespesa(descricao, valorTexto, tipo, participantesSelecionados);
            atualizarListas();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Despesa incluída com sucesso!");
        } catch (PlanejamentoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao incluir despesa: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarDespesa() {
        Despesa despesaSelecionada = listaDespesasList.getSelectedValue();
        if (despesaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma despesa para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String descricao = descricaoField.getText();
        String valorTexto = valorField.getText();
        String tipo = (String) tipoField.getSelectedItem();
        List<Participante> participantesSelecionados = listaParticipantesList.getSelectedValuesList();

        try {
            controller.editarDespesa(despesaSelecionada, descricao, valorTexto, tipo, participantesSelecionados);
            atualizarListas();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Despesa editada com sucesso!");
        } catch (PlanejamentoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar despesa: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirDespesa() {
        Despesa despesaSelecionada = listaDespesasList.getSelectedValue();
        if (despesaSelecionada != null) {
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir a despesa: " + despesaSelecionada.getDescricao() + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                controller.removerDespesa(despesaSelecionada);
                atualizarListas();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Despesa excluída com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma despesa para excluir.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void preencherFormulario() {
        Despesa selecionada = listaDespesasList.getSelectedValue();
        if (selecionada == null) {
            return;
        }
        descricaoField.setText(selecionada.getDescricao());
        valorField.setText(String.format("%d,%02d", selecionada.getValor() / 100, selecionada.getValor() % 100));
        tipoField.setSelectedItem(selecionada.getTipo());
        listaParticipantesList.clearSelection();

        List<Participante> todos = controller.listarParticipantes();
        for (int i = 0; i < todos.size(); i++) {
            Participante p = todos.get(i);
            if (selecionada.getParticipantes().contains(p)) {
                listaParticipantesList.addSelectionInterval(i, i);
            }
        }
    }

    private void limparCampos() {
        descricaoField.setText("");
        valorField.setText("");
        tipoField.setSelectedIndex(0);
        listaParticipantesList.clearSelection();
    }
}