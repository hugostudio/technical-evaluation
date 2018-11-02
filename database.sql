
CREATE TABLE categoria
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome varchar(255)
);
INSERT INTO categoria (id, nome) VALUES (1, 'Informática');
INSERT INTO categoria (id, nome) VALUES (2, 'Escritório');
INSERT INTO categoria (id, nome) VALUES (3, 'Cama mesa  e banho');
INSERT INTO categoria (id, nome) VALUES (4, 'Bricolagem');
INSERT INTO categoria (id, nome) VALUES (5, 'Farmacia');
INSERT INTO categoria (id, nome) VALUES (6, 'Infantil');
INSERT INTO categoria (id, nome) VALUES (7, 'Ferramentas');
INSERT INTO categoria (id, nome) VALUES (8, 'Decoração');
INSERT INTO categoria (id, nome) VALUES (9, 'Jardinagem');
INSERT INTO categoria (id, nome) VALUES (10, 'Perfumaria');
CREATE TABLE cidade
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome varchar(255),
    estado_id int(11)
);
CREATE INDEX FKkworrwk40xj58kevvh3evi500 ON cidade (estado_id);
INSERT INTO cidade (id, nome, estado_id) VALUES (1, 'Uberlândia', 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (2, 'São Paulo', 2);
INSERT INTO cidade (id, nome, estado_id) VALUES (3, 'Campinas', 2);
CREATE TABLE cliente
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    cpf_ou_cnpj varchar(255),
    email varchar(255),
    nome varchar(255),
    tipo int(11)
);
INSERT INTO cliente (id, cpf_ou_cnpj, email, nome, tipo) VALUES (1, '36378912377', 'maria@gmail.com', 'Maria Silva', 1);
CREATE TABLE endereco
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    bairro varchar(255),
    cep varchar(255),
    complemento varchar(255),
    logradouro varchar(255),
    numero varchar(255),
    cidade_id int(11),
    cliente_id int(11)
);
CREATE INDEX FK8b1kcb3wucapb8dejshyn5fsx ON endereco (cidade_id);
CREATE INDEX FK8s7ivtl4foyhrfam9xqom73n9 ON endereco (cliente_id);
INSERT INTO endereco (id, bairro, cep, complemento, logradouro, numero, cidade_id, cliente_id) VALUES (1, 'Jardins', '38220834', 'Apto 30', 'Rua Flores', '300', 1, 1);
INSERT INTO endereco (id, bairro, cep, complemento, logradouro, numero, cidade_id, cliente_id) VALUES (2, 'Centro', '38777012', 'Sala 800', 'Avenida Matos', '105', 2, 1);
CREATE TABLE estado
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome varchar(255)
);
INSERT INTO estado (id, nome) VALUES (1, 'Minas Gerais');
INSERT INTO estado (id, nome) VALUES (2, 'São Paulo');
CREATE TABLE item_pedido
(
    desconto double,
    preco double,
    quantidade int(11),
    pedido_id int(11) NOT NULL,
    produto_id int(11) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (pedido_id, produto_id)
);
CREATE INDEX FKtk55mn6d6bvl5h0no5uagi3sf ON item_pedido (produto_id);
INSERT INTO item_pedido (desconto, preco, quantidade, pedido_id, produto_id) VALUES (0, 2000, 1, 1, 1);
INSERT INTO item_pedido (desconto, preco, quantidade, pedido_id, produto_id) VALUES (0, 80, 2, 1, 3);
INSERT INTO item_pedido (desconto, preco, quantidade, pedido_id, produto_id) VALUES (100, 800, 1, 2, 2);
CREATE TABLE pagamento
(
    pedido_id int(11) PRIMARY KEY NOT NULL,
    estado int(11)
);
INSERT INTO pagamento (pedido_id, estado) VALUES (1, 2);
INSERT INTO pagamento (pedido_id, estado) VALUES (2, 1);
CREATE TABLE pagamento_com_boleto
(
    data_pagamento datetime,
    data_vencimento datetime,
    pedido_id int(11) PRIMARY KEY NOT NULL
);
INSERT INTO pagamento_com_boleto (data_pagamento, data_vencimento, pedido_id) VALUES (null, '2017-10-20 00:00:00', 2);
CREATE TABLE pagamento_com_cartao
(
    numero_de_parcelas int(11),
    pedido_id int(11) PRIMARY KEY NOT NULL
);
INSERT INTO pagamento_com_cartao (numero_de_parcelas, pedido_id) VALUES (6, 1);
CREATE TABLE pedido
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    instante datetime,
    cliente_id int(11),
    endereco_de_entrega_id int(11)
);
CREATE INDEX FK30s8j2ktpay6of18lbyqn3632 ON pedido (cliente_id);
CREATE INDEX FK1fihyy2fnocpuwc74674qmfkv ON pedido (endereco_de_entrega_id);
INSERT INTO pedido (id, instante, cliente_id, endereco_de_entrega_id) VALUES (1, '2017-09-30 10:32:00', 1, 1);
INSERT INTO pedido (id, instante, cliente_id, endereco_de_entrega_id) VALUES (2, '2017-10-14 13:35:00', 1, 2);
CREATE TABLE produto
(
    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome varchar(255),
    preco double
);
INSERT INTO produto (id, nome, preco) VALUES (1, 'Computador', 2000);
INSERT INTO produto (id, nome, preco) VALUES (2, 'Impressora', 800);
INSERT INTO produto (id, nome, preco) VALUES (3, 'Mouse', 80);
INSERT INTO produto (id, nome, preco) VALUES (4, 'Mesa de escritório', 300);
INSERT INTO produto (id, nome, preco) VALUES (5, 'Toalha', 50);
INSERT INTO produto (id, nome, preco) VALUES (6, 'Colcha', 200);
INSERT INTO produto (id, nome, preco) VALUES (7, 'TV true color', 1200);
INSERT INTO produto (id, nome, preco) VALUES (8, 'Roçadeira', 800);
INSERT INTO produto (id, nome, preco) VALUES (9, 'Abajour', 100);
INSERT INTO produto (id, nome, preco) VALUES (10, 'Pendente', 180);
INSERT INTO produto (id, nome, preco) VALUES (11, 'Shampoo', 90);
CREATE TABLE produto_categoria
(
    produto_id int(11) NOT NULL,
    categoria_id int(11) NOT NULL
);
CREATE INDEX FK1c0y58d3n6x3m6euv2j3h64vt ON produto_categoria (produto_id);
CREATE INDEX FKq3g33tp7xk2juh53fbw6y4y57 ON produto_categoria (categoria_id);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (1, 1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (1, 1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (1, 4);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2, 1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2, 2);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2, 1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2, 2);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2, 4);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (3, 1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (3, 1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (3, 4);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (4, 2);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (5, 3);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (6, 3);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (7, 4);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (8, 5);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (9, 6);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (10, 6);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (11, 7);
CREATE TABLE telefone
(
    cliente_id int(11) NOT NULL,
    telefones varchar(255)
);
CREATE INDEX FK8aafha0njkoyoe3kvrwsy3g8u ON telefone (cliente_id);
INSERT INTO telefone (cliente_id, telefones) VALUES (1, '27363323');
INSERT INTO telefone (cliente_id, telefones) VALUES (1, '93838393');