package fintrack;

public class Gasto { private String descricao;
private double valor;

public Gasto(String descricao, double valor) {
    this.descricao = descricao;
    this.valor = valor;
}

public String toString() {
    return descricao + " - R$ " + valor;
}

public double getValor() {
    return valor;
}
}


