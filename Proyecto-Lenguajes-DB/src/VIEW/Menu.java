package VIEW;

import CONTROLLER.BancosController;
import CONTROLLER.CategoriasController;
import CONTROLLER.ClientesController;
import CONTROLLER.DetallePagoController;
import CONTROLLER.DetallePedidoController;
import CONTROLLER.EstadoPedidoController;
import CONTROLLER.InventarioController;
import CONTROLLER.MetodoPagoController;
import CONTROLLER.PagoController;
import CONTROLLER.PedidosController;
import CONTROLLER.ProductosController;
import CONTROLLER.ProveedoresController;
import CONTROLLER.TiposProductoController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private BancosController bancosController;
    private CategoriasController categoriasController;
    private ClientesController clientesController;
    private DetallePagoController detallePagoController;
    private DetallePedidoController detallePedidoController;
    private EstadoPedidoController estadoPedidoController;
    private InventarioController inventarioController;
    private MetodoPagoController metodoPagoController;
    private PagoController pagoController;
    private PedidosController pedidosController;
    private ProductosController productosController;
    private ProveedoresController proveedoresController;
    private TiposProductoController tiposProductoController;

    public Menu() {
        bancosController = new BancosController();
        categoriasController = new CategoriasController();
        clientesController = new ClientesController();
        detallePagoController = new DetallePagoController();
        detallePedidoController = new DetallePedidoController();
        estadoPedidoController = new EstadoPedidoController();
        inventarioController = new InventarioController();
        metodoPagoController = new MetodoPagoController();
        pagoController = new PagoController();
        pedidosController = new PedidosController();
        productosController = new ProductosController();
        proveedoresController = new ProveedoresController();
        tiposProductoController = new TiposProductoController();

        setTitle("Proyecto");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        BancosView bancosView = new BancosView(bancosController);
        CategoriasView categoriasView = new CategoriasView(categoriasController);
        ClientesView clientesView = new ClientesView(clientesController);
        DetallePagoView detallePagoView = new DetallePagoView(detallePagoController);
        DetallePedidoView detallePedidoView = new DetallePedidoView(detallePedidoController);
        EstadoPedidoView estadoPedidoView = new EstadoPedidoView(estadoPedidoController);
        InventarioView inventarioView = new InventarioView(inventarioController);
        MetodoPagoView metodoPagoView = new MetodoPagoView(metodoPagoController);
        PagoView pagoView = new PagoView(pagoController);
        PedidosView pedidosView = new PedidosView(pedidosController);
        ProductosView productosView = new ProductosView(productosController);
        ProveedoresView proveedoresView = new ProveedoresView(proveedoresController);
        TiposProductoView tiposProductoView = new TiposProductoView(tiposProductoController);

        cardPanel.add(bancosView, "Bancos");
        cardPanel.add(categoriasView, "Categorias");
        cardPanel.add(clientesView, "Clientes");
        cardPanel.add(detallePagoView, "DetallePago");
        cardPanel.add(detallePedidoView, "DetallePedido");
        cardPanel.add(estadoPedidoView, "EstadoPedido");
        cardPanel.add(inventarioView, "Inventario");
        cardPanel.add(metodoPagoView, "MetodoPago");
        cardPanel.add(pagoView, "Pago");
        cardPanel.add(pedidosView, "Pedidos");
        cardPanel.add(productosView, "Productos");
        cardPanel.add(proveedoresView, "Proveedores");
        cardPanel.add(tiposProductoView, "TiposProducto");

        JMenuBar menuBar = new JMenuBar();

        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(salirItem);

        JMenu gestionMenu = new JMenu("Gestión");
        addMenuItem(gestionMenu, "Bancos", e -> cardLayout.show(cardPanel, "Bancos"));
        addMenuItem(gestionMenu, "Categorias", e -> cardLayout.show(cardPanel, "Categorias"));
        addMenuItem(gestionMenu, "Clientes", e -> cardLayout.show(cardPanel, "Clientes"));
        addMenuItem(gestionMenu, "DetallePago", e -> cardLayout.show(cardPanel, "DetallePago"));
        addMenuItem(gestionMenu, "DetallePedido", e -> cardLayout.show(cardPanel, "DetallePedido"));
        addMenuItem(gestionMenu, "EstadoPedido", e -> cardLayout.show(cardPanel, "EstadoPedido"));
        addMenuItem(gestionMenu, "Inventario", e -> cardLayout.show(cardPanel, "Inventario"));
        addMenuItem(gestionMenu, "MetodoPago", e -> cardLayout.show(cardPanel, "MetodoPago"));
        addMenuItem(gestionMenu, "Pago", e -> cardLayout.show(cardPanel, "Pago"));
        addMenuItem(gestionMenu, "Pedidos", e -> cardLayout.show(cardPanel, "Pedidos"));
        addMenuItem(gestionMenu, "Productos", e -> cardLayout.show(cardPanel, "Productos"));
        addMenuItem(gestionMenu, "Proveedores", e -> cardLayout.show(cardPanel, "Proveedores"));
        addMenuItem(gestionMenu, "TiposProducto", e -> cardLayout.show(cardPanel, "TiposProducto"));

        menuBar.add(archivoMenu);
        menuBar.add(gestionMenu);

        setJMenuBar(menuBar);
        add(cardPanel, BorderLayout.CENTER);
    }

    private void addMenuItem(JMenu menu, String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(listener);
        menu.add(item);
    }
}
