package com.restaurante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {
    private JTextField nomeClienteField;
    private JTextField contatoClienteField;
    private JTextField detalhesPedidoField;
    private JTextArea outputArea;
    private JList<String> listaPedidosPendentes;
    private JList<String> listaPedidosProntos;
    private JList<String> listaPedidosPagos;

    private DefaultListModel<String> pedidosPendentesModel;
    private DefaultListModel<String> pedidosProntosModel;
    private DefaultListModel<String> pedidosPagosModel;

    private Garcom garcom;
    private Cozinheiro cozinheiro;
    private Caixa caixa;

    private List<Pedido> pedidos;

    public MainGUI() {
        garcom = new Garcom("Carlos", "carlos@restaurante.com");
        cozinheiro = new Cozinheiro("Mariana", "mariana@restaurante.com");
        caixa = new Caixa();
        pedidos = new ArrayList<>();

        setTitle("Sistema de Comanda Digital");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nome do Cliente:"), gbc);
        gbc.gridx = 1;
        nomeClienteField = new JTextField(20);
        inputPanel.add(nomeClienteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Contato do Cliente:"), gbc);
        gbc.gridx = 1;
        contatoClienteField = new JTextField(20);
        inputPanel.add(contatoClienteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Detalhes do Pedido:"), gbc);
        gbc.gridx = 1;
        detalhesPedidoField = new JTextField(20);
        inputPanel.add(detalhesPedidoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
        JButton adicionarPedidoButton = new JButton("Adicionar Pedido");
        JButton pedidoProntoButton = new JButton("Pedido Pronto");
        JButton processarPagamentoButton = new JButton("Processar Pagamento");
        buttonPanel.add(adicionarPedidoButton);
        buttonPanel.add(pedidoProntoButton);
        buttonPanel.add(processarPagamentoButton);
        inputPanel.add(buttonPanel, gbc);

        adicionarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPedido();
            }
        });

        pedidoProntoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pedidoPronto();
            }
        });

        processarPagamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarPagamento();
            }
        });

        add(inputPanel, BorderLayout.NORTH);

        // Área de saída
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Lista de pedidos pendentes
        pedidosPendentesModel = new DefaultListModel<>();
        listaPedidosPendentes = new JList<>(pedidosPendentesModel);
        JScrollPane listaPendentesScrollPane = new JScrollPane(listaPedidosPendentes);
        listaPendentesScrollPane.setBorder(BorderFactory.createTitledBorder("Pedidos Pendentes"));

        // Lista de pedidos prontos
        pedidosProntosModel = new DefaultListModel<>();
        listaPedidosProntos = new JList<>(pedidosProntosModel);
        JScrollPane listaProntosScrollPane = new JScrollPane(listaPedidosProntos);
        listaProntosScrollPane.setBorder(BorderFactory.createTitledBorder("Pedidos Prontos"));

        // Lista de pedidos pagos
        pedidosPagosModel = new DefaultListModel<>();
        listaPedidosPagos = new JList<>(pedidosPagosModel);
        JScrollPane listaPagosScrollPane = new JScrollPane(listaPedidosPagos);
        listaPagosScrollPane.setBorder(BorderFactory.createTitledBorder("Pagamentos Processados"));

        // Painel lateral com as três listas
        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new GridLayout(1, 3, 10, 10));
        listaPanel.add(listaPendentesScrollPane);
        listaPanel.add(listaProntosScrollPane);
        listaPanel.add(listaPagosScrollPane);
        add(listaPanel, BorderLayout.EAST);

        // Estilo da janela
        setBackground(Color.LIGHT_GRAY);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Novo Pedido"));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Histórico de Pedidos"));
    }

    private void adicionarPedido() {
        String nomeCliente = nomeClienteField.getText();
        String contatoCliente = contatoClienteField.getText();
        String detalhesPedido = detalhesPedidoField.getText();

        if (nomeCliente.isEmpty() || contatoCliente.isEmpty() || detalhesPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nomeCliente, contatoCliente);
        Pedido pedido = garcom.anotarPedido(cliente, detalhesPedido);
        pedidos.add(pedido);

        outputArea.append("Pedido anotado: " + detalhesPedido + " (ID: " + pedidos.size() + ")\n");
        pedidosPendentesModel.addElement("Pedido ID: " + pedidos.size() + " - " + detalhesPedido);

        // Limpar os campos de entrada após adicionar o pedido
        nomeClienteField.setText("");
        contatoClienteField.setText("");
        detalhesPedidoField.setText("");
    }

    private void pedidoPronto() {
        int selectedIndex = listaPedidosPendentes.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na lista de pedidos pendentes", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedPedidoStr = pedidosPendentesModel.get(selectedIndex);
        int pedidoID = Integer.parseInt(selectedPedidoStr.split(" ")[2]);

        Pedido pedido = pedidos.get(pedidoID - 1);
        cozinheiro.prepararPedido(pedido);

        outputArea.append("Pedido pronto: " + pedido.getDetalhes() + " (ID: " + pedidoID + ")\n");
        pedidosPendentesModel.remove(selectedIndex);
        pedidosProntosModel.addElement("Pedido ID: " + pedidoID + " - " + pedido.getDetalhes());
    }

    private void processarPagamento() {
        int selectedIndex = listaPedidosProntos.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na lista de pedidos prontos", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedPedidoStr = pedidosProntosModel.get(selectedIndex);
        int pedidoID = Integer.parseInt(selectedPedidoStr.split(" ")[2]);

        Pedido pedido = pedidos.get(pedidoID - 1);
        garcom.realizarAcao();  // Garçom entrega o pedido ao cliente
        caixa.processarPagamento(pedido);

        outputArea.append("Pagamento processado: " + pedido.getDetalhes() + " (ID: " + pedidoID + ")\n");
        pedidosProntosModel.remove(selectedIndex);
        pedidosPagosModel.addElement("Pedido ID: " + pedidoID + " - " + pedido.getDetalhes());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}
