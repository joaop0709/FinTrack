

# FinTrack - Gestão de Gastos Inteligente

**Versão:** 3.0.0 (Database Cloud Edition)  
**Repositório:** [https://github.com/joaop0709/FinTrack](https://github.com/joaop0709/FinTrack)  
**Deploy:** [https://replit.com/@victorinogo1314/FinTrack](https://replit.com/@victorinogo1314/FinTrack)

---

## 👥 Integrantes do Grupo e Divisão de Issues

| Nome Completo | Matrícula | Atribuição / Issue Principal |
| :--- | :--- | :--- |
| **Enzo Bastos** | - | Issue #3: Listagem do Banco e Integração AwesomeAPI |
| **João Paulo Costa Sales** | - | Issue #1 / Dono do Repositório: Conexão Base e Setup |
| *Victor Hugo Candido* | - | Issue #2: Persistência e INSERT de Despesas |
| *Vinicius Coelho* | - | Issue #4: Remoção de Gastos e Tratamento de Erros |
| **João Paulo Costa Sales** e **Enzo Bastos** | - | Issue #5: Ajuste de Testes e Pipeline CI/CD |

---

## 📥 Download e Execução Rápida

> 💡 **Pré-requisito:** Certifique-se de ter o **Java 17 ou superior** instalado e as variáveis de ambiente do banco de dados configuradas.

### 🚀 Passo 1: Baixar o Executável
Clique no card abaixo para obter a build mais recente do sistema:

<table>
  <tr>
    <td>
      <a href="https://github.com/joaop0709/FinTrack/releases/latest">
        <strong>📦 Baixar fintrack.jar (Última Versão)</strong>
      </a>
    </td>
  </tr>
</table>

### 💻 Passo 2: Rodar a Aplicação
Abra o terminal na pasta onde você baixou o arquivo `.jar` e execute o comando abaixo:

```bash
java -jar fintrack.jar
```

> *Requisito: Java 17 ou superior instalado e variáveis de ambiente do banco configuradas.*

---

## 📝 Descrição do Problema

Muitas pessoas têm dificuldade em manter um registo organizado das suas despesas diárias, perdendo a noção de quanto gastam ao longo do mês. A falta de uma ferramenta simples resulta em descontrolo financeiro e dificuldade em priorizar poupanças. Além disso, aplicações locais perdem os dados assim que são fechadas.

## 💡 Proposta da Solução

O **FinTrack** é uma aplicação de consola leve e direta para registo imediato de gastos. Na versão 3.0, a aplicação deixa de ser volátil e passa a **persistir os dados em um banco de dados relacional na nuvem**. O sistema também consulta a **AwesomeAPI** em tempo real para exibir o total de gastos convertido para **USD** e **EUR**, dando ao utilizador uma visão global e internacional do seu consumo.

---

## 🚀 Funcionalidades

* **Persistência na Nuvem (NOVO):** Todos os gastos são salvos de forma definitiva em um banco de dados PostgreSQL hospedado na nuvem.
* **Listagem Dinâmica (NOVO):** O histórico de despesas é recuperado em tempo real direto do servidor remoto através da classe `GastoDAO`.
* **Cálculo com Câmbio em Tempo Real:** Soma automática de todas as despesas da nuvem com conversão instantânea para BRL, USD e EUR via [AwesomeAPI](https://economia.awesomeapi.com.br).
* **Gestão de Erros e Resiliência:** Sistema preparado com blocos de tratamento para falhas de conexão, garantindo que a aplicação não feche inesperadamente.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Detalhe |
| --- | --- |
| **Java 17** | Linguagem principal do ecossistema |
| **PostgreSQL** | Banco de dados relacional para persistência dos gastos |
| **Aiven Cloud** | Provedor de hospedagem em nuvem do banco de dados |
| **Maven** | Gestor de dependências, build e ciclo de vida |
| **JUnit 5** | Framework para testes unitários e de integração |
| **AwesomeAPI** | API pública de câmbio (BRL → USD / EUR) |
| **GitHub Actions** | Automação da esteira de CI/CD (Integração Contínua) |

---

## ⚙️ Instalação e Configuração Local

### Pré-requisitos

* Java 17+
* Maven 3.8+
* Conta ou acesso ao banco de dados PostgreSQL (Aiven/Supabase)

### 1. Configurando as Variáveis de Ambiente (`.env`)

Para proteger as credenciais de acesso ao banco de dados em nuvem, o projeto utiliza variáveis de ambiente. Crie um arquivo chamado `.env` na raiz do projeto dentro da pasta `fintrack/` e adicione as suas credenciais:

```env
DB_URL=jdbc:postgresql://seu-host-da-nuvem:porta/nome_do_banco
DB_USER=seu_usuario
DB_PASSWORD=sua_senha

```

### 2. Clonar e Executar

```bash
# Clonar o repositório
git clone [https://github.com/joaop0709/FinTrack.git](https://github.com/joaop0709/FinTrack.git)
cd FinTrack/fintrack

# Compilar o projeto
mvn compile

# Executar a aplicação de console
mvn exec:java -Dexec.mainClass="com.fintrack.FinTrack"

```

### 3. Rodar os Testes Unitários

```bash
mvn test

```

---

## 🌐 Integrações Externas

### 1. Banco de Dados (PostgreSQL na Nuvem)

* A aplicação se conecta via driver JDBC tradicional.
* **Estrutura da Tabela (`despesas`):**
* `descricao` (VARCHAR)
* `valor` (NUMERIC/DOUBLE PRECISION)
* `data` (DATE / VARCHAR)



### 2. AwesomeAPI — Câmbio

* **Endpoint utilizado:** `https://economia.awesomeapi.com.br/json/last/USD-BRL,EUR-BRL`
* Retorna de forma pública e em tempo real a cotação das moedas estrangeiras para realizar a conversão matemática do saldo total acumulado no banco.

---

## ✅ Pipeline de CI/CD (GitHub Actions)

A esteira automatizada do GitHub Actions garante a qualidade do código a cada `push` ou `pull request` realizado nas branches principais. O fluxo executa de forma automática:

1. Validação do ambiente e download das dependências Maven.
2. Compilação do código fonte (`mvn compile`).
3. Execução dos testes automatizados com JUnit 5 (`mvn test`).
4. Empacotamento e geração do artefato final executável (`mvn package`).


```
