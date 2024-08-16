# Código fonte do curso Spring Boot e Vaadin

Este repositório contém o código fonte para o [Construindo Aplicações Web Modernas com Spring Boot e Vaadin](https://vaadin.com/docs/latest/flow/tutorials/in-depth-course).

*Demonstração ao vivo:* https://crm.demo.vaadin.com

## Requisitos
- Git instalado - [**Download**](https://git-scm.com/downloads).
- JDK 17 instalado - [**Download**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

## Baixando o Projeto
``` bash
  # Clonar o projeto:
  $ git clone https://github.com/diasRibeirao/flow-crm-tutorial.git
```

## Executando o aplicativo
```bash
  # Instalar as dependências:
  $ mvn clean install 
  
  # Caso ocorra erro na instrução acima, ex: Erro de licença
  # Instalar as dependências:
  $ mvn install -DskipTests
  
  # Rodar diretamente do seu IDE:
  $ Executar a classe Application

  # Rodar a aplicação com mvn:
  $ mvn spring-boot:run

  # Rodar a aplicação com o CMD:
  $ Executar o seguinte comando: java -jar target/flowcrmtutorial-1.0-SNAPSHOT.jar
  
```
## Tutorial em texto
Você pode encontrar uma versão em texto do tutorial no [Vaadin Documentation](https://vaadin.com/docs/latest/flow/tutorials/in-depth-course).