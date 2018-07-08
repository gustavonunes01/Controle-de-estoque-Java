COMMIT -- >  SYNC -->  PUSH (<-- REPEAT)

<-------------------------------------------------------------------------------->

 -> Crystal Report: http://www.crystalreports.com/crystal-reports-visual-studio/
      - Digitar o e-mail e selecionar o país, clicar em Free Download;
      - Clicar no botão LARANJA na parte superior da tela e o arquivo será baixado.

 -> Crystal Report Essentials Version: 13.0.20.2399

<-------------------------------------------------------------------------------->




<-------------------------------------------------------------------------------->

 -> WebService Correios: https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl
 
      - Adicionar nova referencia.
      - Colocar o link da referencia, dar go.
      - Colocar o nome que deseja, ex: WSCorreios.
      - Chamar ela em uma função que dejar ou só no botão ou change text, chamar um " var ws = new WSCorreios.AtendeClienteClient(); "
      - Que isso retornara todos os campos, RUA, BAIRRO, CIDADE - ESTADO E COMPLEMENTO.

 -> SITE DE EXPLICAÇÃO http://www.andrealveslima.com.br/blog/index.php/2016/09/07/acessando-os-web-services-dos-correios-com-c-e-vb-net-consulta-de-ceps-e-precos/

<-------------------------------------------------------------------------------->

ControlX -> Control de Controle(Estoque, produtos, enfim, do estabelecimento) e X, como a incógnita, uma variável,
mostrando de que é possível utilizar esse Software em vários tipos de Estabelecimento.

- Sistema de controle de Estoque, Compra e Venda.


*------------------------------ PARA FAZER ------------------------------*

 - Criar uma base de dados correta para apresentação;
 - Aperfeiçoar a interface gráfica do software;*SEMI-FEITO*

------------------------------ F O R M S ------------------------------


------------------------------ EM TODOS OS FORMS ------------------------------

- Busca por nome não pode aceitar ' aspas simples para não bugar o SQL *FEITO*
- Quando trocar de tipo de busca(ID,NOME), apagar o textbox para não gerar conflitos com o banco *FEITO*

------------------------------  ESTOQUE ------------------------------*CONCLUIDO, TESTAR*

- Busca por Categoria não funcionando *FEITO*
- DGV com dados errados em UN e Categoria quando usa-se o filtro de BUSCA *FEITO*
- Arrumar as imagens dos produtos/ou remover*FEITO*
- Na hora de adicionar, o campo nome não aceita ' aspas simples(inverter com o campo busca) *FEITO*

------------------------------ FORNECEDORES ------------------------------*CONCLUIDO,TESTAR*

  - Adicionar filtro por ID*FEITO*
  - Configurar para o Filtro buscar no NOME e por CIDADE*FEITO*

------------------------------ COMPRAS ------------------------------

  - Habilitar modo TELA CHEIA e arrumar os 'Anchors'
  - Preencher o DGV com as ultimas compras realizadas OU Colocar algo mais útil no lugar *FEITO*
  - Botão Finalizar só pode estar habilitado caso tenha algo no DGV de Compras em Aguardo *FEITO*
  - Ao adicionar uma compra, os valores em R$ devem estar com sua devida máscara
  - Poder Visualizar os itens da compra ao clicar em uma compra não finalizada(DGV direito) *FEITO*
  - Notificar caso o Preço de Compra seja maior que o Preço de Venda *FEITO*
  - Não pode comprar '0' produtos *FEITO*
  - Verificar se o ID já consta na lista antes de adicionar um item na compra, e avisar o usuario para remover e adicionar a quantidade correta caso ele ja exista (FAZER O MESMO COM VENDAS).*FEITO*

*-------------------------------------------------------------------------*

*------------------------------ C A R G O S ------------------------------*

------------------------------ ADMIN ------------------------------

  - Dono da porra toda, acesso total ao Software *FEITO*

------------------------------ SUPERVISOR ------------------------------

  - Acesso total aos relatórios *FEITO*
  - Acesso parcial aos usuarios *FEITO*
	- (Pode adicionar usuarios do tipo Caixa e Almoxarifado) *FEITO*
	- (Não pode remover ninguém) *FEITO*
	- (Pode editar usuarios do tipo Caixa e Almoxarifado) *FEITO*
  - Acesso total ao resto da porra toda ?!? *FEITO*

------------------------------ CAIXA ------------------------------

  - Acesso total ao sistema de Vendas *FEITO*
  - Acesso total ao Histórico de Vendas *FEITO*
  - Acesso parcial ao estoque *FEITO*
	- (Somente habilitada a função de 'Detalhes') *FEITO*
  - Acesso total a aba Ajuda e Sobre *FEITO*

------------------------------ ALMOXARIFADO ------------------------------

  - Acesso total ao sistema de Compras *FEITO*
  - Acesso parcial ao estoque *FEITO*
	- (Somente habilitada a função de 'Detalhes') *FEITO*
  -Acesso parcial aos fornecedores *FEITO*
	- (Somente habilitada a função de 'Detalhes') *FEITO*

*-------------------------------------------------------------------------*  

*---------------------------- ATRIBUTOS ------------------------------*

---------- PRODUTO/ESTOQUE --------------

- Cadastrar produto (ADMIN):
  - id(auto incremento)
  - nome
  - preço
  - qtd
  - fornecedor

- Realizar venda/atualizar qtd estoque(ADMIN,USER):


"Criar uma tabela no venda para vendas de produtos."
  - Produto
    - Id
    - Nome
  - qtd
  - preço(preco * qtd)
  - fornecedor(Só para salvar o nome do fornecedor desse produto)
      - id
      - nome

"No form de vender fazer uma List<Produtos> para colocar dentro da lista os produtos
  a serem vendidos. E dois datagridview para listar todos os produtos e os produtos
  que serão adicionados a venda."
  
- Realizar pedido para novos estoques(ADMIN):
  - Produto
    - Id
    - Nome
    - Quantidade(qtd)
  - (qtd <= 1)
  - Fornecedor
    - Id
    - Nome
  - Nova qtd a ser pedida.
  - (Fornecedor)Telefone(os dois se necessario)
  - (Fornecedor)Endereço
  
  
"Mostrar um tipo de relatorio com tudo acima."
  
---------- FORNECEDOR --------------

- Inserir fornecedor(ADMIN)
  - id(auto incremento)
  - nome
  - cnpj
  - telefone1
  - telefone2(opcional)
  - endereço:
    - cep(com busca no WS dos Correios)
    - rua
    - numero
    - bairro
    - cidade
    - estado
    - complemento(opcional)
    
- Visualizar produtos por fornecedor
  
---------------------------------

---------- USUÁRIOS -------------- 

- Cadastrar funcionarios(ADMIN)
  - id(auto incremento)
  - cpf
  - nome
  - data de nascimento
  - telefone
  - endereço:
    - cep(com busca no WS dos Correios)
    - rua
    - numero
    - bairro
    - cidade
    - estado
    - complemento(opcional)
  - login(id do funcionário)
  - senha(cpf??)
  
- Remover Funcionario;

------- VENDA -------------------

- Realizar venda(ADMIN, USER)
  - Produtos a serem vendidos;
  - Retirar do estoque;
  - Nome do funcionario;
  - Valor total da venda;
  - Emissão de nota fiscal com todos os dados acima.
  
- Buscar produto:
  - Numero do produto ou nome para busca.
  
- Total vendido no dia;
----------------------------------


