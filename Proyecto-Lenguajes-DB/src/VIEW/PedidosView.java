package VIEW;

import CONTROLLER.PedidosController;
import MODEL.Pedidos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PedidosView extends JPanel {

    private JTextField txtId, txtCliente, txtEstado, txtMonto;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final PedidosController controller;

    public PedidosView(PedidosController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarPedidos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Cliente:", txtCliente = createTextField(true), 1);
        addFormField(formPanel, gbc, "ID Estado:", txtEstado = createTextField(true), 2);
        addFormField(formPanel, gbc, "Monto Pagado:", txtMonto = createTextField(true), 3);

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

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Cliente", "ID Estado", "Fecha", "Monto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Pedidos Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    controller.insertarPedido(
                            Integer.parseInt(txtCliente.getText()),
                            Integer.parseInt(txtEstado.getText()),
                            Double.parseDouble(txtMonto.getText())
                    );
                    limpiarCampos();
                    cargarPedidos();
                } catch (Exception ex) {
                    mostrarError("Error al guardar: " + ex.getMessage());
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
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
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Confirmas que deseas eliminar este pedido?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarPedido(Integer.parseInt(txtId.getText()));
                        limpiarCampos();
                        cargarPedidos();
                    } catch (Exception ex) {
                        mostrarError("Error al eliminar: " + ex.getMessage());
                    }
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtCliente.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtEstado.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtMonto.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtCliente.getText().trim().isEmpty()) {
            mostrarError("El campo 'ID Cliente' es obligatorio");
            return false;
        }
        if (txtEstado.getText().trim().isEmpty()) {
            mostrarError("El campo 'ID Estado' es obligatorio");
            return false;
        }
        if (txtMonto.getText().trim().isEmpty()) {
            mostrarError("El campo 'Monto Pagado' es obligatorio");
            return false;
        }
        try {
            Integer.parseInt(txtCliente.getText());
            Integer.parseInt(txtEstado.getText());
            Double.parseDouble(txtMonto.getText());
        } catch (NumberFormatException e) {
            mostrarError("Los campos numéricos deben contener valores válidos");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCliente.setText("");
        txtEstado.setText("");
        txtMonto.setText("");
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

    private JTextField createTextField(boolean enabled) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(250, 25));
        field.setEnabled(enabled);
        return field;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
