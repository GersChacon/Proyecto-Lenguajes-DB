package VIEW;

import CONTROLLER.CategoriasController;
import MODEL.Categorias;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CategoriasView extends JPanel {

    private JTextField txtId, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final CategoriasController controller;

    public CategoriasView(CategoriasController controller) {
        this.controller = controller;
        initComponents();
        setupListeners();
        cargarCategorias();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos de Categoría"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 1);

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

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Categorías Registradas"));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                controller.insertarCategoria(txtNombre.getText());
                limpiarCampos();
                cargarCategorias();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                controller.actualizarCategoria(Integer.parseInt(txtId.getText()), txtNombre.getText());
                limpiarCampos();
                cargarCategorias();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Confirmas que deseas eliminar esta categoría?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarCategoria(Integer.parseInt(txtId.getText()));
                    limpiarCampos();
                    cargarCategorias();
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
    }

    private void cargarCategorias() {
        modeloTabla.setRowCount(0);
        List<Categorias> lista = controller.obtenerTodasLasCategorias();
        for (Categorias categoria : lista) {
            modeloTabla.addRow(new Object[]{
                categoria.getIdCategoria(),
                categoria.getNombre()
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
