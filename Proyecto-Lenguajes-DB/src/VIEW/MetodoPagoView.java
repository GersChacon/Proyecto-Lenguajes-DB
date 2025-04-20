package VIEW;

import CONTROLLER.MetodoPagoController;
import MODEL.MetodoPago;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MetodoPagoView extends JFrame {

    private JTextField txtId, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private MetodoPagoController controller;

    public MetodoPagoView() {
        controller = new MetodoPagoController();
        initComponents();
        setupListeners();
        cargarMetodosPago();
    }

    private void initComponents() {
        setTitle("Gestión de Métodos de Pago");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Método de Pago"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 1);

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
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Métodos de Pago Registrados"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                controller.insertarMetodoPago(nombre);
                limpiarCampos();
                cargarMetodosPago();
            } else {
                mostrarError("El campo 'Nombre' no puede estar vacío.");
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nombre = txtNombre.getText().trim();
                    if (!nombre.isEmpty()) {
                        controller.actualizarMetodoPago(id, nombre);
                        limpiarCampos();
                        cargarMetodosPago();
                    } else {
                        mostrarError("El campo 'Nombre' no puede estar vacío.");
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("ID inválido.");
                }
            } else {
                mostrarError("Selecciona un método de pago para actualizar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas eliminar este método de pago?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        int id = Integer.parseInt(txtId.getText());
                        controller.eliminarMetodoPago(id);
                        limpiarCampos();
                        cargarMetodosPago();
                    } catch (NumberFormatException ex) {
                        mostrarError("ID inválido.");
                    }
                }
            } else {
                mostrarError("Selecciona un método de pago para eliminar.");
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            }
        });
    }

    private void cargarMetodosPago() {
        modeloTabla.setRowCount(0);
        List<MetodoPago> lista = controller.obtenerTodosLosMetodosPago();
        for (MetodoPago metodo : lista) {
            modeloTabla.addRow(new Object[]{
                metodo.getIdMetodoPago(),
                metodo.getNombre()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
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
        SwingUtilities.invokeLater(() -> new MetodoPagoView().setVisible(true));
    }
}
