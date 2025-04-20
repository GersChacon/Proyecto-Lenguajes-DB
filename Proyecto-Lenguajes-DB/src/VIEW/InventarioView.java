package VIEW;

import CONTROLLER.InventarioController;
import MODEL.Inventario;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class InventarioView extends JFrame {

    private JTextField txtId, txtProducto, txtCantidad, txtDetallePedido;
    private JComboBox<String> cmbTipoMovimiento;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private InventarioController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public InventarioView() {
        controller = new InventarioController();
        initComponents();
        setupListeners();
        cargarMovimientos();
    }

    private void initComponents() {
        setTitle("Gestión de Inventario");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Movimiento de Inventario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID Movimiento:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Producto:", txtProducto = createTextField(true), 1);

        // ComboBox para Tipo de Movimiento
        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Tipo Movimiento:"), gbc);
        gbc.gridx = 1;
        cmbTipoMovimiento = new JComboBox<>(new String[]{"entrada", "salida"});
        cmbTipoMovimiento.setPreferredSize(new Dimension(200, 25));
        formPanel.add(cmbTipoMovimiento, gbc);

        addFormField(formPanel, gbc, "Cantidad (kg):", txtCantidad = createTextField(true), 3);
        addFormField(formPanel, gbc, "ID Detalle Pedido:", txtDetallePedido = createTextField(true), 4);

        // Panel de botones
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

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Producto", "Tipo", "Cantidad", "Fecha", "Detalle Pedido"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Movimientos de Inventario Registrados"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> guardarMovimiento());
        btnActualizar.addActionListener(e -> actualizarMovimiento());
        btnEliminar.addActionListener(e -> eliminarMovimiento());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarMovimientoDeTabla();
            }
        });
    }

    private void guardarMovimiento() {
        try {
            Integer detallePedido = txtDetallePedido.getText().isEmpty() ? null : Integer.parseInt(txtDetallePedido.getText());
            controller.insertarMovimiento(
                    Integer.parseInt(txtProducto.getText()),
                    (String) cmbTipoMovimiento.getSelectedItem(),
                    Double.parseDouble(txtCantidad.getText()),
                    detallePedido
            );
            limpiarCampos();
            cargarMovimientos();
        } catch (Exception ex) {
            mostrarError("Datos inválidos: " + ex.getMessage());
        }
    }

    private void actualizarMovimiento() {
        if (!txtId.getText().isEmpty()) {
            try {
                Integer detallePedido = txtDetallePedido.getText().isEmpty() ? null : Integer.parseInt(txtDetallePedido.getText());
                controller.actualizarMovimiento(
                        Integer.parseInt(txtId.getText()),
                        Integer.parseInt(txtProducto.getText()),
                        (String) cmbTipoMovimiento.getSelectedItem(),
                        Double.parseDouble(txtCantidad.getText()),
                        detallePedido
                );
                limpiarCampos();
                cargarMovimientos();
            } catch (Exception ex) {
                mostrarError("Error al actualizar: " + ex.getMessage());
            }
        } else {
            mostrarError("Selecciona un movimiento para actualizar.");
        }
    }

    private void eliminarMovimiento() {
        if (!txtId.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Deseas eliminar este movimiento?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.eliminarMovimiento(Integer.parseInt(txtId.getText()));
                limpiarCampos();
                cargarMovimientos();
            }
        } else {
            mostrarError("Selecciona un movimiento para eliminar.");
        }
    }

    private void seleccionarMovimientoDeTabla() {
        int fila = tabla.getSelectedRow();
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtProducto.setText(modeloTabla.getValueAt(fila, 1).toString());
        cmbTipoMovimiento.setSelectedItem(modeloTabla.getValueAt(fila, 2));
        txtCantidad.setText(modeloTabla.getValueAt(fila, 3).toString());
        Object detalle = modeloTabla.getValueAt(fila, 5);
        txtDetallePedido.setText(detalle != null ? detalle.toString() : "");
    }

    private void cargarMovimientos() {
        modeloTabla.setRowCount(0);
        List<Inventario> lista = controller.obtenerTodosLosMovimientos();
        for (Inventario movimiento : lista) {
            modeloTabla.addRow(new Object[]{
                movimiento.getIdMovimiento(),
                movimiento.getIdProducto(),
                movimiento.getTipoMovimiento(),
                movimiento.getCantidadKg(),
                dateFormat.format(movimiento.getFechaMovimiento()),
                movimiento.getIdDetallePedido() != null ? movimiento.getIdDetallePedido() : ""
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtProducto.setText("");
        cmbTipoMovimiento.setSelectedIndex(0);
        txtCantidad.setText("");
        txtDetallePedido.setText("");
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
        SwingUtilities.invokeLater(() -> new InventarioView().setVisible(true));
    }
}
