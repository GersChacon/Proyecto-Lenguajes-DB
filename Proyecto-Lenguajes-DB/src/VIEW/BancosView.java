package VIEW;

import CONTROLLER.BancosController;
import MODEL.Bancos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BancosView extends JFrame {

    private JTextField txtId, txtNombre, txtDireccion, txtTelefono, txtEmail;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private BancosController controller;

    public BancosView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller = new BancosController();
        initComponents();
        setupListeners();
        cargarBancos();
    }

    private void initComponents() {
        setTitle("Gestión de Bancos - Modern UI");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Datos del Banco"
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos del formulario
        addFormField(formPanel, gbc, "ID:", txtId = createTextField(false), 0);
        addFormField(formPanel, gbc, "Nombre:", txtNombre = createTextField(true), 1);
        addFormField(formPanel, gbc, "Dirección:", txtDireccion = createTextField(true), 2);
        addFormField(formPanel, gbc, "Teléfono:", txtTelefono = createTextField(true), 3);
        addFormField(formPanel, gbc, "Email:", txtEmail = createTextField(true), 4);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnGuardar = createStyledButton("Guardar", new Color(76, 175, 80));
        btnActualizar = createStyledButton("Actualizar", new Color(33, 150, 243));
        btnEliminar = createStyledButton("Eliminar", new Color(244, 67, 54));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);

        // Panel superior (formulario + botones)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección", "Teléfono", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        tabla = new JTable(modeloTabla);
        styleTable(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Lista de Bancos"
        ));

        // Agregar componentes al panel principal
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void setupListeners() {
        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                controller.insertarBanco(
                        txtNombre.getText(),
                        txtDireccion.getText(),
                        txtTelefono.getText(),
                        txtEmail.getText()
                );
                limpiarCampos();
                cargarBancos();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty() && validarCampos()) {
                int id = Integer.parseInt(txtId.getText());
                controller.actualizarBanco(id,
                        txtNombre.getText(),
                        txtDireccion.getText(),
                        txtTelefono.getText(),
                        txtEmail.getText()
                );
                limpiarCampos();
                cargarBancos();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarBanco(id);
                limpiarCampos();
                cargarBancos();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) { // Verificar que se haya seleccionado una fila válida
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtDireccion.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtEmail.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Puedes agregar más validaciones aquí
        return true;
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    private void cargarBancos() {
        modeloTabla.setRowCount(0);
        List<Bancos> lista = controller.obtenerTodosLosBancos();
        for (Bancos banco : lista) {
            modeloTabla.addRow(new Object[]{
                    banco.getIdBancos(),
                    banco.getNombre(),
                    banco.getDireccion(),
                    banco.getTelefono(),
                    banco.getEmail()
            });
        }
    }

    // Métodos auxiliares para crear componentes estilizados
    private JTextField createTextField(boolean enabled) {
        JTextField field = new JTextField(25);
        field.setEnabled(enabled);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setShowGrid(false);
        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(table.getFont().deriveFont(Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BancosView().setVisible(true);
        });
    }
}