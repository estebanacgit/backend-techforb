
INSERT INTO `usuarios` (`enabled`, `username`, `password`, `apellido`, `email`, `nombre`) VALUES (true, 'estebanac', '123456', 'Acosta', 'estebanacosta994@gmail.com', 'Esteban');
INSERT INTO `usuarios` (`enabled`, `username`, `password`, `apellido`, `email`, `nombre`) VALUES (true, 'estebanaco', '1234756', 'Acosta2', 'este@gmail.com', 'Esteban2');

INSERT INTO `balances` (`disponibilidad_fondos`, `estado_cuenta`, `limite_cuenta`, `saldo_actual`, `fecha_actualizacion`, `usuario_id`, `moneda`) VALUES ('2500', true, '100000', '2500', NOW(), '1', 'USD');
INSERT INTO `balances` (`disponibilidad_fondos`, `estado_cuenta`, `limite_cuenta`, `saldo_actual`, `fecha_actualizacion`, `usuario_id`, `moneda`) VALUES ('2500', true, '100000', '2500', NOW(), '2', 'USD');

INSERT INTO `transacciones` (`monto_transferido`, `balance_id`, `fecha`, `id_destino`, `descripcion`, `tipo`) VALUES ('900', '1', NOW(), '2', 'Deposito Salario', 'Deposito');

INSERT INTO `tarjetas` (`fecha_vencimiento`, `nombre_tarjeta`, `numero_tarjeta`, `tipo_tarjeta`) VALUES ('2025-08-07', 'Master Card', '4478 5501 5559 7999', 'Debito');

INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Consulta tu saldo actual', 'Ver Balance', '/api/balance');
INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Transfiere dinero a otra cuenta', 'Realizar Transferencia', '/api/transferencia');
INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Ver todas las transacciones anteriores', 'Historial de Transacciones', '/api/transacciones');
INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Actualiza tus datos personales y preferencias de cuenta', 'Configuración de la Cuenta', '/api/configuracion-cuenta');
INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Configura notificaciones importantes', 'Alertas y Notificaciones', '/api/alertas');
INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Obtén ayuda y soporte', 'Ayuda y Soporte', '/ayuda');
INSERT INTO `menu_opciones` (`usuario_id`, `descripcion`, `nombre`, `ruta`) VALUES ('1', 'Salir de la aplicación', 'Cerrar Sesión', '/api/cerrar-sesione');