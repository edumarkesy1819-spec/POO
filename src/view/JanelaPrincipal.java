package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.lang.ModuleLayer.Controller;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import controller.PlanejamentoController;
import model.PlanejamentoException;

public class JanelaPrincipal extends JFrame {
    private PlanejamentoController controller;
    private PainelDespesas painelDespesas;
    private PainelResumo painelResumo;
    private PainelParticipantes painelParticipantes;

    public JanelaPrincipal(PlanejamentoController controller) {
        this.controller = controller;
        setTitle("Cabe no bolso");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArquivo = new JMenu("Arquivo");
        menuBar.add(menuArquivo);
        JMenuItem itemNovo = new JMenuItem("Novo");
        menuArquivo.add(itemNovo);
        itemNovo.addActionListener(e -> novo());
        JMenuItem itemAbrir = new JMenuItem("Abrir");
        menuArquivo.add(itemAbrir);
        itemAbrir.addActionListener(e -> abrir());
        JMenuItem itemSalvar = new JMenuItem("Salvar");
        menuArquivo.add(itemSalvar);
        itemSalvar.addActionListener(e -> salvar());

        JTabbedPane abas = new JTabbedPane();
        add(abas);
        this.painelParticipantes = new PainelParticipantes(controller);
        abas.addTab("Participantes", this.painelParticipantes);

        this.painelDespesas = new PainelDespesas(controller);
        abas.addTab("Despesas", this.painelDespesas);

        this.painelResumo = new PainelResumo(controller);
        abas.addTab("Resumo", this.painelResumo);

        abas.addChangeListener(e -> {
            if (abas.getSelectedComponent() == painelDespesas) {
                painelDespesas.atualizarListas();
            } else if (abas.getSelectedComponent() == painelResumo) {
                painelResumo.atualizarResumo();
            }
        });
    }

    private void atualizarTelas() {
        painelParticipantes.atualizarListaParticipantes();
        painelDespesas.atualizarListas();
        painelResumo.atualizarResumo();
    }

    private void novo() {
        if (JOptionPane.showConfirmDialog(this,
                "Deseja criar um novo planejamento? As alterações não salvas serão perdidas.", "Novo Planejamento",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.novoPlanejamento();
            atualizarTelas();
        }
    }

    private void salvar() {
        try {
            controller.salvarPlanejamento();
            JOptionPane.showMessageDialog(this, "Planejamento salvo com sucesso!");

        } catch (PlanejamentoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar planejamento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrir() {
        try {
            controller.carregarPlanejamento();
            atualizarTelas();
            JOptionPane.showMessageDialog(this, "Planejamento carregado com sucesso!");
        } catch (PlanejamentoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir planejamento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}