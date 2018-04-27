# bankslip-api
bankslip-api é uma api rest com funções destinadas a gerenciamento de boletos que será consumida por um módulo de sistema de gestão financeira de microempresas

# Descritivo da API
A API dispõe dos seguintes serviços que serão melhor específicados posteriormente.
  * Criação de boletos
  * Listagem de boletos
  * Visualização de boletos
  * Pagamento de boletos
  * Cancelamento de boletos

# Pré-requisitos
Ferramentas devidamente instaladas e configuradas no ambiente de implantação.
  * JDK8 +
  * Git
  * Apache Maven 3.5 + 

# Código fonte
Disponível clonagem à partir de: https://github.com/georgeoikawa/bankslips-api.git

# Instalação da API Rest
Clonar o código fonte: git clone https://github.com/georgeoikawa/bankslip-api.git 
Executar o comando mvn clean install
Executar java -jar bankslip-api-0.0.1-SNAPSHOT.jar (o jar se encontra dentro da pasta target)

# Utilização
Para invocação dos serviços alguns critérios de obrigatoriedade devem ser seguidos conforme abaixo.

	* Criação de  boleto - O serviço cria um novo boleto no banco de dados
	Método http: POST
	Endpoint do serviço: http://localhost:8080/rest/bankslips
	Exemplo do corpo de requisição:
		{
		 "due_date":"2018-01-01",
		 "total_in_cents":"100000",
		 "customer":"Trillian Company",
		 "status":"PENDING"
		}
	Exemplo do corpo de Resposta:
		{
			"statusCode": 201,
			"message": "Bankslip created",
			"object": {
			"id": "92b0200b-ab17-4227-a7f0-99648cb28030",
			"customer": "Trillian Company",
			"status": "PENDING",
			"assessment": "0",
			"due_date": "2018-01-01",
			"total_in_cents": "100000"
			},
			"listError": null
		}
	Mensagens de resposta
		* 201 : Bankslip created
		* 400 : Bankslip not provided in the request body
		* 422 : Invalid bankslip provided.The possible reasons are:
			○ A field of the provided bankslip was null or with invalid values
			
			
	* Lista de boletos - Retorna uma lista de boletos em formato JSON
	Método http: GET
	Endpoint:http://localhost:8080/rest/bankslips/
	Exemplo da resposta:
		{
			"statusCode": 200,
			"message": "Ok",
			"object": [
			  {
			"id": "46b34930-ea90-455f-abee-d0cf1f4488b4",
			"customer": "Trillian Company",
			"status": "PAID",
			"assessment": "0",
			"due_date": "2018-01-01",
			"total_in_cents": "100000"
			},
			  {
			"id": "92b0200b-ab17-4227-a7f0-99648cb28030",
			"customer": "Trillian Company",
			"status": "PENDING",
			"assessment": "0",
			"due_date": "2018-01-01",
			"total_in_cents": "100000"
			}
			],
			"listError": null
		}
	Mensagens de resposta
		* 200 : Ok
		
	* Visualizar detalhe de boleto - Retorna um boleto filtrado pelo id, e caso o boleto estiver
	atrasado é calculado o valor da multa com 0,5% (Juros Simples) até 10 dias e Multa de 1% (Juros Simples) acima de 10 dias
	Método http: GET
	Endpoint:http://localhost:8080/rest/bankslips/{id}
	Exemplo da resposta:
	  {
			"statusCode": 200,
			"message": "Ok",
			"object": {
			"id": "46b34930-ea90-455f-abee-d0cf1f4488b4",
			"customer": "Trillian Company",
			"status": "PAID",
			"assessment": "116000",
			"due_date": "2018-01-01",
			"total_in_cents": "100000"
			},
			"listError": null
		}
	Mensagens de resposta
		* 200 : Ok
		* 400 : Invalid id provided - it must be a valid UUID
		* 404 : Bankslip not found with the specified id

	* Pagamento de boleto - altera o status do boleto para PAID de acordo com o id.
	Método http: PUT
	Endpoint:http://localhost:8080/rest/bankslips/{id}/pay
	Exemplo da resposta:
		{
			"statusCode": 200,
			"message": "Bankslip paid",
			"object": {
			"id": "46b34930-ea90-455f-abee-d0cf1f4488b4",
			"customer": "Trillian Company",
			"status": "PAID",
			"assessment": "0",
			"due_date": "2018-01-01",
			"total_in_cents": "100000"
			},
			"listError": null
		}
	Mensagens de resposta
		* 200 : Bankslip paid
		* 404 : Bankslip not found with the specified id
		
	
	* Cancelamento de boleto - altera o status do boleto para CANCELED de acordo com o id.
	Método http: DELETE
	Endpoint:http://localhost:8080/rest/bankslips/{id}/cancel
	Exemplo da resposta:
		{
			"statusCode": 200,
			"message": "Bankslip canceled",
			"object": {
			"id": "46b34930-ea90-455f-abee-d0cf1f4488b4",
			"customer": "Trillian Company",
			"status": "CANCELED",
			"assessment": "0",
			"due_date": "2018-01-01",
			"total_in_cents": "100000"
			},
			"listError": null
		}
	Mensagens de resposta
		* 200 : Bankslip canceled
		* 404 : Bankslip not found with the specified id

		
# Execução de testes
Executar: mvn clean install		