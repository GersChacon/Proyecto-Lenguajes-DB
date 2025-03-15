-- =============================
-- 1. CREACIÓN DE TABLAS
-- =============================

-- Tabla Bancos
CREATE TABLE Bancos (
    id_banco INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    direccion VARCHAR2(255),
    telefono VARCHAR2(50),
    email VARCHAR2(100)
);

-- Tabla Clientes
CREATE TABLE Clientes (
    id_cliente INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(255) NOT NULL,
    direccion VARCHAR2(500),
    telefono VARCHAR2(20),
    email VARCHAR2(100)
);

-- Tabla Proveedores
CREATE TABLE Proveedores (
    id_proveedor INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(255) NOT NULL,
    telefono VARCHAR2(20),
    email VARCHAR2(100),
    direccion VARCHAR2(500)
);

-- Tabla Categorías
CREATE TABLE Categorias (
    id_categoria INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) UNIQUE NOT NULL
);

-- Tabla Tipos de Producto
CREATE TABLE Tipos (
    id_tipo INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre VARCHAR2(100) UNIQUE NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria)
);

-- Tabla Productos
CREATE TABLE Productos (
    id_producto INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_tipo INT NOT NULL,
    id_proveedor INT NOT NULL,
    nombre VARCHAR2(255) NOT NULL,
    precio_kg NUMBER(10,2) NOT NULL CHECK (precio_kg >= 0),
    stock_kg NUMBER(10,2) NOT NULL CHECK (stock_kg >= 0),
    FOREIGN KEY (id_tipo) REFERENCES Tipos(id_tipo),
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

-- Tabla Estados de Pedido
CREATE TABLE Estados_Pedido (
    id_estado INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(50) UNIQUE NOT NULL
);

-- Tabla Pedidos
CREATE TABLE Pedidos (
    id_pedido INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_estado INT NOT NULL,
    fecha_pedido DATE DEFAULT SYSDATE NOT NULL,
    monto_pagado NUMBER(10,2) DEFAULT 0 CHECK (monto_pagado >= 0),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_estado) REFERENCES Estados_Pedido(id_estado)
);

-- Tabla Detalle de Pedido
CREATE TABLE Detalle_Pedido (
    id_detalle INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad_kg NUMBER(10,2) NOT NULL CHECK (cantidad_kg > 0),
    precio_unitario NUMBER(10,2) NOT NULL,
    subtotal NUMBER(10,2) GENERATED ALWAYS AS (cantidad_kg * precio_unitario),
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto) ON DELETE CASCADE
);

-- Tabla Inventario
CREATE TABLE Inventario (
    id_movimiento INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_producto INT NOT NULL,
    tipo_movimiento VARCHAR2(10) CHECK (tipo_movimiento IN ('entrada', 'salida')),
    cantidad_kg NUMBER(10,2) NOT NULL CHECK (cantidad_kg > 0),
    fecha_movimiento DATE DEFAULT SYSDATE NOT NULL,
    id_detalle_pedido INT NULL, 
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto),
    FOREIGN KEY (id_detalle_pedido) REFERENCES Detalle_Pedido(id_detalle)
);

-- Tabla Métodos de Pago
CREATE TABLE Metodos_Pago (
    id_metodo_pago INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(50) UNIQUE NOT NULL
);

-- Tabla Pagos
CREATE TABLE Pagos (
    id_pago NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido NUMBER NOT NULL,
    monto NUMBER(10,2) NOT NULL CHECK (monto > 0),
    fecha_pago DATE DEFAULT SYSDATE,
    estado_pago VARCHAR2(20) CHECK (estado_pago IN ('pendiente', 'pagado', 'cancelado')),
    CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido) ON DELETE CASCADE
);

-- Tabla Detalles de Pago
CREATE TABLE Detalles_Pago (
    id_detalle_pago INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pago INT NOT NULL,
    id_metodo_pago INT NOT NULL,
    id_banco INT, 
    numero_tarjeta VARCHAR2(19), 
    nombre_titular VARCHAR2(100), 
    fecha_expiracion DATE, 
    numero_transferencia VARCHAR2(50), 
    FOREIGN KEY (id_pago) REFERENCES Pagos(id_pago),
    FOREIGN KEY (id_metodo_pago) REFERENCES Metodos_Pago(id_metodo_pago),
    FOREIGN KEY (id_banco) REFERENCES Bancos(id_banco)
);


-- =============================
-- 2. CRUD PARA BANCOS
-- =============================

CREATE OR REPLACE PROCEDURE InsertarBanco(
    p_nombre IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_email IN VARCHAR2
) AS
BEGIN
    INSERT INTO Bancos (nombre, direccion, telefono, email)
    VALUES (p_nombre, p_direccion, p_telefono, p_email);
    COMMIT;
END InsertarBanco;
/

CREATE OR REPLACE PROCEDURE ActualizarBanco(
    p_id_banco IN INT,
    p_nombre IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_email IN VARCHAR2
) AS
BEGIN
    UPDATE Bancos
    SET nombre = p_nombre,
        direccion = p_direccion,
        telefono = p_telefono,
        email = p_email
    WHERE id_banco = p_id_banco;
    COMMIT;
END ActualizarBanco;
/

CREATE OR REPLACE PROCEDURE EliminarBanco(p_id_banco IN INT) AS
BEGIN
    DELETE FROM Bancos WHERE id_banco = p_id_banco;
    COMMIT;
END EliminarBanco;
/

-- =============================
-- 3. CRUD PARA CLIENTES
-- =============================

CREATE OR REPLACE PROCEDURE InsertarCliente(
    p_nombre IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_email IN VARCHAR2
) AS
BEGIN
    INSERT INTO Clientes (nombre, direccion, telefono, email)
    VALUES (p_nombre, p_direccion, p_telefono, p_email);
    COMMIT;
END InsertarCliente;
/

CREATE OR REPLACE PROCEDURE ActualizarCliente(
    p_id_cliente IN INT,
    p_nombre IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_email IN VARCHAR2
) AS
BEGIN
    UPDATE Clientes
    SET nombre = p_nombre,
        direccion = p_direccion,
        telefono = p_telefono,
        email = p_email
    WHERE id_cliente = p_id_cliente;
    COMMIT;
END ActualizarCliente;
/

CREATE OR REPLACE PROCEDURE EliminarCliente(p_id_cliente IN INT) AS
BEGIN
    DELETE FROM Clientes WHERE id_cliente = p_id_cliente;
    COMMIT;
END EliminarCliente;
/

-- =============================
-- 4. CRUD PARA PROVEEDORES
-- =============================

CREATE OR REPLACE PROCEDURE InsertarProveedor(
    p_nombre IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_email IN VARCHAR2,
    p_direccion IN VARCHAR2
) AS
BEGIN
    INSERT INTO Proveedores (nombre, telefono, email, direccion)
    VALUES (p_nombre, p_telefono, p_email, p_direccion);
    COMMIT;
END InsertarProveedor;
/

CREATE OR REPLACE PROCEDURE ActualizarProveedor(
    p_id_proveedor IN INT,
    p_nombre IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_email IN VARCHAR2,
    p_direccion IN VARCHAR2
) AS
BEGIN
    UPDATE Proveedores
    SET nombre = p_nombre,
        telefono = p_telefono,
        email = p_email,
        direccion = p_direccion
    WHERE id_proveedor = p_id_proveedor;
    COMMIT;
END ActualizarProveedor;
/

CREATE OR REPLACE PROCEDURE EliminarProveedor(p_id_proveedor IN INT) AS
BEGIN
    DELETE FROM Proveedores WHERE id_proveedor = p_id_proveedor;
    COMMIT;
END EliminarProveedor;
/

-- =============================
-- 5. CRUD PARA CATEGORIAS
-- =============================

CREATE OR REPLACE PROCEDURE InsertarCategoria(
    p_nombre IN VARCHAR2
) AS
BEGIN
    INSERT INTO Categorias (nombre)
    VALUES (p_nombre);
    COMMIT;
END InsertarCategoria;
/

CREATE OR REPLACE PROCEDURE ActualizarCategoria(
    p_id_categoria IN INT,
    p_nombre IN VARCHAR2
) AS
BEGIN
    UPDATE Categorias
    SET nombre = p_nombre
    WHERE id_categoria = p_id_categoria;
    COMMIT;
END ActualizarCategoria;
/

CREATE OR REPLACE PROCEDURE EliminarCategoria(p_id_categoria IN INT) AS
BEGIN
    DELETE FROM Categorias WHERE id_categoria = p_id_categoria;
    COMMIT;
END EliminarCategoria;
/

-- =============================
-- 6. CRUD PARA TIPOS DE PRODUCTO
-- =============================

CREATE OR REPLACE PROCEDURE InsertarTipoProducto(
    p_id_categoria IN INT,
    p_nombre IN VARCHAR2
) AS
BEGIN
    INSERT INTO Tipos (id_categoria, nombre)
    VALUES (p_id_categoria, p_nombre);
    COMMIT;
END InsertarTipoProducto;
/

CREATE OR REPLACE PROCEDURE ActualizarTipoProducto(
    p_id_tipo IN INT,
    p_id_categoria IN INT,
    p_nombre IN VARCHAR2
) AS
BEGIN
    UPDATE Tipos
    SET id_categoria = p_id_categoria,
        nombre = p_nombre
    WHERE id_tipo = p_id_tipo;
    COMMIT;
END ActualizarTipoProducto;
/

CREATE OR REPLACE PROCEDURE EliminarTipoProducto(p_id_tipo IN INT) AS
BEGIN
    DELETE FROM Tipos WHERE id_tipo = p_id_tipo;
    COMMIT;
END EliminarTipoProducto;
/

-- =============================
-- 7. CRUD PARA PRODUCTOS
-- =============================

CREATE OR REPLACE PROCEDURE InsertarProducto(
    p_id_tipo IN INT,
    p_id_proveedor IN INT,
    p_nombre IN VARCHAR2,
    p_precio_kg IN NUMBER,
    p_stock_kg IN NUMBER
) AS
BEGIN
    INSERT INTO Productos (id_tipo, id_proveedor, nombre, precio_kg, stock_kg)
    VALUES (p_id_tipo, p_id_proveedor, p_nombre, p_precio_kg, p_stock_kg);
    COMMIT;
END InsertarProducto;
/

CREATE OR REPLACE PROCEDURE ActualizarProducto(
    p_id_producto IN INT,
    p_id_tipo IN INT,
    p_id_proveedor IN INT,
    p_nombre IN VARCHAR2,
    p_precio_kg IN NUMBER,
    p_stock_kg IN NUMBER
) AS
BEGIN
    UPDATE Productos
    SET id_tipo = p_id_tipo,
        id_proveedor = p_id_proveedor,
        nombre = p_nombre,
        precio_kg = p_precio_kg,
        stock_kg = p_stock_kg
    WHERE id_producto = p_id_producto;
    COMMIT;
END ActualizarProducto;
/

CREATE OR REPLACE PROCEDURE EliminarProducto(p_id_producto IN INT) AS
BEGIN
    DELETE FROM Productos WHERE id_producto = p_id_producto;
    COMMIT;
END EliminarProducto;
/

-- =============================
-- 8. CRUD PARA ESTADOS DE PEDIDO
-- =============================

CREATE OR REPLACE PROCEDURE InsertarEstadoPedido(
    p_nombre IN VARCHAR2
) AS
BEGIN
    INSERT INTO Estados_Pedido (nombre)
    VALUES (p_nombre);
    COMMIT;
END InsertarEstadoPedido;
/

CREATE OR REPLACE PROCEDURE ActualizarEstadoPedido(
    p_id_estado IN INT,
    p_nombre IN VARCHAR2
) AS
BEGIN
    UPDATE Estados_Pedido
    SET nombre = p_nombre
    WHERE id_estado = p_id_estado;
    COMMIT;
END ActualizarEstadoPedido;
/

CREATE OR REPLACE PROCEDURE EliminarEstadoPedido(p_id_estado IN INT) AS
BEGIN
    DELETE FROM Estados_Pedido WHERE id_estado = p_id_estado;
    COMMIT;
END EliminarEstadoPedido;
/

-- =============================
-- 9. CRUD PARA PEDIDOS
-- =============================

CREATE OR REPLACE PROCEDURE InsertarPedido(
    p_id_cliente IN INT,
    p_id_estado IN INT,
    p_fecha_pedido IN DATE DEFAULT SYSDATE,
    p_monto_pagado IN NUMBER DEFAULT 0
) AS
BEGIN
    INSERT INTO Pedidos (id_cliente, id_estado, fecha_pedido, monto_pagado)
    VALUES (p_id_cliente, p_id_estado, p_fecha_pedido, p_monto_pagado);
    COMMIT;
END InsertarPedido;
/

CREATE OR REPLACE PROCEDURE ActualizarPedido(
    p_id_pedido IN INT,
    p_id_cliente IN INT,
    p_id_estado IN INT,
    p_fecha_pedido IN DATE,
    p_monto_pagado IN NUMBER
) AS
BEGIN
    UPDATE Pedidos
    SET id_cliente = p_id_cliente,
        id_estado = p_id_estado,
        fecha_pedido = p_fecha_pedido,
        monto_pagado = p_monto_pagado
    WHERE id_pedido = p_id_pedido;
    COMMIT;
END ActualizarPedido;
/

CREATE OR REPLACE PROCEDURE EliminarPedido(p_id_pedido IN INT) AS
BEGIN
    DELETE FROM Pedidos WHERE id_pedido = p_id_pedido;
    COMMIT;
END EliminarPedido;
/

-- =============================
-- 10. CRUD PARA DETALLES DE PEDIDO
-- =============================

CREATE OR REPLACE PROCEDURE InsertarDetallePedido(
    p_id_pedido IN INT,
    p_id_producto IN INT,
    p_cantidad_kg IN NUMBER,
    p_precio_unitario IN NUMBER
) AS
BEGIN
    INSERT INTO Detalle_Pedido (id_pedido, id_producto, cantidad_kg, precio_unitario)
    VALUES (p_id_pedido, p_id_producto, p_cantidad_kg, p_precio_unitario);
    COMMIT;
END InsertarDetallePedido;
/

CREATE OR REPLACE PROCEDURE ActualizarDetallePedido(
    p_id_detalle IN INT,
    p_id_pedido IN INT,
    p_id_producto IN INT,
    p_cantidad_kg IN NUMBER,
    p_precio_unitario IN NUMBER
) AS
BEGIN
    UPDATE Detalle_Pedido
    SET id_pedido = p_id_pedido,
        id_producto = p_id_producto,
        cantidad_kg = p_cantidad_kg,
        precio_unitario = p_precio_unitario
    WHERE id_detalle = p_id_detalle;
    COMMIT;
END ActualizarDetallePedido;
/

CREATE OR REPLACE PROCEDURE EliminarDetallePedido(p_id_detalle IN INT) AS
BEGIN
    DELETE FROM Detalle_Pedido WHERE id_detalle = p_id_detalle;
    COMMIT;
END EliminarDetallePedido;
/

-- =============================
-- 11. CRUD PARA INVENTARIO
-- =============================

CREATE OR REPLACE PROCEDURE InsertarInventario(
    p_id_producto IN INT,
    p_tipo_movimiento IN VARCHAR2,
    p_cantidad_kg IN NUMBER,
    p_fecha_movimiento IN DATE DEFAULT SYSDATE,
    p_id_detalle_pedido IN INT DEFAULT NULL
) AS
BEGIN
    INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad_kg, fecha_movimiento, id_detalle_pedido)
    VALUES (p_id_producto, p_tipo_movimiento, p_cantidad_kg, p_fecha_movimiento, p_id_detalle_pedido);
    COMMIT;
END InsertarMovimientoInventario;
/

CREATE OR REPLACE PROCEDURE ActualizarInventario(
    p_id_movimiento IN INT,
    p_id_producto IN INT,
    p_tipo_movimiento IN VARCHAR2,
    p_cantidad_kg IN NUMBER,
    p_fecha_movimiento IN DATE,
    p_id_detalle_pedido IN INT
) AS
BEGIN
    UPDATE Inventario
    SET id_producto = p_id_producto,
        tipo_movimiento = p_tipo_movimiento,
        cantidad_kg = p_cantidad_kg,
        fecha_movimiento = p_fecha_movimiento,
        id_detalle_pedido = p_id_detalle_pedido
    WHERE id_movimiento = p_id_movimiento;
    COMMIT;
END ActualizarMovimientoInventario;
/

CREATE OR REPLACE PROCEDURE EliminarInventario(p_id_movimiento IN INT) AS
BEGIN
    DELETE FROM Inventario WHERE id_movimiento = p_id_movimiento;
    COMMIT;
END EliminarMovimientoInventario;
/

-- =============================
-- 12. CRUD PARA METODOS DE PAGO
-- =============================

CREATE OR REPLACE PROCEDURE InsertarMetodoPago(
    p_nombre IN VARCHAR2
) AS
BEGIN
    INSERT INTO Metodos_Pago (nombre)
    VALUES (p_nombre);
    COMMIT;
END InsertarMetodoPago;
/

CREATE OR REPLACE PROCEDURE ActualizarMetodoPago(
    p_id_metodo_pago IN INT,
    p_nombre IN VARCHAR2
) AS
BEGIN
    UPDATE Metodos_Pago
    SET nombre = p_nombre
    WHERE id_metodo_pago = p_id_metodo_pago;
    COMMIT;
END ActualizarMetodoPago;
/

CREATE OR REPLACE PROCEDURE EliminarMetodoPago(p_id_metodo_pago IN INT) AS
BEGIN
    DELETE FROM Metodos_Pago WHERE id_metodo_pago = p_id_metodo_pago;
    COMMIT;
END EliminarMetodoPago;
/

-- =============================
-- 13. CRUD PARA PAGOS
-- =============================

CREATE OR REPLACE PROCEDURE InsertarPago(
    p_id_pedido IN NUMBER,
    p_monto IN NUMBER,
    p_fecha_pago IN DATE DEFAULT SYSDATE,
    p_estado_pago IN VARCHAR2 DEFAULT 'pendiente'
) AS
BEGIN
    INSERT INTO Pagos (id_pedido, monto, fecha_pago, estado_pago)
    VALUES (p_id_pedido, p_monto, p_fecha_pago, p_estado_pago);
    COMMIT;
END InsertarPago;
/

CREATE OR REPLACE PROCEDURE ActualizarPago(
    p_id_pago IN NUMBER,
    p_id_pedido IN NUMBER,
    p_monto IN NUMBER,
    p_fecha_pago IN DATE,
    p_estado_pago IN VARCHAR2
) AS
BEGIN
    UPDATE Pagos
    SET id_pedido = p_id_pedido,
        monto = p_monto,
        fecha_pago = p_fecha_pago,
        estado_pago = p_estado_pago
    WHERE id_pago = p_id_pago;
    COMMIT;
END ActualizarPago;
/

CREATE OR REPLACE PROCEDURE EliminarPago(p_id_pago IN NUMBER) AS
BEGIN
    DELETE FROM Pagos WHERE id_pago = p_id_pago;
    COMMIT;
END EliminarPago;
/

-- =============================
-- 14. CRUD PARA DETALLES DE PAGO
-- =============================

CREATE OR REPLACE PROCEDURE InsertarDetallePago(
    p_id_pago IN INT,
    p_id_metodo_pago IN INT,
    p_id_banco IN INT,
    p_numero_tarjeta IN VARCHAR2,
    p_nombre_titular IN VARCHAR2,
    p_fecha_expiracion IN DATE,
    p_numero_transferencia IN VARCHAR2
) AS
BEGIN
    INSERT INTO Detalles_Pago (id_pago, id_metodo_pago, id_banco, numero_tarjeta, nombre_titular, fecha_expiracion, numero_transferencia)
    VALUES (p_id_pago, p_id_metodo_pago, p_id_banco, p_numero_tarjeta, p_nombre_titular, p_fecha_expiracion, p_numero_transferencia);
    COMMIT;
END InsertarDetallePago;
/

CREATE OR REPLACE PROCEDURE ActualizarDetallePago(
    p_id_detalle_pago IN INT,
    p_id_pago IN INT,
    p_id_metodo_pago IN INT,
    p_id_banco IN INT,
    p_numero_tarjeta IN VARCHAR2,
    p_nombre_titular IN VARCHAR2,
    p_fecha_expiracion IN DATE,
    p_numero_transferencia IN VARCHAR2
) AS
BEGIN
    UPDATE Detalles_Pago
    SET id_pago = p_id_pago,
        id_metodo_pago = p_id_metodo_pago,
        id_banco = p_id_banco,
        numero_tarjeta = p_numero_tarjeta,
        nombre_titular = p_nombre_titular,
        fecha_expiracion = p_fecha_expiracion,
        numero_transferencia = p_numero_transferencia
    WHERE id_detalle_pago = p_id_detalle_pago;
    COMMIT;
END ActualizarDetallePago;
/

CREATE OR REPLACE PROCEDURE EliminarDetallePago(p_id_detalle_pago IN INT) AS
BEGIN
    DELETE FROM Detalles_Pago WHERE id_detalle_pago = p_id_detalle_pago;
    COMMIT;
END EliminarDetallePago;
/

-- =============================
-- 15. FUNCIONES
-- =============================

CREATE OR REPLACE FUNCTION ObtenerTotalStock RETURN NUMBER IS
    total_stock NUMBER;
BEGIN
    SELECT SUM(stock_kg) INTO total_stock FROM Productos;
    RETURN total_stock;
END ObtenerTotalStock;
/

CREATE OR REPLACE FUNCTION ObtenerTotalPedidosCliente(p_id_cliente IN INT) RETURN NUMBER IS
    total_pedidos NUMBER;
BEGIN
    SELECT COUNT(*) INTO total_pedidos FROM Pedidos WHERE id_cliente = p_id_cliente;
    RETURN total_pedidos;
END ObtenerTotalPedidosCliente;
/

-- =============================
-- 16. VISTAS
-- =============================

CREATE OR REPLACE VIEW Vista_Productos AS
SELECT p.id_producto, p.nombre, p.precio_kg, p.stock_kg, c.nombre AS categoria, pr.nombre AS proveedor
FROM Productos p
JOIN Tipos t ON p.id_tipo = t.id_tipo
JOIN Categorias c ON t.id_categoria = c.id_categoria
JOIN Proveedores pr ON p.id_proveedor = pr.id_proveedor;
/

CREATE OR REPLACE VIEW Vista_Pedidos AS
SELECT pe.id_pedido, cl.nombre AS cliente, pe.fecha_pedido, pe.monto_pagado, ep.nombre AS estado
FROM Pedidos pe
JOIN Clientes cl ON pe.id_cliente = cl.id_cliente
JOIN Estados_Pedido ep ON pe.id_estado = ep.id_estado;
/

-- =============================
-- 17. TRIGGERS
-- =============================

CREATE OR REPLACE TRIGGER ActualizarInventarioDespuesDePedido
AFTER INSERT ON Detalle_Pedido
FOR EACH ROW
BEGIN
    UPDATE Productos
    SET stock_kg = stock_kg - :NEW.cantidad_kg
    WHERE id_producto = :NEW.id_producto;
END;
/

CREATE OR REPLACE TRIGGER VerificacionEstadoPago
BEFORE INSERT ON Pagos
FOR EACH ROW
BEGIN
    IF :NEW.estado_pago IS NULL THEN
        :NEW.estado_pago := 'pendiente';
    END IF;
END;

-- =============================
-- 18. CURSORES
-- =============================

CREATE OR REPLACE PROCEDURE ListarProductosBajoStock AS
    CURSOR cur_productos IS
    SELECT id_producto, nombre, stock_kg FROM Productos WHERE stock_kg < 10;
    v_producto Productos%ROWTYPE;
BEGIN
    OPEN cur_productos;
    LOOP
        FETCH cur_productos INTO v_producto;
        EXIT WHEN cur_productos%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Producto: ' || v_producto.nombre || ' - Stock: ' || v_producto.stock_kg);
    END LOOP;
    CLOSE cur_productos;
END ListarProductosBajoStock;
/