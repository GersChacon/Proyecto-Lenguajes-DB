package VIEW;

import CONTROLLER.TiposProductoController;
import MODEL.TiposProducto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TiposProductoView extends JPanel {

    private JTextField txtId, txtIdCategoria, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final TiposProductoController controller;

    public TiposProductoView(TiposProductoController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarTiposProducto();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Tipo de Producto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "ID Categoría:", txtIdCategoria = createTextField(true), 1);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 2);

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

        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Categoría", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Tipos de Producto Registrados"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    controller.insertarTipoProducto(
                            Integer.parseInt(txtIdCategoria.getText()),
                            txtNombre.getText().trim()
                    );
                    mostrarMensaje("Tipo de producto guardado correctamente");
                    limpiarCampos();
                    cargarTiposProducto();
                } catch (NumberFormatException ex) {
                    mostrarError("El ID Categoría debe ser un número válido");
                } catch (Exception ex) {
                    mostrarError("Error al guardar: " + ex.getMessage());
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                try {
                    controller.actualizarTipoProducto(
                            Integer.parseInt(txtId.getText()),
                            Integer.parseInt(txtIdCategoria.getText()),
                            txtNombre.getText().trim()
                    );
                    mostrarMensaje("Tipo de producto actualizado correctamente");
                    limpiarCampos();
                    cargarTiposProducto();
                } catch (NumberFormatException ex) {
                    mostrarError("Los IDs deben ser números válidos");
                } catch (Exception ex) {
                    mostrarError("Error al actualizar: " + ex.getMessage());
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Confirmas que deseas eliminar este tipo de producto?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.eliminarTipoProducto(Integer.parseInt(txtId.getText()));
                        mostrarMensaje("Tipo de producto eliminado correctamente");
                        limpiarCampos();
                        cargarTiposProducto();
                    } catch (Exception ex) {
                        mostrarError("Error al eliminar: " + ex.getMessage());
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
                    txtIdCategoria.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio");
            return false;
        }

        if (txtIdCategoria.getText().trim().isEmpty()) {
            mostrarError("El campo 'ID Categoría' es obligatorio");
            return false;
        }

        try {
            Integer.parseInt(txtIdCategoria.getText());
        } catch (NumberFormatException ex) {
            mostrarError("El ID Categoría debe ser un número válido");
            return false;
        }

        return true;
    }

    private void cargarTiposProducto() {
        modeloTabla.setRowCount(0);
        List<TiposProducto> lista = controller.obtenerTodosLosTiposProducto();
        for (TiposProducto tipo : lista) {
            modeloTabla.addRow(new Object[]{
                tipo.getIdTipo(),
                tipo.getIdCategoria(),
                tipo.getNombre()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtIdCategoria.setText("");
        txtNombre.setText("");
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
