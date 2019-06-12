public class ContaController {
    private Map<Integer, Conta> contas = new Map<>();

    public ContaController(List<Conta> contas){
        for(Conta c : contas) {
            this.contas.put(c.getNumero(), c);
        }
    }
    
    public Conta getConta(int numero) {
        return contas.get(numero);
    }
}