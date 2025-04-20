package VIEW;

import CONTROLLER.ProductosController;
import MODEL.Productos;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductosView extends JFrame {

    private JTextField txtId, txtNombre, txtPrecioKg, txtStockKg, txtIdTipo, txtIdProveedor;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ProductosController controller;

    public ProductosView() {
        controller = new ProductosController();
        initComponents();
        setupListeners();
        cargarProductos();
    }

    private void initComponents() {
        setTitle("Gestión de Productos");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Tipo:", txtIdTipo = createTextField(true), 1);
        addFormField(formPanel, gbc, "ID Proveedor:", txtIdProveedor = createTextField(true), 2);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 3);
        addFormField(formPanel, gbc, "Precio/Kg:", txtPrecioKg = createTextField(true), 4);
        addFormField(formPanel, gbc, "Stock/Kg:", txtStockKg = createTextField(true), 5);

        // Botones
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

        // Tabla
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "ID Tipo", "ID Proveedor", "Nombre", "Precio/Kg", "Stock/Kg"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Productos Registrados"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> guardarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
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

    private void guardarProducto() {
        try {
            if (validarCampos()) {
                controller.insertarProducto(
                    Integer.parseInt(txtIdTipo.getText()),
                    Integer.parseInt(txtIdProveedor.getText()),
                    txtNombre.getText().trim(),
                    Double.parseDouble(txtPrecioKg.getText()),
                    Double.parseDouble(txtStockKg.getText())
                );
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
                limpiarCampos();
                cargarProductos();
            }
        } catch (NumberFormatException ex) {
            mostrarError("Por favor ingrese valores numéricos válidos");
        } catch (Exception ex) {
            mostrarError("Error al guardar el producto: " + ex.getMessage());
        }
    }

    private void actualizarProducto() {
        if (!txtId.getText().isEmpty()) {
            try {
                if (validarCampos()) {
                    controller.actualizarProducto(
                        Integer.parseInt(txtId.getText()),
                        Integer.parseInt(txtIdTipo.getText()),
                        Integer.parseInt(txtIdProveedor.getText()),
                        txtNombre.getText().trim(),
                        Double.parseDouble(txtPrecioKg.getText()),
                        Double.parseDouble(txtStockKg.getText())
                    );
                    JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
                    limpiarCampos();
                    cargarProductos();
                }
            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese valores numéricos válidos");
            } catch (Exception ex) {
                mostrarError("Error al actualizar el producto: " + ex.getMessage());
            }
        } else {
            mostrarError("Seleccione un producto para actualizar");
        }
    }

    private void eliminarProducto() {
        if (!txtId.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro de eliminar este producto?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.eliminarProducto(Integer.parseInt(txtId.getText()));
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
                    limpiarCampos();
                    cargarProductos();
                } catch (Exception ex) {
                    mostrarError("Error al eliminar el producto: " + ex.getMessage());
                }
            }
        } else {
            mostrarError("Seleccione un producto para eliminar");
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || 
            txtIdTipo.getText().trim().isEmpty() || 
            txtIdProveedor.getText().trim().isEmpty() || 
            txtPrecioKg.getText().trim().isEmpty() || 
            txtStockKg.getText().trim().isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return false;
        }
        
        try {
            double precio = Double.parseDouble(txtPrecioKg.getText());
            double stock = Double.parseDouble(txtStockKg.getText());
            
            if (precio <= 0 || stock < 0) {
                mostrarError("Precio y stock deben ser valores positivos");
                return false;
            }
        } catch (NumberFormatException ex) {
            mostrarError("Precio y stock deben ser valores numéricos válidos");
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("No se pudo aplicar FlatLaf.");
        }
        SwingUtilities.invokeLater(() -> new ProductosView().setVisible(true));
    }
}