package VIEW;

import CONTROLLER.PagoController;
import MODEL.Pago;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PagoView extends JFrame {

    private JTextField txtId, txtPedido, txtMonto;
    private JComboBox<String> cmbEstado;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PagoController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PagoView() {
        controller = new PagoController();
        initComponents();
        setupListeners();
        cargarPagos();
    }

    private void initComponents() {
        setTitle("Gestión de Pagos");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Pago"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID Pago:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Pedido:", txtPedido = createTextField(true), 1);
        addFormField(formPanel, gbc, "Monto:", txtMonto = createTextField(true), 2);

        // ComboBox para Estado
        gbc.gridy = 3;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        cmbEstado = new JComboBox<>(new String[]{"pendiente", "pagado", "cancelado"});
        cmbEstado.setPreferredSize(new Dimension(200, 25));
        formPanel.add(cmbEstado, gbc);

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
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Pedido", "Monto", "Fecha", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Pagos Registrados"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
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
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
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
            } else {
                mostrarError("Selecciona un pago para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Deseas eliminar este pago?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarPago(Integer.parseInt(txtId.getText()));
                        limpiarCampos();
                        cargarPagos();
                    } catch (Exception ex) {
                        mostrarError("Error al eliminar: " + ex.getMessage());
                    }
                }
            } else {
                mostrarError("Selecciona un pago para eliminar.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtPedido.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtMonto.setText(modeloTabla.getValueAt(fila, 2).toString());
                cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 4));
            }
        });
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

    private void limpiarCampos() {
        txtId.setText("");
        txtPedido.setText("");
        txtMonto.setText("");
        cmbEstado.setSelectedIndex(0);
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
        SwingUtilities.invokeLater(() -> new PagoView().setVisible(true));
    }
}
