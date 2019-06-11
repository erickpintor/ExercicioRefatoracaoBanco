public class ContaController {
    private Map<Integer, Conta> contas = new Map<>();

    public ContaController(List<Conta> contas){
        for(Conta c : contas) {
            this.contas.put(c.getNumero(), c);
        }
    }

    public void deposito(double valor, int id) {
        contas.get(id).deposito(valor);
    }

    public void retirada(double valor, int conta) {
        contas.get(id).retirada(valor)
    }
}