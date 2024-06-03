-- Base de Datos - KinalXpress
-- Devyn Orlando Tubac Gomez
-- Carne: 2020247
-- Codigo Tecnico: IN5BM

drop database if exists dbKinalXpress;

create database dbKinalXpress;

use dbKinalXpress;

set global time_zone = '-6:00';

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
    imagenProducto varchar(3),
    existencia int(11),
    codigoTipoProducto int,
    codigoProveedor int,
    primary key PK_Productos(codigoProducto),
    constraint FK_Productos_TipoProducto foreign key Productos(codigoTipoProducto)
		references TipoProducto (codigoTipoProducto) on delete cascade,
	constraint FK_Productos_Proveedor foreign key Productos(codigoProveedor)
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

call sp_agregarCargoEmpleado(1,'Vendedor','Vender');
call sp_agregarCargoEmpleado(2,'Cajero','Cobrar');

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

call sp_agregarCompras(1,'2020-09-09','Visa',10.00);

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

call sp_actualizarCompras(2,'2020-05-05','Hi',23.00);

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

call sp_agregarTipoProducto(1,'Gaseosas');
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

call sp_agregarEmpleados(1,'Pedro','Gomez','10.0','10 Calle y 10 Avenida','M',2);


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

call sp_actualizarEmpleados(1,'David','G',23.0,'29 calle','M',2);

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

call sp_agregarEmailProveedor(1,'dtubac@kinal','Guatemala',1);

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

call sp_agregarTelefonoProveedor(2,'12345678','87654321','502',1);

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

call sp_eliminarTelefonoProveedor(1);

-- --------------------------- Productos --------------------------- 

delimiter $$
create procedure sp_agregarProductos(in codigoProducto varchar(15),in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(3), in existencia int(11), in codigoTipoProducto int(11), in codigoProveedor int)
begin
	insert into Productos(codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor)
    values (codigoProducto,descripcionProducto,precioUnitario,precioDocena,precioMayor,imagenProducto,existencia,codigoTipoProducto,codigoProveedor);
end $$
delimiter ;

call sp_agregarProductos('PE5DM','Coca Cola',0.00,0.00,0.00,'pn',11,1,1);

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
create procedure sp_actualizarProductos(in codigoProducto varchar(15),in descripcionProducto varchar(45), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(3), in existencia int(11), in codigoTipoProducto int(11), in codigoProveedor int)
begin
	update productos
    set
		productos.descripcionProducto = descripcionProducto,
		productos.precioUnitario = precioUnitario,
        productos.precioDocena = precioDocena,
        productos.precioMayor = precioMayor,
        productos.imagenProducto = imagenProducto,
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

call sp_eliminarProductos(1);

-- --------------------------- Detalle Compra --------------------------- 

delimiter $$
create procedure sp_agregarDetalleCompra(in codigoDetalleCompra int, in costoUnitario decimal(10,2), in cantidad int, in codigoProducto varchar(15), in numeroDocumento int)
begin
	insert into DetalleCompra(codigoDetalleCompra, costoUnitario, cantidad, codigoProducto,numeroDocumento)
    values (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento);
end $$
delimiter ;

call sp_agregarDetalleCompra(2,2.0,5,'PE5DM',1);

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

call sp_agregarFactura(1,'Positivo',34.0,'2022-06-06',2,1);

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

call sp_agregarDetalleFactura(1,0.00,3,1,'PE5DM');
call sp_agregarDetalleFactura(2,2.00,3,1,'PE5DM');

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