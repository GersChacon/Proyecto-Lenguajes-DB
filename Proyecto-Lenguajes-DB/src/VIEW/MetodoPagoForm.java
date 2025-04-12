package VIEW;

import CONTROLLER.MetodoPagoController;
import MODEL.MetodoPago;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class MetodoPagoForm extends JFrame {

    private JTextField txtId, txtNombre;
    private JButton btnGuardar, btnActualizar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private MetodoPagoController controller;

    public MetodoPagoForm() {
        controller = new MetodoPagoController();

        setTitle("Gestión de Métodos de Pago");
        setSize(600, 400);
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

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 20, 100, 30);
        add(btnGuardar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 60, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 100, 100, 30);
        add(btnEliminar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 550, 200);
        add(scroll);

        cargarMetodosPago();

        // Eventos
        btnGuardar.addActionListener(e -> {
            controller.insertarMetodoPago(txtNombre.getText());
            limpiarCampos();
            cargarMetodosPago();
        });

        btnActualizar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.actualizarMetodoPago(id, txtNombre.getText());
                limpiarCampos();
                cargarMetodosPago();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminarMetodoPago(id);
                limpiarCampos();
                cargarMetodosPago();
            }
        });

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            }
        });

        setVisible(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
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

    public static void main(String[] args) {
        new MetodoPagoForm();
    }
}
