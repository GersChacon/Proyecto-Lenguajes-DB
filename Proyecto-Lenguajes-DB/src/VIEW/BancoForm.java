package VIEW;

import CONTROLLER.BancosController;
import MODEL.Bancos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class BancoForm extends JFrame {

    private JTextField txtId, txtNombre, txtDireccion, txtTelefono, txtEmail;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private BancosController controller;

    public BancoForm() {
        controller = new BancosController();

        setTitle("Gestión de Bancos");
        setSize(700, 450);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 200, 25);
        txtId.setEnabled(false);
        add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 60, 200, 25);
        add(txtNombre);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(20, 100, 100, 25);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(130, 100, 200, 25);
        add(txtDireccion);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 140, 100, 25);
        add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(130, 140, 200, 25);
        add(txtTelefono);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 180, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(130, 180, 200, 25);
        add(txtEmail);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 60, 100, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 100, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 140, 100, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección", "Teléfono", "Email"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 230, 640, 150);
        add(scroll);

        cargarBancos();

        btnGuardar.addActionListener(e -> {
            controller.insertarBanco(
                    txtNombre.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText()
            );
            limpiarCampos();
            cargarBancos();
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
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
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtDireccion.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtEmail.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        setVisible(true);
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

    public static void main(String[] args) {
        new BancoForm();
    }
}
