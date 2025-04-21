package VIEW;

import CONTROLLER.ProductosController;
import MODEL.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductosView extends JPanel {

    private JTextField txtId, txtNombre, txtPrecioKg, txtStockKg, txtIdTipo, txtIdProveedor;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final ProductosController controller;

    public ProductosView(ProductosController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarProductos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Tipo:", txtIdTipo = createTextField(true), 1);
        addFormField(formPanel, gbc, "ID Proveedor:", txtIdProveedor = createTextField(true), 2);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 3);
        addFormField(formPanel, gbc, "Precio/Kg:", txtPrecioKg = createTextField(true), 4);
        addFormField(formPanel, gbc, "Stock/Kg:", txtStockKg = createTextField(true), 5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "ID Tipo", "ID Proveedor", "Nombre", "Precio/Kg", "Stock/Kg"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Productos Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    controller.insertarProducto(
                            Integer.parseInt(txtIdTipo.getText()),
                            Integer.parseInt(txtIdProveedor.getText()),
                            txtNombre.getText().trim(),
                            Double.parseDouble(txtPrecioKg.getText()),
                            Double.parseDouble(txtStockKg.getText())
                    );
                    mostrarMensaje("Producto guardado correctamente");
                    limpiarCampos();
                    cargarProductos();
                } catch (Exception ex) {
                    mostrarError("Error al guardar el producto: " + ex.getMessage());
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                try {
                    controller.actualizarProducto(
                            Integer.parseInt(txtId.getText()),
                            Integer.parseInt(txtIdTipo.getText()),
                            Integer.parseInt(txtIdProveedor.getText()),
                            txtNombre.getText().trim(),
                            Double.parseDouble(txtPrecioKg.getText()),
                            Double.parseDouble(txtStockKg.getText())
                    );
                    mostrarMensaje("Producto actualizado correctamente");
                    limpiarCampos();
                    cargarProductos();
                } catch (Exception ex) {
                    mostrarError("Error al actualizar el producto: " + ex.getMessage());
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Confirmas que deseas eliminar este producto?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarProducto(Integer.parseInt(txtId.getText()));
                        mostrarMensaje("Producto eliminado correctamente");
                        limpiarCampos();
                        cargarProductos();
                    } catch (Exception ex) {
                        mostrarError("Error al eliminar el producto: " + ex.getMessage());
                    }
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtIdTipo.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtIdProveedor.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtPrecioKg.setText(modeloTabla.getValueAt(fila, 4).toString());
                    txtStockKg.setText(modeloTabla.getValueAt(fila, 5).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio");
            return false;
        }
        if (txtIdTipo.getText().trim().isEmpty()) {
            mostrarError("El campo 'ID Tipo' es obligatorio");
            return false;
        }
        if (txtIdProveedor.getText().trim().isEmpty()) {
            mostrarError("El campo 'ID Proveedor' es obligatorio");
            return false;
        }
        if (txtPrecioKg.getText().trim().isEmpty()) {
            mostrarError("El campo 'Precio/Kg' es obligatorio");
            return false;
        }
        if (txtStockKg.getText().trim().isEmpty()) {
            mostrarError("El campo 'Stock/Kg' es obligatorio");
            return false;
        }

        try {
            double precio = Double.parseDouble(txtPrecioKg.getText());
            if (precio <= 0) {
                mostrarError("El precio debe ser mayor que cero");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El precio debe ser un valor numérico válido");
            return false;
        }

        try {
            double stock = Double.parseDouble(txtStockKg.getText());
            if (stock < 0) {
                mostrarError("El stock no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El stock debe ser un valor numérico válido");
            return false;
        }

        return true;
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        List<Productos> lista = controller.obtenerTodosLosProductos();
        for (Productos producto : lista) {
            modeloTabla.addRow(new Object[]{
                producto.getIdProducto(),
                producto.getIdTipo(),
                producto.getIdProveedor(),
                producto.getNombre(),
                String.format("%.2f", producto.getPrecioKg()),
                String.format("%.2f", producto.getStockKg())
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtIdTipo.setText("");
        txtIdProveedor.setText("");
        txtNombre.setText("");
        txtPrecioKg.setText("");
        txtStockKg.setText("");
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

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
