
 
![Olimpus](https://github.com/luandkg/Olimpus/blob/master/res/P0.png)

Linguagem de manipulação matemática simples

### PALAVRAS CHAVES

	- DEF 10		# Atribui o valor 10 ao valor de execução
	
	- ADD 5			# Soma com valor 5
	- SUB 2			# Subtrai o valor 2
	- MUX 3			# Multiplica pelo valor 3
	- DIV 2			# Divide pelo valor 2
	
	- MAKE x 		# Cria a variável x
	- APPLY x 		# Atribui o valor de execução à variável x
	- USE x 		# Atribui o valor da variável x ao valor de execução
	
	- STACK			# Mostra o valor de execução no Terminal em uma nova linha
	- STACK_INLINE          # Mostra o valor de execução no Terminal
	- TAB 1			# Atribui o 1 ao valor de Identação 
	- STATUS ""     	# Mostra o texto no Terminal em uma nova linha
	- STATUS_INLINE ""      # Mostra o texto no Terminal 

	- WHITE 1		# Salta linhas em branco no Terminal
	
	- CLEAN			# Atribui 0 ao valor de execução
	
	- SNAPSHOT		# Guarda a pilha de execução
	- ROLLBACK		# Retorna a pilha de execução a um ponto guardado anteriormente ou reinicia a pilha !
	
	- CONDITION_TRUE	# Verifica se o valor da variável é igual +1
	- CONDITION_FALSE	# Verifica se o valor da variável é igual -1
	
	- LOOP			# Executa repetidas vezes enquanto a variável for igual a +1
	
	- STEP			# Executa o numero de vezes determinado, o valor deve ser maior que 1
	
	- CANCEL		# Interrompe o laço de execução
	- CONTINUE		# Pula uma execução do laço de execução
	

	- RET			# Retorna o valor de dentro de uma função
	- DO			# Inicia a execução de uma função
	- WITH			# Realiza a passagem de parametros para uma função
	- RUN			# Retorna o valor de uma função
	- TO                    # Retorna o valor de uma função para a variável
    - SAVE                  # Salva a FUNCTION em uma nova ACTION
	- ACTION		# Inicia a definição de uma ação
	- FUNCTION		# Inicia a definição de uma função
	- REQUIRED		# Importa uma biblioteca de ACTIONS e FUNCTIONS
	
	- FUNCTION_GET          # Obtem o endereço de uma FUNCTION
	- FUNCTION_SET          # Cria uma nova FUNCTION com o endereço da função anterior
	- ACTION_GET            # Obtem o endereço de uma ACTION
	- ACTION_SET            # Cria uma nova ACTION com o endereço da função anterior
	
	
### AÇÃO

	- Bloco de códigos nomeado
	- A execução da action é realizada com o comando call

### FUNÇÃO

	- O corpo de uma função é formada por duas actions : PARAM e RETURN
	- O retorno de uma função é executada com o comando ret
	- A inicio da execução de uma função é realizada com o comando DO
	- O retorno da execução de uma função é realizada com o comando RUN
	
### REQUISIÇÃO

	- Importa uma biblioteca de ACTIONS e FUNCTIONS

### CÓDIGO 1 - ESCOPO PRINCIPAL

![Olimpus](https://github.com/luandkg/Olimpus/blob/master/res/P1.png)

### CÓDIGO 2 - CONDITION_TRUE e CONDITION_FALSE

![Olimpus](https://github.com/luandkg/Olimpus/blob/master/res/P2.png)

### CÓDIGO 3 - STEP

![Olimpus](https://github.com/luandkg/Olimpus/blob/master/res/P3.png)

### CÓDIGO 4 - LOOP

![Olimpus](https://github.com/luandkg/Olimpus/blob/master/res/P4.png)

### CÓDIGO 6 - ACTIONS e FUNCTIONS

![Olimpus](https://github.com/luandkg/Olimpus/blob/master/res/P5.png)


