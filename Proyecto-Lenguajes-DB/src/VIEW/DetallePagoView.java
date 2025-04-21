package VIEW;

import CONTROLLER.DetallePagoController;
import MODEL.DetallePago;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetallePagoView extends JPanel {

    private JTextField txtId, txtPago, txtBanco, txtNumeroTarjeta, txtNombreTitular, txtNumeroTransferencia;
    private JComboBox<String> cmbMetodoPago;
    private JFormattedTextField txtFechaExpiracion;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final DetallePagoController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DetallePagoView(DetallePagoController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarMetodosPago();
        cargarDetallesPago();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Detalle de Pago"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Pago:", txtPago = createTextField(true), 1);

        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Método Pago:"), gbc);

        gbc.gridx = 1;
        cmbMetodoPago = new JComboBox<>();
        cmbMetodoPago.setPreferredSize(new Dimension(200, 25));
        formPanel.add(cmbMetodoPago, gbc);

        addFormField(formPanel, gbc, "ID Banco:", txtBanco = createTextField(true), 3);
        addFormField(formPanel, gbc, "Número Tarjeta:", txtNumeroTarjeta = createTextField(true), 4);
        addFormField(formPanel, gbc, "Nombre Titular:", txtNombreTitular = createTextField(true), 5);

        gbc.gridy = 6;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Fecha Expiración:"), gbc);

        gbc.gridx = 1;
        txtFechaExpiracion = new JFormattedTextField(dateFormat);
        txtFechaExpiracion.setPreferredSize(new Dimension(200, 25));
        formPanel.add(txtFechaExpiracion, gbc);

        addFormField(formPanel, gbc, "Número Transferencia:", txtNumeroTransferencia = createTextField(true), 7);

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
                new String[]{"ID", "ID Pago", "Método", "Banco", "Tarjeta", "Titular", "Expiración", "Transferencia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Detalles de Pago Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> guardarDetallePago());
        btnActualizar.addActionListener(e -> actualizarDetallePago());
        btnEliminar.addActionListener(e -> eliminarDetallePago());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtPago.setText(modeloTabla.getValueAt(fila, 1).toString());
                    cmbMetodoPago.setSelectedItem(modeloTabla.getValueAt(fila, 2));
                    txtBanco.setText(modeloTabla.getValueAt(fila, 3) != null ? modeloTabla.getValueAt(fila, 3).toString() : "");
                    txtNumeroTarjeta.setText(modeloTabla.getValueAt(fila, 4).toString());
                    txtNombreTitular.setText(modeloTabla.getValueAt(fila, 5).toString());
                    txtFechaExpiracion.setText(modeloTabla.getValueAt(fila, 6) != null ? modeloTabla.getValueAt(fila, 6).toString() : "");
                    txtNumeroTransferencia.setText(modeloTabla.getValueAt(fila, 7) != null ? modeloTabla.getValueAt(fila, 7).toString() : "");
                }
            }
        });
    }

    private void guardarDetallePago() {
        try {
            String nombreTitular = txtNombreTitular.getText().trim();
            if (nombreTitular.isEmpty()) {
                mostrarError("El campo 'Nombre Titular' no puede estar vacío.");
                return;
            }

            controller.insertarDetallePago(
                    Integer.parseInt(txtPago.getText()),
                    cmbMetodoPago.getSelectedIndex() + 1,
                    txtBanco.getText().isEmpty() ? null : Integer.parseInt(txtBanco.getText()),
                    txtNumeroTarjeta.getText(),
                    nombreTitular,
                    txtFechaExpiracion.getText().isEmpty() ? null : dateFormat.parse(txtFechaExpiracion.getText()),
                    txtNumeroTransferencia.getText()
            );
            limpiarCampos();
            cargarDetallesPago();
        } catch (Exception ex) {
            mostrarError("Error al guardar: " + ex.getMessage());
        }
    }

    private void actualizarDetallePago() {
        if (!txtId.getText().isEmpty()) {
            try {
                String nombreTitular = txtNombreTitular.getText().trim();
                if (nombreTitular.isEmpty()) {
                    mostrarError("El campo 'Nombre Titular' no puede estar vacío.");
                    return;
                }

                controller.actualizarDetallePago(
                        Integer.parseInt(txtId.getText()),
                        Integer.parseInt(txtPago.getText()),
                        cmbMetodoPago.getSelectedIndex() + 1,
                        txtBanco.getText().isEmpty() ? null : Integer.parseInt(txtBanco.getText()),
                        txtNumeroTarjeta.getText(),
                        nombreTitular,
                        txtFechaExpiracion.getText().isEmpty() ? null : dateFormat.parse(txtFechaExpiracion.getText()),
                        txtNumeroTransferencia.getText()
                );
                limpiarCampos();
                cargarDetallesPago();
            } catch (Exception ex) {
                mostrarError("Error al actualizar: " + ex.getMessage());
            }
        } else {
            mostrarError("Selecciona un detalle de pago para actualizar.");
        }
    }

    private void eliminarDetallePago() {
        if (!txtId.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de eliminar este detalle de pago?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarDetallePago(Integer.parseInt(txtId.getText()));
                limpiarCampos();
                cargarDetallesPago();
            }
        } else {
            mostrarError("Selecciona un detalle de pago para eliminar.");
        }
    }

    private void cargarMetodosPago() {
        cmbMetodoPago.addItem("Efectivo");
        cmbMetodoPago.addItem("Tarjeta");
        cmbMetodoPago.addItem("Transferencia");
    }

    private void cargarDetallesPago() {
        modeloTabla.setRowCount(0);
        List<DetallePago> lista = controller.obtenerTodosLosDetallesPago();
        for (DetallePago detalle : lista) {
            modeloTabla.addRow(new Object[]{
                detalle.getIdDetallePago(),
                detalle.getIdPago(),
                detalle.getIdMetodoPago() == 1 ? "Efectivo"
                : detalle.getIdMetodoPago() == 2 ? "Tarjeta" : "Transferencia",
                detalle.getIdBanco(),
                detalle.getNumeroTarjeta(),
                detalle.getNombreTitular(),
                detalle.getFechaExpiracion() != null ? dateFormat.format(detalle.getFechaExpiracion()) : "",
                detalle.getNumeroTransferencia()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtPago.setText("");
        cmbMetodoPago.setSelectedIndex(0);
        txtBanco.setText("");
        txtNumeroTarjeta.setText("");
        txtNombreTitular.setText("");
        txtFechaExpiracion.setText("");
        txtNumeroTransferencia.setText("");
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

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
