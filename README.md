# FinTrack - Gestão de Gastos Inteligente

**Versão:** 2.0.0  
**Autor:** João Paulo Costa Sales  
**Repositório:** [https://github.com/joaop0709/FinTrack](https://github.com/joaop0709/FinTrack)

---

## ⬇️ Download / Execução

> **[📦 Baixar fintrack.jar — Última Versão](https://github.com/joaop0709/FinTrack/releases/latest)**

```bash
java -jar fintrack.jar
```

> Requisito: Java 17 ou superior instalado.

---

## 📝 Descrição do Problema

Muitas pessoas têm dificuldade em manter um registo organizado das suas despesas diárias, perdendo a noção de quanto gastam ao longo do mês. A falta de uma ferramenta simples resulta em descontrolo financeiro e dificuldade em priorizar poupanças.

## 💡 Proposta da Solução

O **FinTrack** é uma aplicação de consola leve e direta para registo imediato de gastos. Na versão 2.0, passa a consultar a **AwesomeAPI** em tempo real para exibir o total de gastos convertido para **USD** e **EUR**, dando ao utilizador uma visão global do seu consumo.

## 👥 Público-alvo

Estudantes e jovens profissionais que procuram uma ferramenta minimalista e funcional para controlo financeiro sem a complexidade de aplicações bancárias.

---

## 🚀 Funcionalidades

- **Registo de Despesas:** Adição de valores numéricos com suporte a casas decimais.
- **Cálculo Automático:** Visualização instantânea do total acumulado.
- **Conversão de Moeda (NOVO):** Total exibido em BRL, USD e EUR com cotação em tempo real via [AwesomeAPI](https://economia.awesomeapi.com.br).
- **Gestão de Erros:** Opção para remover gastos ou limpar toda a lista.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Detalhe |
|---|---|
| Java 17 | Linguagem principal |
| Maven | Gestor de dependências e build |
| JUnit 5.10.0 | Testes unitários e de integração |
| AwesomeAPI | API pública de câmbio (BRL→USD/EUR) |
| GitHub Actions | Pipeline de CI/CD |

---

## ⚙️ Instalação e Execução

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Clonar e executar
```bash
git clone https://github.com/joaop0709/FinTrack.git
cd FinTrack/fintrack
mvn compile
mvn exec:java -Dexec.mainClass="com.fintrack.FinTrack"
```

### Rodar os testes
```bash
mvn test
```

### Gerar JAR
```bash
mvn package
java -jar target/fintrack.jar
```

---

## 🌐 API Utilizada

**AwesomeAPI — Câmbio**  
- Endpoint: `https://economia.awesomeapi.com.br/json/last/USD-BRL,EUR-BRL`  
- Gratuita, sem autenticação  
- Retorna cotação em tempo real de USD e EUR em relação ao BRL

---

## ✅ CI/CD

O pipeline do GitHub Actions roda automaticamente a cada push nas branches `master` e `entrega-intermediaria`, executando:
1. Compilação (`mvn compile`)
2. Testes unitários + integração (`mvn test`)
3. Geração do JAR (`mvn package`)

