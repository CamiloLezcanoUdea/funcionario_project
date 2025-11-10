USE recursos_humanos;

INSERT INTO funcionario (tipo_identificacion, numero_identificacion, nombres, apellidos, estado_civil, sexo, direccion, telefono, fecha_nacimiento)
VALUES
('CC','100200300','María','Gómez','Soltera','F','Calle 10 #20-30','3001234567','1985-04-12'),
('CC','100200301','Carlos','Pérez','Casado','M','Carrera 5 #10-40','3109876543','1978-09-01');

INSERT INTO grupo_familiar (funcionario_id, nombres, parentesco, fecha_nacimiento)
VALUES
(1,'Juan Gómez','Hijo','2010-06-15'),
(1,'Ana Gómez','Hija','2013-02-20'),
(2,'Luisa Pérez','Esposa','1980-12-01');

INSERT INTO formacion_academica (funcionario_id, universidad, nivel_estudio, titulo)
VALUES
(1,'Universidad Autónoma','Pregrado','Administración de Empresas'),
(2,'Universidad Nacional','Especialización','Gestión de la Salud');
