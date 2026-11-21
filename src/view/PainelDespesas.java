package view;

import model.Participante;
import model.Despesa;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
        botoesPanel.add(adicionarButton);
        JButton alterarButton = new JButton("Editar Despesa");
        botoesPanel.add(alterarButton);
        JButton excluirButton = new JButton("Excluir Despesa");
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

}
