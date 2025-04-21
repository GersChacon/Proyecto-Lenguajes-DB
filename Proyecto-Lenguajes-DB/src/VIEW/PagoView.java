package VIEW;

import CONTROLLER.PagoController;
import MODEL.Pago;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PagoView extends JPanel {

    private JTextField txtId, txtPedido, txtMonto;
    private JComboBox<String> cmbEstado;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final PagoController controller;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PagoView(PagoController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarPagos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Pago"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID Pago:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Pedido:", txtPedido = createTextField(true), 1);
        addFormField(formPanel, gbc, "Monto:", txtMonto = createTextField(true), 2);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        cmbEstado = new JComboBox<>(new String[]{"pendiente", "pagado", "cancelado"});
        cmbEstado.setPreferredSize(new Dimension(250, 25));
        formPanel.add(cmbEstado, gbc);

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

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Pedido", "Monto", "Fecha", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Pagos Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    controller.insertarPago(
                            Integer.parseInt(txtPedido.getText()),
                            Double.parseDouble(txtMonto.getText()),
                            (String) cmbEstado.getSelectedItem()
                    );
                    limpiarCampos();
                    cargarPagos();
                } catch (Exception ex) {
                    mostrarError("Datos inválidos: " + ex.getMessage());
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                try {
                    controller.actualizarPago(
                            Integer.parseInt(txtId.getText()),
                            Integer.parseInt(txtPedido.getText()),
                            Double.parseDouble(txtMonto.getText()),
                            (String) cmbEstado.getSelectedItem()
                    );
                    limpiarCampos();
                    cargarPagos();
                } catch (Exception ex) {
                    mostrarError("Error al actualizar: " + ex.getMessage());
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Confirmas que deseas eliminar este pago?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarPago(Integer.parseInt(txtId.getText()));
                        limpiarCampos();
                        cargarPagos();
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
                    txtPedido.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtMonto.setText(modeloTabla.getValueAt(fila, 2).toString());
                    cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 4));
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtPedido.getText().trim().isEmpty()) {
            mostrarError("El campo 'ID Pedido' es obligatorio");
            return false;
        }
        if (txtMonto.getText().trim().isEmpty()) {
            mostrarError("El campo 'Monto' es obligatorio");
            return false;
        }
        try {
            Double.parseDouble(txtMonto.getText());
        } catch (NumberFormatException e) {
            mostrarError("El monto debe ser un valor numérico válido");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtPedido.setText("");
        txtMonto.setText("");
        cmbEstado.setSelectedIndex(0);
    }

    private void cargarPagos() {
        modeloTabla.setRowCount(0);
        List<Pago> lista = controller.obtenerTodosLosPagos();
        for (Pago pago : lista) {
            modeloTabla.addRow(new Object[]{
                pago.getIdPago(),
                pago.getIdPedido(),
                pago.getMonto(),
                dateFormat.format(pago.getFechaPago()),
                pago.getEstadoPago()
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
