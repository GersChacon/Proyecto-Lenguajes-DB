package VIEW;

import CONTROLLER.PedidosController;
import MODEL.Pedidos;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PedidosView extends JFrame {

    private JTextField txtId, txtCliente, txtEstado, txtMonto;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PedidosController controller;

    public PedidosView() {
        controller = new PedidosController();
        initComponents();
        setupListeners();
        cargarPedidos();
    }

    private void initComponents() {
        setTitle("Gestión de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Cliente:", txtCliente = createTextField(true), 1);
        addFormField(formPanel, gbc, "ID Estado:", txtEstado = createTextField(true), 2);
        addFormField(formPanel, gbc, "Monto Pagado:", txtMonto = createTextField(true), 3);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Cliente", "ID Estado", "Fecha", "Monto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Pedidos Registrados"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            try {
                controller.insertarPedido(
                        Integer.parseInt(txtCliente.getText()),
                        Integer.parseInt(txtEstado.getText()),
                        Double.parseDouble(txtMonto.getText())
                );
                limpiarCampos();
                cargarPedidos();
            } catch (Exception ex) {
                mostrarError("Datos inválidos: " + ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                try {
                    controller.actualizarPedido(
                            Integer.parseInt(txtId.getText()),
                            Integer.parseInt(txtCliente.getText()),
                            Integer.parseInt(txtEstado.getText()),
                            Double.parseDouble(txtMonto.getText())
                    );
                    limpiarCampos();
                    cargarPedidos();
                } catch (Exception ex) {
                    mostrarError("Error al actualizar: " + ex.getMessage());
                }
            } else {
                mostrarError("Selecciona un pedido para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Deseas eliminar este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarPedido(Integer.parseInt(txtId.getText()));
                        limpiarCampos();
                        cargarPedidos();
                    } catch (Exception ex) {
                        mostrarError("Error al eliminar: " + ex.getMessage());
                    }
                }
            } else {
                mostrarError("Selecciona un pedido para eliminar.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtCliente.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtEstado.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtMonto.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        List<Pedidos> lista = controller.obtenerTodosLosPedidos();
        for (Pedidos pedido : lista) {
            modeloTabla.addRow(new Object[]{
                pedido.getIdPedido(),
                pedido.getIdCliente(),
                pedido.getIdEstado(),
                pedido.getFechaPedido(),
                pedido.getMontoPagado()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCliente.setText("");
        txtEstado.setText("");
        txtMonto.setText("");
    }

    private JTextField createTextField(boolean enabled) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 25));
        field.setEnabled(enabled);
        return field;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("No se pudo aplicar FlatLaf.");
        }
        SwingUtilities.invokeLater(() -> new PedidosView().setVisible(true));
    }
}
