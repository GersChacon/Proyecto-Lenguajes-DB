package VIEW;

import CONTROLLER.DetallePedidoController;
import MODEL.DetallePedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DetallePedidoView extends JPanel {

    private JTextField txtId, txtPedido, txtProducto, txtCantidad, txtPrecio;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final DetallePedidoController controller;

    public DetallePedidoView(DetallePedidoController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarDetalles();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Detalle de Pedido"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Pedido:", txtPedido = createTextField(true), 1);
        addFormField(formPanel, gbc, "ID Producto:", txtProducto = createTextField(true), 2);
        addFormField(formPanel, gbc, "Cantidad (kg):", txtCantidad = createTextField(true), 3);
        addFormField(formPanel, gbc, "Precio Unitario:", txtPrecio = createTextField(true), 4);

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

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "ID Pedido", "ID Producto", "Cantidad (kg)", "Precio Unitario", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Detalles de Pedido Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            try {
                if (validarCampos()) {
                    controller.insertarDetalle(
                            Integer.parseInt(txtPedido.getText()),
                            Integer.parseInt(txtProducto.getText()),
                            Double.parseDouble(txtCantidad.getText()),
                            Double.parseDouble(txtPrecio.getText())
                    );
                    limpiarCampos();
                    cargarDetalles();
                }
            } catch (Exception ex) {
                mostrarError("Error al guardar: " + ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                try {
                    if (validarCampos()) {
                        controller.actualizarDetalle(
                                Integer.parseInt(txtId.getText()),
                                Integer.parseInt(txtPedido.getText()),
                                Integer.parseInt(txtProducto.getText()),
                                Double.parseDouble(txtCantidad.getText()),
                                Double.parseDouble(txtPrecio.getText())
                        );
                        limpiarCampos();
                        cargarDetalles();
                    }
                } catch (Exception ex) {
                    mostrarError("Error al actualizar: " + ex.getMessage());
                }
            } else {
                mostrarError("Selecciona un detalle de pedido para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Estás seguro de eliminar este detalle de pedido?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarDetalle(Integer.parseInt(txtId.getText()));
                    limpiarCampos();
                    cargarDetalles();
                }
            } else {
                mostrarError("Selecciona un detalle de pedido para eliminar.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtPedido.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtProducto.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtCantidad.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtPedido.getText().trim().isEmpty()
                || txtProducto.getText().trim().isEmpty()
                || txtCantidad.getText().trim().isEmpty()
                || txtPrecio.getText().trim().isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return false;
        }

        try {
            double cantidad = Double.parseDouble(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            if (cantidad <= 0 || precio <= 0) {
                mostrarError("Cantidad y precio deben ser valores positivos");
                return false;
            }
        } catch (NumberFormatException ex) {
            mostrarError("Cantidad y precio deben ser valores numéricos válidos");
            return false;
        }

        return true;
    }

    private void cargarDetalles() {
        modeloTabla.setRowCount(0);
        List<DetallePedido> lista = controller.obtenerTodosLosDetalles();
        for (DetallePedido detalle : lista) {
            modeloTabla.addRow(new Object[]{
                detalle.getIdDetalle(),
                detalle.getIdPedido(),
                detalle.getIdProducto(),
                detalle.getCantidadKg(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtPedido.setText("");
        txtProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
    }

    private JTextField createTextField(boolean enabled) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 25));
        field.setEnabled(enabled);
        return field;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
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
