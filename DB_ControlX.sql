create database controlx;
use controlx;

CREATE TABLE fornecedor (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(64) DEFAULT NULL,
  cnpj varchar(15) DEFAULT NULL, 
  tel1 varchar(11) DEFAULT NULL,	
  tel2 varchar(11) DEFAULT NULL,
  cep varchar(10) DEFAULT NULL,
  num int(6) DEFAULT NULL,
  rua varchar(64) DEFAULT NULL,
  comp varchar(32) DEFAULT NULL,
  bairro varchar(64) DEFAULT NULL,
  cidade varchar(64) DEFAULT NULL,
  estado varchar(64) DEFAULT NULL,
  deleted_at date DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE categoria (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(64),
  deleted_at date,
  PRIMARY KEY (id)
 );

CREATE TABLE produtos (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(64) DEFAULT NULL,
  preco double DEFAULT NULL,
  qntd double DEFAULT NULL,
  tipoUn varchar(5),
  estoqueMin double DEFAULT 20,
  localPic varchar(256),
  idFornecedor int,
  idCategoria int,
  deleted_at date DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (idFornecedor) REFERENCES fornecedor (id),
  FOREIGN KEY (idCategoria) REFERENCES categoria (id)
 );

 CREATE TABLE usuario (
	id int NOT NULL AUTO_INCREMENT,
	nome varchar(64),
	cpf varchar(11),
	sexo varchar(1),
	dataNasc date,
	tel1 varchar(11),
	tel2 varchar(11),
	cep varchar(10),
	num int(6),
	rua varchar(64),
	comp varchar(32),
	bairro varchar(64),
	cidade varchar(64),
	estado varchar(64),
	cargo int,
	login varchar(32),
	senha varchar(32),
	deleted_at date DEFAULT NULL,
	localPic varchar(256),
	PRIMARY KEY (id)	
 );

 CREATE TABLE compras (
	id int NOT NULL AUTO_INCREMENT,
	idUsuario int NOT NULL,
	valor double,
	status int DEFAULT 0,
	dataCompra datetime,
	dataEntrega datetime,
	dataFinal datetime DEFAULT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (idUsuario) REFERENCES usuario (id)
);
 
 
 CREATE TABLE produtos_compra(
 	idCompra int NOT NULL,
	idProduto int NOT NULL,
	qtdProduto double,
	precoUnProduto double,
	PRIMARY KEY(idCompra,idProduto),
	FOREIGN KEY (idCompra) REFERENCES compras (id),
	FOREIGN KEY (idProduto) REFERENCES produtos (id)
);

 CREATE TABLE vendas (
	id int NOT NULL AUTO_INCREMENT,
	idUsuario int NOT NULL,
	valor double,
	dataVenda datetime,
	PRIMARY KEY (id),
	FOREIGN KEY (idUsuario) REFERENCES usuario (id)
);
 
 
 CREATE TABLE produtos_venda(
 	idVenda int NOT NULL,
	idProduto int NOT NULL,
	qtdProduto double DEFAULT 0,
	precoUnProduto double,
	PRIMARY KEY(idVenda,idProduto),
	FOREIGN KEY (idVenda) REFERENCES vendas (id),
	FOREIGN KEY (idProduto) REFERENCES produtos (id)
);


INSERT INTO usuario(nome, cpf, sexo, dataNasc, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado, cargo, login, senha) 
	values ('Gustavo Nunes','46760818806','M','1997-09-24','1633667070','0','13563340',666,'Rua Albino Triques','','Parque Santa Felícia Jardim','São Carlos','SP',0,'gnunesinfo','gnunesinfo');
	

INSERT INTO usuario(nome, cpf, sexo, dataNasc, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado, cargo, login, senha) 
	values ('Carlos Silva','44691492844','M','1996-12-06','1633661367','16982472564','13573059',882,'Rua Joaquim Garcia de Oliveira ','','Cidade Aracy ','São Carlos','SP',0,'csilva','csilva');
	
	
INSERT INTO usuario(nome, cpf, sexo, dataNasc, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado, cargo, login, senha) 
	values ('Administrador','31313131313','M','1990-10-21','1633667070','16988536978','23065190',562,'Rua Dezoito','','Paciência','Rio de Janeiro','RJ',0,'admin','admin');

INSERT INTO usuario(nome, cpf, sexo, dataNasc, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado, cargo, login, senha) 
	values ('Rochelle Fernandez','74598861067','F','1987-05-12','1633665897','0','72903088',12,'Quadra Quadra 169','','Parque Estrela Dalva XVII','Santo Antônio do Descoberto','GO',3,'caixa','caixa');
	
INSERT INTO usuario(nome, cpf, sexo, dataNasc, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado, cargo, login, senha) 
	values ('Alice Amanda dos Santos','26871795154','F','1987-06-05','6326791147','63986377616','77060828',1258,'Alameda Mato Grosso','','Distrito Industrial de Taquaralto','Palmas','TO',1,'supervisor','supervisor');
	
INSERT INTO usuario(nome, cpf, sexo, dataNasc, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado, cargo, login, senha) 
	values ('Carolina Sophia Mirella Souza','79868891284','F','1992-11-22','6525239048','65995260440','78008135',778,'Rua Professor João Félix','','Baú','Cuiabá','MT',2,'almox','almox');

	

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Specx Eletrônica Ltda','02948980000184','1339025792','0','11349285','917','Rua Antonio Pacífico','','Conjunto Residencial Humaitá','São Vicente','SP');

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Laura Pães e Doces Ltda','98250971000194','1635030991','16989944942','14093536','646','Rua Mario Maurim','','Parque Residencial Cândido Portinari','Ribeirão Preto','SP');

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Alvorecer Comercio de Bebidas Ltda','53211931000103','1138922225','11988929113','03286040','824','Rua José Loureiro das Neves','','Vila Cleonice','São Paulo','SP');

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Cutigi Frios','50899158000113','1939150257','19982701726','13087536','110','Rua Antero Patrício Silvestre','','Parque Rural Fazenda Santa Cândida','Campinas','SP');

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Tati Salgados & Pastéis','48338778000131','1135270579','11992705672','05831115','997','Travessa Dona Dora','','Chácara Santana','São Paulo','SP');	
	
INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Depósito de Bebidas','54946336000170','1738036418','17987241975','15041522','931','Avenida Aguinaldo Rossini','','Jardim Bianco','São José do Rio Preto','SP');	
	
INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Kabum Eletrônicos','01624509000178','17993755105','17987241975','15500310','837','Rua Benjamin Constant','','Jardim Bom Clima','Votuporanga','SP');
	
INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Livraria Kzar','43712077000105','1138673753','0','06707200','891','Rua Maestro Manoel Vitorino dos Santos','','Granja Viana II','Cotia','SP');

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Tem-de-Tudo','43712077789157','1137873753','0','06707200','1002','Rua Maestro Manoel Vitorino dos Santos','','Granja Viana I','Cotia','SP');

INSERT INTO fornecedor(nome, cnpj, tel1, tel2, cep, num, rua, comp, bairro, cidade, estado) 
	values ('Jô Modas e Calçados','52042077789157','1136873356','0','08599660','102','Rua Beta','','Una','Itaquaquecetuba','SP');
	
INSERT INTO categoria(nome) values ('Doces'); 
INSERT INTO categoria(nome) values ('Salgados');
INSERT INTO categoria(nome) values ('Eletrônicos');
INSERT INTO categoria(nome) values ('Bebidas');
INSERT INTO categoria(nome) values ('Acessórios');	
INSERT INTO categoria(nome) values ('Roupas');
INSERT INTO categoria(nome) values ('Sapatos');
INSERT INTO categoria(nome) values ('Escolar');
INSERT INTO categoria(nome) values ('Encanamentos');
INSERT INTO categoria(nome) values ('Cabos');
INSERT INTO categoria(nome) values ('Construção');
INSERT INTO categoria(nome) values ('Cosméticos');
INSERT INTO categoria(nome) values ('Decoração');
INSERT INTO categoria(nome) values ('Brinquedos');
INSERT INTO categoria(nome) values ('Peças Automotivas');
INSERT INTO categoria(nome) values ('Móveis');
INSERT INTO categoria(nome) values ('Animais');
INSERT INTO categoria(nome) values ('Utensílios Domésticos');
INSERT INTO categoria(nome) VALUES ('Cinema');
INSERT INTO categoria(nome) VALUES ('Bebê');

INSERT INTO categoria(nome) VALUES ('Frios');
INSERT INTO categoria(nome) VALUES ('Limpeza');

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Pastel de Frango', 3.5, 10,'UN', 5, 2);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Mouse Razer Abyssus',157.50, 55,'UN', 7, 3);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Requeijão',3.99, 102,'UN', 4, 21);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Linguiça Toscana Perdigão', 8.00, 45.52,'KG', 4, 21 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('As Crônicas de Nárnia',29.90, 32,'UN', 8, 8);

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Teclado Multilaser Warrior', 169.90, 23,'UN', 1, 3 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Cabo Coaxial 3/4 pol',12.49, 2500,'M', 1, 10);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Budweiser Lata 300ml', 4.49, 256,'UN', 3, 4 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Casa de Cachorro Média',75.50, 12,'UN', 9, 17);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Carrinho Hotwheels', 5.00, 26,'UN', 9, 14);

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Tenis Nike Chavoso',349.90, 6,'UN', 10, 7);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Lapiseira 0.7 BIC', 3.99, 56,'UN', 8, 8 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Pão Francês',6.99, 150,'KG', 2, 2);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Corote Flavors', 1.99, 53,'UN', 6, 4 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Super Saia Jeans',99.90, 14,'UN', 9, 6);


INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Cotovelo 5/8 Tigre', 1.50, 78,'UN', 9, 9 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Requeijão',3.99, 102,'UN', 4, 21);
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Cera Automotiva', 19.90, 14,'UN', 9, 15 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Leite Frimeza', 2.99, 365,'UN', 2, 4 );

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Detergente YPÊ neutro', 3.20, 64,'UN', 9, 22 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Coca-Cola 2L', 5.50, 39,'UN', 6, 4 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Alcatra', 19.90, 85.5,'KG', 4, 21 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Lâ de Aço Assolan', 1.69, 68,'UN', 9, 15 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Desodorante Rexona Men 90g', 8.99, 4,'UN', 9, 12 );

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Porta Retratos 210x197', 14.99, 23,'UN', 9, 13 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Coxinha de Frango com Catupiry', 4.99, 8,'UN', 5, 2 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Batata Doce', 13.00, 13,'UN', 2, 1 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Roteador TP-LINK 100MB/s', 69.90, 4,'UN', 1, 3 );

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Mesa para Computador Mogno', 250.00, 2,'UN', 9, 16 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Fralda Pampers', 49.90, 65,'UN', 9, 20 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Óculos 3D', 14.99, 3,'UN', 9, 19 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Iogurte Caseiro', 6.00, 36.5,'L', 2, 4);

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Óculos Rayban Chave', 300.00, 9,'UN', 9, 5 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Carpete de Boas Vindas', 29.90, 5,'UN', 9, 5 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('CD The Offspring', 39.90, 1,'UN', 9, 3 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Cabo HDMI/VGA', 17.50, 15,'UN', 7, 10);

INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Monitor LED LG 144Hz', 1499.00, 2,'UN', 7, 3 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Enxada Padrão', 79.90, 4,'UN', 9, 11 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Sansung Galaxy XYZ', 4999.90, 3,'UN', 1, 3 );
INSERT INTO produtos(nome, preco, qntd, tipoUn, idFornecedor, idCategoria) values ('Cimento Padrão', 18.90, 54.7,'KG', 9, 11);
