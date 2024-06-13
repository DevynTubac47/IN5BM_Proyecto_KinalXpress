-- Base de Datos - KinalXpress
-- Devyn Orlando Tubac Gomez
-- Carne: 2020247
-- Codigo Tecnico: IN5BM

drop database if exists dbKinalXpress;

create database dbKinalXpress;

use dbKinalXpress;

set global time_zone = '-6:00';

alter user 'root'@'localhost' IDENTIFIED WITH mysql_native_password by 'Tottus47d';

create table Usuario(
	idUsuario int not null auto_increment,
	email varchar(100),
    contrasenia varchar(100),
    nombreU varchar(100),
    apellidoU varchar(100),
    telefono varchar(8),
    nombreUsuario varchar(50),
    primary key PK_Usuario(idUsuario)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_CargoEmpleado(codigoCargoEmpleado)
);

create table Clientes(
	clienteID int not null,
	nombresCliente varchar(50),
    apellidosCliente varchar(50),
    NITClientes varchar(10) not null,
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    correoCliente varchar(45),
    primary key PK_Clientes(clienteID)
);

create table Proveedores(
	codigoProveedor int not null,
    NITProvedor varchar(10) not null,
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_Proveedores(codigoProveedor)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2) default 0.00,
    primary key PK_Compras(numeroDocumento)
);

create table TipoProducto(
	codigoTipoProducto int,
    descripcion varchar(45),
    primary key PK_TipoProducto(codigoTipoProducto)
);

create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_Empleados(codigoEmpleado),
    constraint FK_Empleados_CargoEmpleado foreign key Empleados(codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado) on delete cascade
);

create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_EmailProveedor(codigoEmailProveedor),
    constraint FK_EmailProveedor_Proveedores foreign key EmailProveedor(codigoProveedor)
		references Proveedores(codigoProveedor) on delete cascade
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_TelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_TelefonoProveedor_Proveedores foreign key TelefonoProveedor(codigoProveedor)
		references Proveedores(codigoProveedor) on delete cascade
);

create table Productos(
	codigoProducto varchar(15) not null,
    descripcionProducto varchar(45),
    precioUnitario decimal(10,2) default 0.0,
    precioDocena decimal(10,2) default 0.0,
    precioMayor decimal(10,2) default 0.0,
    existencia int(11),
    codigoTipoProducto int,
    codigoProveedor int,
    primary key PK_Producto(codigoProducto),
    constraint FK_Producto_TipoProducto foreign key Productos(codigoTipoProducto)
		references TipoProducto (codigoTipoProducto) on delete cascade,
	constraint FK_Producto_Proveedor foreign key Productos(codigoProveedor)
		references Proveedores(codigoProveedor) on delete cascade
);


create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_DetalleCompra(codigoDetalleCompra),
    constraint FK_DetalleCompra_Productos foreign key DetalleCompra(codigoProducto)
		references Productos(codigoProducto) on delete cascade,
	constraint FK_DetalleCompra_Compras foreign key DetalleCompra(numeroDocumento)
		references	Compras(numeroDocumento) on delete cascade
);

create table Factura(
	numeroFactura int not null,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura date,
    clienteID int,
    codigoEmpleado int,
    primary key PK_Factura(numeroFactura),
    constraint FK_Factura_Clientes foreign key Factura(clienteID)
		references Clientes(clienteID) on delete cascade,
	constraint FK_Factura_Empleados foreign key Factura(codigoEmpleado)
		references Empleados(codigoEmpleado) on delete cascade
);

create table DetalleFactura(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int,
    codigoProducto varchar(15),
    primary key PK_DetalleFactura(codigoDetalleFactura),
    constraint FK_DetalleFactura_Factura foreign key DetalleFactura(numeroFactura)
		references Factura(numeroFactura) on delete cascade,
	constraint FK_DetalleFactura foreign key DetalleFactura(codigoProducto)
		references Productos(codigoProducto) on delete cascade
);

create table Inventario(
    codigoProducto varchar(15),
    cantidad int,
    numeroDocumento int,
    primary key PK_Inventario(codigoProducto),
    constraint FK_Inventario_Producto foreign key Inventario(codigoProducto)
		references Productos(codigoProducto) on delete cascade,
	constraint FK_Inventario_Compras foreign key Inventario(numeroDocumento)
		references Compras(numeroDocumento) on delete cascade
);

delimiter $$
create trigger tr_ActualizarInventario_After_Insert
after insert on DetalleCompra
for each row
begin
	declare cantidadComprada int;
    declare codigoProducto varchar(15);
    
    set cantidadComprada = new.cantidad;
    set codigoProducto = new.codigoProducto;
    
    update Inventario
		set cantidad = cantidad + cantidadComprada
    where Inventario.codigoProducto = codigoProducto;
end $$
delimiter ;

delimiter $$
create trigger tr_AgregarProducto_Inventario_After_Insert
after insert on DetalleCompra
for each row
begin
    declare productoExistente int;

    select count(*) into productoExistente
    from Inventario
    where codigoProducto = new.codigoProducto;

    if productoExistente = 0 then
        insert into Inventario(codigoProducto, cantidad, numeroDocumento)
        values (new.codigoProducto, new.cantidad, new.numeroDocumento);
    end if;
end $$
delimiter ;

delimiter $$
create function fn_totalInventario()
returns decimal(10,2)
reads sql data
begin
	declare total decimal(10,2);
    select sum(totalDocumento) into total
    from Compras
    where numeroDocumento in (select numeroDocumento from Inventario);
    
    return total;
end $$
delimiter ;

select fn_totalInventario() as resultado;

create view vw_Inventario as
select Productos.codigoProducto, Productos.descripcionProducto, inventario.cantidad, Productos.precioUnitario,Productos.existencia,Proveedores.razonSocial,TipoProducto.descripcion,Compras.totalDocumento
from Inventario
inner join Productos on Inventario.codigoProducto = Productos.codigoProducto
inner join Proveedores on Proveedores.codigoProveedor = Productos.codigoProveedor
inner join TipoProducto on TipoProducto.codigoTipoProducto = Productos.codigoTipoProducto
inner join Compras on Inventario.numeroDocumento = Compras.numeroDocumento;

select * from Inventario;
select * from vw_inventario;


-- --------------------------- Usuario ---------------------------------
insert into Usuario(email,contrasenia,nombreU,apellidoU,telefono,nombreUsuario) 
values ('devynGomez@gmail.com','abc123**','Devyn','Gomez','12345678','Devyn Gomez');

select * from Usuario where email = email and contrasenia = contrasenia;
select * from Usuario;
select count(*) from Usuario where email = email;
select count(*) from Usuario where nombreUsuario = nombreUsuario;

-- --------------------------- Cargo Empleado --------------------------- 
delimiter $$
create procedure sp_agregarCargoEmpleado(in codigoCargoEmpleado int,in nombreCargo varchar(45), in descripcionCargo varchar(45))
begin
	insert into CargoEmpleado(codigoCargoEmpleado,nombreCargo,descripcionCargo)
    values(codigoCargoEmpleado,nombreCargo,descripcionCargo);
end $$
delimiter ;

call sp_agregarCargoEmpleado(1,'Gerente de Tienda','Responsable de la gestión operativa');
call sp_agregarCargoEmpleado(2,'Cajero','Registra las Compras');
call sp_agregarCargoEmpleado(3,'Reponedor de Almacén','Responsable de mantener los estantes');
call sp_agregarCargoEmpleado(4,'Encargado de Frutas y Verduras','Encargado de la selección y exhibición');
call sp_agregarCargoEmpleado(5,'Jefe de Seguridad','Responsable de la seguridad de la tienda');
call sp_agregarCargoEmpleado(6,'Atención al Cliente','Encargado de brindar asistencia');


delimiter $$
create procedure sp_listarCargoEmpleado()
begin
	select * from CargoEmpleado;
end $$
delimiter ;

call sp_listarCargoEmpleado();

delimiter $$
create procedure sp_buscarCargoEmpleado(in codigoCargoEmpleado int)
begin
	select * from CargoEmpleado where codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;

call sp_buscarCargoEmpleado(1);

delimiter $$
create procedure sp_actualizarCargoEmpleado(in codigoCargoEmpleado int,in nombreCargo varchar(45), in descripcionCargo varchar(45))
begin
	update CargoEmpleado
    set
		CargoEmpleado.nombreCargo = nombreCargo,
        CargoEmpleado.descripcionCargo = descripcionCargo
	where
		CargoEmpleado.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarCargoEmpleado(in codigoCargoEmpleado int)
begin
	delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = codigoCargoEmpleado;
end $$
delimiter ;

-- ---------------------------  Clientes --------------------------- 
 delimiter $$
 create procedure sp_agregarClientes(in clienteID int, in nombresCliente varchar(50), in apellidosCliente varchar(50), in NITClientes varchar(10),in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
 begin
	insert into Clientes(clienteID, nombresCliente, apellidosCliente, NITClientes,direccionCliente, telefonoCliente, correoCliente)
    values (clienteID, nombresCliente, apellidosCliente, NITClientes,direccionCliente, telefonoCliente, correoCliente);
 end $$
 delimiter ;
 
call sp_agregarClientes(1,'Devyn Orlando','Tubac Gomez','1234567890','12 Calle y 6 Avenida','12345678','dgomez@gmail.com');
call sp_agregarClientes(2,'Jose David','Gutierrez Lopez','9823751474','28 Calle y 7 Avenida','98541864','jdlopez1@gmail.com');
call sp_agregarClientes(3,'Carmen Maria','Morales Perez','12345678','5 Calle y 10 Av, zona 10 CG','98541864','maria.lopez@example.com');
call sp_agregarClientes(4,'Christopher Miguel','Hermandez Cap','123456789','3a Avenida, Zona 4, Quetzaltenango','55555678','juan.perez@example.com');
call sp_agregarClientes(5,'Carlos Moises','Rodirguez Carrillo','543216789','7a Avenida, Zona 15, Mixco','66443322','carlos.rodriguez@example.com');
call sp_agregarClientes(6,'Ana Lucia','Campos Herrera','89076243','10a Calle, Zona 1, Escuintla','09812345','anaCH@example.com');
 
delimiter $$
 create procedure sp_listarClientes()
 begin
	select * from Clientes;
 end $$
 delimiter ;
 
 call sp_listarClientes();
 
delimiter $$
create procedure sp_buscarClientes(in clienteID int)
 begin
	select * from Clientes where clienteID = clienteID;
 end $$
 delimiter ;
 
 call sp_buscarClientes(1);

delimiter $$
 create procedure sp_actualizarClientes(in clienteID int, in nombresCliente varchar(50), in apellidosCliente varchar(50),in NITClientes varchar(10), in direccionCliente varchar(150), telefonoCliente varchar(8), in correoCliente varchar(45))
 begin
	update Clientes
    set
        Clientes.nombresCliente = nombresCliente,
        Clientes.apellidosCliente = apellidosCliente,
        Clientes.NITClientes = NITClientes,
        Clientes.direccionCliente = direccionCliente,
        Clientes.telefonoCliente = telefonoCliente,
        Clientes.correoCliente = correoCliente
	where
		Clientes.clienteID = clienteID;
 end $$
 delimiter ;
 
delimiter $$
create procedure sp_eliminarClientes(in clienteID int)
begin
	delete from Clientes where Clientes.clienteID = clienteID;
end $$
delimiter ;

-- ---------------------------  Compras --------------------------- 

delimiter $$
create procedure sp_agregarCompras(in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
begin
	insert into Compras(numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    values(numeroDocumento, fechaDocumento, descripcion, totalDocumento);
end $$
delimiter ;

call sp_agregarCompras(1,'2024-09-09','Leche Kellogg',0.0);
call sp_agregarCompras(2,'2024-06-12','Yogur Danone',0.0);
call sp_agregarCompras(3,'2024-06-16','Jabón Dove',0.0);
call sp_agregarCompras(4,'2024-06-16','Crema Nivea',0.0);
call sp_agregarCompras(5,'2024-06-19','Pizza congelada California',0.0);
call sp_agregarCompras(6,'2024-06-22','Pan integral Bimbo',0.0);
call sp_agregarCompras(7,'2024-06-22','Pollo fresco Tyson',0.0);
call sp_agregarCompras(8,'2024-06-22','Manzanas Granny Smith',0.0);
call sp_agregarCompras(9,'2024-06-22','Agua Purificada Dasani ',0.0);
call sp_agregarCompras(10,'2024-06-25','Papas fritas Lays',0.0);
call sp_agregarCompras(11,'2024-06-28','Alimento para perros Royal Canin',0.0);
call sp_agregarCompras(12,'2024-06-28','Avena Quaker Oats',0.0);
call sp_agregarCompras(13,'2024-06-30','Lechugas frescas',0.0);
call sp_agregarCompras(14,'2024-06-29','Shampoo Head & Shoulders',0.0);
call sp_agregarCompras(15,'2024-06-01','Jugo de naranja Tropicana',0.0);

delimiter $$
create procedure sp_listarCompras()
begin
	select * from Compras;
end $$
delimiter ;

call sp_listarCompras();

delimiter $$
create procedure sp_buscarCompras(in numeroDocumento int)
begin
	select * from Compras where numeroDocumento = numeroDocumento;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarCompras(in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
begin
	update Compras
    set
		Compras.fechaDocumento = fechaDocumento,
        Compras.descripcion = descripcion,
        Compras.totalDocumento = totalDocumento
	where
		Compras.numeroDocumento = numeroDocumento;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarCompras(in numeroDocumento int)
begin
	delete from Compras where Compras.numeroDocumento = numeroDocumento;
end $$
delimiter ;

-- --------------------------- Proveedores --------------------------- 

delimiter $$
create procedure sp_agregarProveedores(in codigoProveedor int, in NITProvedor varchar(10), in nombresProveedor varchar(60), in apellidosProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(100), in paginaWeb varchar(50))
begin
	insert into Proveedores(codigoProveedor, NITProvedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (codigoProveedor, NITProvedor, nombresProveedor, apellidosProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
end $$
delimiter ;

call sp_agregarProveedores(1,'1234567890','Devyn','Gomez','11 Calle y 5 Avenida','Suministros Comerciales del Sur S.A. de C.V.','1234567','www.ComercioSur');
call sp_agregarProveedores(2,'789013321','Juan Carlos','Garcia Perez','25 Calle, Zona 6, Ciudad de Guatemala','Distribuidora del Valle S.A.','Alfonso Herrera','www.distribuidoradelvalle.com.gt');
call sp_agregarProveedores(3,'6543210987','Maria Elena','Lopez Martinez','8 Avenida, Zona 4, Quetzaltenango','Suministros Industriales S.A.','María Elena López Martínez','www.suministrosindustriales.com.gt');
call sp_agregarProveedores(4,'3210987654','Carlos Alberto','Rodriguez Gomez','12 Calle, Zona 1, Escuintla','Comercializadora Maya S.A.','Carlos Alberto','Carlos Alberto Rodríguez Gómez');
call sp_agregarProveedores(5,'431726321','Laura Sofia','Gonzales Hernandez','30 Avenida, Zona 15, Mixco','Importadora Castillo S.A.','Laura Sofía González Hernández','www.importadoracastillo.com.gt');
call sp_agregarProveedores(6,'678901','Pedro Jose','Perez Diaz','5a Calle, Zona 20, San Marcos','Exportaciones del Sur S.A.','Diego Morales','www.exportacionesdelsur.com.gt');

delimiter $$
create procedure sp_listarProveedores()
begin
	select * from Proveedores;
end $$
delimiter ;

call sp_listarProveedores();

delimiter $$
create procedure sp_buscarProveedores(in codigoProveedor int)
begin
	select * from Proveedores where codigoProveedor = codigoProveedor;
end $$
delimiter ;

call sp_buscarProveedores(1);

delimiter $$
create procedure sp_actualizarProveedores(in codigoProveedor int, in NITProvedor varchar(10), in nombresProveedor varchar(60), in apellidosProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(100), in paginaWeb varchar(50))
begin
	update Proveedores
    set
		NITProvedor = NITProvedor,
        nombresProveedor = nombresProveedor,
        apellidosProveedor = apellidosProveedor,
        direccionProveedor = direccionProveedor,
        razonSocial = razonSocial,
        contactoPrincipal = contactoPrincipal,
        paginaWeb = paginaWeb
	where
		Proveedores.codigoProveedor = codigoProveedor;
end $$	
delimiter ;

delimiter $$
create procedure sp_eliminarProveedores(in codigoProveedor int)
begin
	delete from Proveedores where Proveedores.codigoProveedor = codigoProveedor;
end $$
delimiter ;

-- --------------------------- Tipo Producto --------------------------- 

delimiter $$
create procedure sp_agregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
begin 
	insert into TipoProducto(codigoTipoProducto, descripcion)
    values(codigoTipoProducto, descripcion);
end $$
delimiter ;

call sp_agregarTipoProducto(1,'Cereales');
call sp_agregarTipoProducto(2,'Productos lácteos');
call sp_agregarTipoProducto(3,'Artículos de limpieza');
call sp_agregarTipoProducto(4,'Cuidado Personal');
call sp_agregarTipoProducto(5,'Congelados');
call sp_agregarTipoProducto(6,'Productos de Panadería');
call sp_agregarTipoProducto(7,'Frutas y Verduras');
call sp_agregarTipoProducto(8,'Carnes');
call sp_agregarTipoProducto(9,'Bebidas');
call sp_agregarTipoProducto(10,'Snaks');
call sp_agregarTipoProducto(11,'Mascotas');
call sp_agregarTipoProducto(12,'Enlatados');

delimiter $$
create procedure sp_listarTipoProducto()
begin 
	select * from TipoProducto;
end $$
delimiter ;

call sp_listarTipoProducto();

delimiter $$
create procedure sp_buscarTipoProducto(in codigoTipoProducto int)
begin 
	select * from TipoProducto where codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;

call sp_buscarTipoProducto(1);

delimiter $$
create procedure sp_actualizarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
begin 
	update TipoProducto
    set
		descripcion = descripcion
	where 
		TipoProducto.codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarTipoProducto(in codigoTipoProducto int)
begin 
	delete from TipoProducto where TipoProducto.codigoTipoProducto = codigoTipoProducto;
end $$
delimiter ;


-- ---------------------------  Empleados --------------------------- 

delimiter $$
create procedure sp_agregarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
begin 
	insert into Empleados(codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
    values (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
end $$
delimiter ;

call sp_agregarEmpleados(1,'Josue Miguel','Velazquez Ochoa',6500.0,'10 Calle y 10 Avenida','Mañana',2);
call sp_agregarEmpleados(2,'Juan Pablo', 'Gomez Perez', 5000.0, '11 Calle y 9 Avenida Ciudad de Guatemala', 'Mañana',1);
call sp_agregarEmpleados(3,'Maria Elena', 'Hernandez Gonzalez', 6000, 'Zona 10, Guatemala', 'Tarde',4);
call sp_agregarEmpleados(4,'Pedro Antonio', 'Lopez Rodriguez', 5500, 'Mixco, Guatemala', 'Noche',6);
call sp_agregarEmpleados(5,'Ana Sofia', 'Martinez Ramirez', 5200, 'Villa Nueva, Guatemala', 'Mañana',3);
call sp_agregarEmpleados(6,'Luis Alejandro', 'Rodriguez Garcia', 4800, 'Zona 4, Guatemala', 'Tarde',5);

delimiter $$
create procedure sp_listarEmpleados()
begin 
	select * from Empleados;
end $$
delimiter ;

call sp_listarEmpleados();

delimiter $$
create procedure sp_buscarEmpleados(in codigoEmpleado int)
begin 
	select * from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

call sp_buscarEmpleados(1);

delimiter $$
create procedure sp_actualizarEmpleados(in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in codigoCargoEmpleado int)
begin 
	update Empleados
    set	
		Empleados.nombresEmpleado = nombresEmpleado,
		Empleados.apellidosEmpleado = apellidosEmpleado,
        Empleados.sueldo = sueldo,
        Empleados.direccion = direccion,
        Empleados.turno = Empleados.turno,
        Empleados.codigoCargoEmpleado = codigoCargoEmpleado
	where
		Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarEmpleados(in codigoEmpleado int)
begin 
	delete from Empleados where Empleados.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

-- ---------------------------  Email Proveedor --------------------------- 

delimiter $$
create procedure sp_agregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
begin
	insert into EmailProveedor(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    values(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
end $$
delimiter ;

call sp_agregarEmailProveedor(1,'info@meatmaster.com','Proveedor de carne fresca',2);
call sp_agregarEmailProveedor(2,'sales@bestautoparts.com','Consultas y Pedidos',1);
call sp_agregarEmailProveedor(3,'orders@dairydelight.com','Proveedor de productos lácteos',3);
call sp_agregarEmailProveedor(4,'sales@snackmasterinc.com','Proveedor de bocadillos y alimentos envasados',6);
call sp_agregarEmailProveedor(5,'info@beverageblast.com','Proveedor de bebidas',4);
call sp_agregarEmailProveedor(6,'orders@frozenfoodsexpress.com','Proveedor de alimentos congelados',5);


delimiter $$
create procedure sp_listarEmailProveedor()
begin
	select * from EmailProveedor;
end $$
delimiter ;

call sp_listarEmailProveedor;

delimiter $$
create procedure sp_buscarEmailProveedor(in codigoEmailProveedor int)
begin
	select*from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

call sp_buscarEmailProveedor(1);

delimiter $$
create procedure sp_actualizarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)
begin
	update EmailProveedor
	set
		EmailProveedor.emailProveedor = emailProveedor,
        EmailProveedor.descripcion = descripcion,
        EmailProveedor.codigoProveedor = codigoProveedor
	where
		EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarEmailProveedor(in codigoEmailProveedor int)
begin
	delete from EmailProveedor where EmailProveedor.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

-- --------------------------- Telefono Proveedor --------------------------- 

delimiter $$
create procedure sp_agregarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
begin
	insert into TelefonoProveedor(codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
end $$
delimiter ;

call sp_agregarTelefonoProveedor(1,'12345678','87654321','Oficinas Centrales',1);
call sp_agregarTelefonoProveedor(2,'04341431','11111222','Horario de 5Am a 3pm',2);
call sp_agregarTelefonoProveedor(3,'54435345','98766543','Ventas',3);
call sp_agregarTelefonoProveedor(4,'65264363','65252657','Atencion al Cliente',4);
call sp_agregarTelefonoProveedor(5,'87423763','12343245','Reclamos y consultas',5);
call sp_agregarTelefonoProveedor(6,'54542252','65442432','Horario de 8am a 2pm',6);

delimiter $$
create procedure sp_listarTelefonoProveedor()
begin
	select * from TelefonoProveedor;
end $$
delimiter ;

call sp_listarTelefonoProveedor;

delimiter $$
create procedure sp_buscarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
	select * from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

call sp_buscarTelefonoProveedor(1);

delimiter $$
create procedure sp_actualizarTelefonoProveedor(in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
begin
	update TelefonoProveedor
    set
		TelefonoProveedor.numeroPrincipal = numeroPrincipal,
        TelefonoProveedor.numeroSecundario = numeroSecundario, 
        TelefonoProveedor.observaciones = observaciones,
        TelefonoProveedor.codigoProveedor = codigoProveedor
	where 
		TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
	delete from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

-- --------------------------- Productos --------------------------- 

delimiter $$
create procedure sp_agregarProductos(in codigoProducto varchar(15),in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in existencia int(11), in codigoTipoProducto int(11), in codigoProveedor int)
begin
	insert into Productos(codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,codigoTipoProducto,codigoProveedor)
    values (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,existencia,codigoTipoProducto,codigoProveedor);
end $$
delimiter ;

call sp_agregarProductos('PD001','Leche entera Lala 1L',0.00,0.00,0.00,50,2,6);
call sp_agregarProductos('PD002','Arroz blanco grano largo 1kg',0.00,0.00,0.00,134,1,2);
call sp_agregarProductos('PD003','Atún en lata Calvo al natural 200g',0.00,0.00,0.00,90,12,3);
call sp_agregarProductos('PD004','Kelloggs',0.00,0.00,0.00,90,1,4);
call sp_agregarProductos('PD005','Yogur Danone ',0.00,0.00,0.00,50,1,6);
call sp_agregarProductos('PD006','Jabón Dove',0.00,0.00,0.00,100,3,5);
call sp_agregarProductos('PD007','Crema Nivea',0.00,0.00,0.00,50,4,1);
call sp_agregarProductos('PD008','Pizza congelada California',0.00,0.00,0.00,24,5,2);
call sp_agregarProductos('PD009','Pan integral Bimbo',0.00,0.00,0.00,63,6,1);
call sp_agregarProductos('PD0010','Manzanas Granny Smith',0.00,0.00,0.00,200,7,4);
call sp_agregarProductos('PD0011','Pollo fresco Tyson',0.00,0.00,0.00,14,8,5);
call sp_agregarProductos('PD0012','Agua Purificada Dasani',0.00,0.00,0.00,43,9,6);
call sp_agregarProductos('PD0013','Papas fritas Lays',0.00,0.00,0.00,50,10,3);
call sp_agregarProductos('PD0014','Alimento para perros Royal Canin',0.00,0.00,0.00,32,11,2);
call sp_agregarProductos('PD0015','Avena Quaker Oats',0.00,0.00,0.00,78,1,5);
call sp_agregarProductos('PD0016','Lechugas frescas',0.00,0.00,0.00,80,7,3);
call sp_agregarProductos('PD0017','Shampoo Head & Shoulders',0.00,0.00,0.00,52,4,4);
call sp_agregarProductos('PD0018','Jugo de naranja Tropicana',0.00,0.00,0.00,22,9,2);

delimiter $$
create procedure sp_listarProductos()
begin 
	select * from Productos; 
end $$
delimiter ;

call sp_listarProductos();

delimiter $$
create procedure sp_buscarProductos(in codigoProducto varchar(15))
begin
	select * from Productos where productos.codigoProducto = codigoProducto;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarProductos(in codigoProducto varchar(15),in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in existencia int(11), in codigoTipoProducto int(11), in codigoProveedor int)
begin
	update productos
    set
		productos.descripcionProducto = descripcionProducto,
		productos.precioUnitario = precioUnitario,
        productos.precioDocena = precioDocena,
        productos.precioMayor = precioMayor,
        productos.existencia = existencia,
        productos.codigoTipoProducto = codigoTipoProducto,
        productos.codigoProveedor = codigoProveedor
	where
		productos.codigoProducto = codigoProducto;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarProductos(in codigoProducto varchar(45))
begin 
	delete from Productos where productos.codigoProducto = codigoProducto;
end $$
delimiter ;

-- --------------------------- Detalle Compra --------------------------- 

delimiter $$
create procedure sp_agregarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	insert into DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento)
    values (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento);
end $$
delimiter ;

call sp_agregarDetalleCompra(1,14.0,3,'PD001',3);
call sp_agregarDetalleCompra(2,24.0,7,'PD002',5);
call sp_agregarDetalleCompra(3,3.5,10,'PD003',2);
call sp_agregarDetalleCompra(4,8.66,15,'PD004',4);
call sp_agregarDetalleCompra(5,10.0,2,'PD005',6);
call sp_agregarDetalleCompra(6,20.0,54,'PD006',1);
call sp_agregarDetalleCompra(7,22.0,3,'PD007',7);
call sp_agregarDetalleCompra(8,13.0,5,'PD008',8);
call sp_agregarDetalleCompra(9,54.5,13,'PD009',8);
call sp_agregarDetalleCompra(10,16.0,4,'PD0010',9);
call sp_agregarDetalleCompra(11,22.0,8,'PD0011',10);
call sp_agregarDetalleCompra(12,26.0,7,'PD0012',11);
call sp_agregarDetalleCompra(13,12.0,6,'PD0013',12);
call sp_agregarDetalleCompra(14,25.0,5,'PD0014',13);
call sp_agregarDetalleCompra(15,43.0,10,'PD0015',14);


delimiter $$
create procedure sp_listarDetalleCompra()
begin
	select * from DetalleCompra;
end $$
delimiter ;

call sp_listarDetalleCompra();

delimiter $$
create procedure sp_buscarDetalleCompra(in codigoDetalleCompra int)
begin
	select * from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	update DetalleCompra
    set
		DetalleCompra.costoUnitario = costoUnitario,
        DetalleCompra.cantidad = cantidad,
        DetalleCompra.codigoProducto = codigoProducto,
        DetalleCompra.numeroDocumento = numeroDocumento
	where
		DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarDetalleCompra(in codigoDetalleCompra int)
begin
	delete from DetalleCompra where DetalleCompra.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

-- ---------------------------  Factura --------------------------- 

delimiter $$
create procedure sp_agregarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in clienteID int, in codigoEmpleado int)
begin
	insert into Factura(numeroFactura,estado,totalFactura,fechaFactura,clienteID,codigoEmpleado)
    values (numeroFactura,estado,totalFactura,fechaFactura,clienteID,codigoEmpleado);
end $$
delimiter ;

call sp_agregarFactura(1,'Pagada',0.0,'2024-05-10',6,1);
call sp_agregarFactura(2,'Pagada',0.0,'2024-05-10',2,5);
call sp_agregarFactura(3,'Pagada',0.0,'2024-06-02',4,3);
call sp_agregarFactura(4,'Pagada',0.0,'2024-04-15',4,3);
call sp_agregarFactura(5,'Pagada',0.0,'2024-06-07',2,5);
call sp_agregarFactura(6,'Pagada',0.0,'2024-05-02',6,1);

delimiter $$
create procedure sp_listarFactura()
begin
	select * from Factura;
end $$
delimiter ;

delimiter $$
create procedure sp_buscarFactura(in numeroFactura int)
begin
	select * from Factura where Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

call sp_buscarFactura(1);

delimiter $$
create procedure sp_actualizarFactura(in numeroFactura int, in estado varchar(50), in totalFactura decimal(10,2), in fechaFactura date,in clienteID int, in codigoEmpleado int)
begin
	update Factura
    set
		Factura.estado = estado,
        Factura.totalFactura = totalFactura,
        Factura.fechaFactura = fechaFactura,
        Factura.clienteID = clienteID,
        Factura.codigoEmpleado = codigoEmpleado
	where
		Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarFactura(in numeroFactura int)
begin
	delete from Factura where Factura.numeroFactura = numeroFactura;
end $$
delimiter ;

-- ---------------------------  Detalle Factura --------------------------- 

delimiter $$
create procedure sp_agregarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
begin
	insert into DetalleFactura(codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    values (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto);
end $$
delimiter ;

call sp_agregarDetalleFactura(1,0.00,6,3,'PD004');
call sp_agregarDetalleFactura(2,0.00,4,5,'PD002');
call sp_agregarDetalleFactura(3,0.00,1,2,'PD005');
call sp_agregarDetalleFactura(4,0.00,2,4,'PD002');
call sp_agregarDetalleFactura(5,0.00,5,1,'PD003');
call sp_agregarDetalleFactura(6,0.00,3,6,'PD006');
call sp_agregarDetalleFactura(7,0.00,3,6,'PD001');
call sp_agregarDetalleFactura(8,0.00,5,6,'PD004');

delimiter $$
create procedure sp_listarDetallesFacturas()
begin
	select * from DetalleFactura;
end $$
delimiter ;

call sp_listarDetallesFacturas();

delimiter $$
create procedure sp_buscarDetalleFactura(in codigoDetalleFactura int)
begin 
	select * from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;


delimiter $$
create procedure sp_actualizarDetalleFactura(in codigoDetalleFactura int, in precioUnitario decimal(10,2), in cantidad int, in numeroFactura int, in codigoProducto varchar(15))
begin
	update DetalleFactura
		set
			DetalleFactura.precioUnitario = precioUnitario,
            DetalleFactura.cantidad = cantidad,
            DetalleFactura.numeroFactura = numeroFactura,
            DetalleFactura.codigoProducto = codigoProducto
		where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarDetalleFactura(in codigoDetalleFactura int)
begin
	delete from DetalleFactura where DetalleFactura.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

delimiter $$
create trigger tr_PrecioProductos_After_Insert
after insert on DetalleCompra
for each row 
begin
	declare total decimal(10,2);
    declare cantidad int;
    
    set total = new.costoUnitario * new.cantidad;

	update Productos
	set precioUnitario = total * 0.40,
		precioDocena  = total * 0.35 * 12,
        precioMayor = total * 0.25
    where Productos.codigoProducto = new.codigoProducto;
    
	update Productos
        set Productos.existencia = Productos.existencia - new.cantidad
	where Productos.codigoProducto = new.codigoProducto;

end $$
delimiter ;

delimiter $$
create trigger tr_TotalDocumento_After_Insert
after insert on DetalleCompra
for each row
begin
    declare total decimal(10,2);
    
    select sum(costoUnitario * cantidad) into total from DetalleCompra 
    where numeroDocumento = NEW.numeroDocumento;
    
    update Compras 
		set totalDocumento = total 
	where numeroDocumento = NEW.numeroDocumento;
end $$
delimiter ;

delimiter $$
create trigger tr_PrecioUnitario_After_Upd
after insert on DetalleCompra
for each row
begin

	declare precioP decimal(10,2);
    
    set precioP = (select precioUnitario from Productos where codigoProducto = new.codigoProducto);
    
    update DetalleFactura
    set DetalleFactura.precioUnitario = precioP
    where DetalleFactura.codigoProducto = NEW.codigoProducto;
end $$
delimiter ;

delimiter $$
create trigger tr_TotalFactura_Aftr_U
after update on DetalleFactura
for each row
begin
	declare totalFactura decimal(10,2);
    
    select sum(precioUnitario * cantidad) into totalFactura from DetalleFactura
    where numeroFactura = new.numeroFactura;
    
    update Factura
		set Factura.totalFactura = totalFactura
	where Factura.numeroFactura = new.numeroFactura;
end $$
delimiter ;

create view vw_Productos as
select Productos.codigoProducto, Productos.precioUnitario, Productos.precioDocena, Productos.precioMayor, Productos.existencia, TipoProducto.Descripcion, Proveedores.razonSocial
from Productos
inner join TipoProducto on Productos.codigoTipoProducto = TipoProducto.codigoTipoProducto
inner join Proveedores on Productos.codigoProveedor = Proveedores.codigoProveedor;

select * from vw_Productos;

create view vw_Proveedores as
select Proveedores.codigoProveedor, Proveedores.NITProvedor, Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal, TelefonoProveedor.numeroPrincipal, EmailProveedor.emailProveedor
from Proveedores
inner join TelefonoProveedor on Proveedores.codigoProveedor = TelefonoProveedor.codigoProveedor
inner join EmailProveedor on Proveedores.codigoProveedor = EmailProveedor.codigoProveedor;

select * from vw_Proveedores;
select * from productos;

create view vw_vistaProductos as
select Productos.codigoProducto,Productos.descripcionProducto, Productos.precioUnitario, Productos.existencia,TipoProducto.Descripcion
from Productos
inner join TipoProducto on Productos.codigoTipoProducto = TipoProducto.codigoTipoProducto;

select * from vw_vistaProductos where vw_vistaProductos.descripcionProducto like 't%';

select codigoProducto, sum(cantidad) as totalCompra from DetalleCompra group by codigoProducto;

select fechaDocumento, sum(totalDocumento) as totalCompra from Compras group by fechaDocumento;

create view vw_listaProductos as
select Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario
from Productos;

select * from vw_listaProductos;

select * from DetalleFactura
	inner join Factura on DetalleFactura.numeroFactura = Factura.numeroFactura
    inner join Clientes on Factura.clienteID = Clientes.clienteID
    inner join Productos on DetalleFactura.codigoProducto = Productos.codigoProducto
    where Factura.numeroFactura = 1;
    