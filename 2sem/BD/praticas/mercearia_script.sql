use  empresa;

insert into tipo_cc (id,descricao) values (1,'Shopping Tradicional'),
                                                             (2,'Shopping Outlet'),
                                                            (3,'Shopping Temático'),
                                                            (4,'Shopping Rotativo'),
                                                            (5,'Shopping Grossista'),
                                                            (6,'Shopping Virtual');


insert into categoria (id,descricao) values (1,'Gerente de Loja'),
                                                            (2,'Segurança'),
                                                            (3,'Caixa de loja'),
                                                            (4,'Vendedor'),
                                                            (5,'Assistente Técnico'),
                                                            (6,'Comercial'),
                                                             (7,'Armazem'),
                                                            (8,'Auditor'),
                                                            (9,'Manutenção'),
                                                            (10,'Gestor');
              
 insert into funcionario (numero,nome,cpostal,nif,data_nascimento,ativo,id_categoria)
 values (1,'José Manuel Silva','4715-999','123456789','1990-10-01',1,10);
  insert into funcionario (numero,nome,cpostal,nif,data_nascimento,ativo,id_categoria,supervisor)
 values (2,'Carlos Manuel Abreu','4720-001','234567890','1990-10-01',1,1,1),
             (3,'Fernando Machado','4720-001','345678901','2000-09-01',1,1,1),
             (4,'Joaquim Diretor','4720-001','456789012','1989-09-01',1,9,1),
              (5,'António José Manuel Silva','4715-999','923456789','1980-10-01',1,6,1),
              (6,'Pedro Manuel Abreu Silva','4720-001','294567890','1990-10-01',1,6,1),
              (7,'Joaquim Fernando Santos','4728-001','348678901','1970-09-01',1,8,3),
              (8,'Manuel da Caixa','4728-001','956789012','1999-09-01',1,2,3),
              (9,'Pedro da Caixa','4728-001','996789012','1989-09-01',1,2,3),
              (10,'Paulo da Caixa','4728-001','999789012','1975-09-01',1,2,3),
              (11,'Maria da Caixa','4728-001','888789012','1979-09-01',1,2,3),
              (12,'Paula Seguraça','4728-001','777789012','1983-09-01',1,2,1),
              (13,'Manuel Segurança','4728-001','770789012','1980-09-01',1,2,12),
              (14,'Paula Mecanica','4728-001','776789012','1982-09-01',1,9,1),
              (15,'Pedro Mecanica','4728-001','666789012','1976-09-01',1,9,14),
              (16,'Joaquim Mecanica','4728-001','555789012','1978-09-01',1,9,14);
              
insert into centro_comercial (id,nif,nome,id_tipo_cc,diretor)
 values (1,'112233445','Centro Comercial do Norte',1,1),
              (2,'112233447','Centro Comercial do Lado',3,1); 

insert into ramo_atividade (id_ramo,ramo)
values (1,'Ramo Alimentar'),
            (2,'Vestuário Senhora'),
            (3,'Vestuário Masculino'),
            (4,'Restauração'),
            (5,'Vestuação criança');
            
insert into diretor_ramo(id_func,id_ramo,id_cc)
values (3,1,1),
             (3,4,1),
             (3,1,2),
             (2,3,1),
             (2,2,2),
             (4,2,1),
             (4,5,1),
             (4,5,2);