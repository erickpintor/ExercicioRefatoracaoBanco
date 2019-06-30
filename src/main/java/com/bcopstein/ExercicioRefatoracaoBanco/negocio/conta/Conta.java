package com.bcopstein.ExercicioRefatoracaoBanco.negocio.conta;

public class Conta {

    private enum ContaStatus {
        SILVER("Silver", 5000.0),
        GOLD("Gold", 50000.0),
        PLATINUM("Platinum", 500000.0);

        static ContaStatus from(int ord) {
            if (ord < 0 || ord > values().length) {
                throw new IllegalArgumentException(
                    "Código de status inválido.");
            }
            return values()[ord];
        }

        final String nome;
        final double limRetirada;

        ContaStatus(String nome, double limRetirada) {
            this.nome = nome;
            this.limRetirada = limRetirada;
        }
    }

    private interface ContaState {

        ContaStatus getStatus();

        void deposito(double valor);

        void retirada(double valor);
    }

    private class Silver implements ContaState {

        private static final int LIM_UPGRADE = 50000;

        @Override
        public ContaStatus getStatus() {
            return ContaStatus.SILVER;
        }

        @Override
        public void deposito(double valor) {
            saldo += valor;
            if (saldo >= LIM_UPGRADE) {
                estado = new Gold();
            }
        }

        @Override
        public void retirada(double valor) {
            saldo = saldo - valor;
        }
    }

    private class Gold implements ContaState {

        private static final int LIM_UPGRADE = 200000;
        private static final int LIM_DOWNGRADE = 25000;

        @Override
        public ContaStatus getStatus() {
            return ContaStatus.GOLD;
        }

        @Override
        public void deposito(double valor) {
            saldo += valor * 1.01;
            if (saldo >= LIM_UPGRADE) {
                estado = new Platinum();
            }
        }

        @Override
        public void retirada(double valor) {
            saldo = saldo - valor;
            if (saldo < LIM_DOWNGRADE) {
                estado = new Silver();
            }
        }
    }

    private class Platinum implements ContaState {

        private static final int LIM_DOWNGRADE = 100000;

        @Override
        public ContaStatus getStatus() {
            return ContaStatus.PLATINUM;
        }

        @Override
        public void deposito(double valor) {
            saldo += valor * 1.025;
        }

        @Override
        public void retirada(double valor) {
            saldo = saldo - valor;
            if (saldo < LIM_DOWNGRADE) {
                estado = new Gold();
            }
        }
    }

    private ContaState stateFromStatus(int status) {
        switch (ContaStatus.from(status)) {
            case SILVER:
                return new Silver();
            case GOLD:
                return new Gold();
            case PLATINUM:
                return new Platinum();
            default:
                throw new IllegalArgumentException("Código de status inválido.");
        }
    }

    private final int numero;
    private final String correntista;
    private double saldo;
    private ContaState estado;

    public Conta(int numero, String nome, double saldo, int status) {
        this.numero = numero;
        this.correntista = nome;
        this.saldo = saldo;
        this.estado = stateFromStatus(status);
    }

    public Integer getNumero() {
        return numero;
    }

    public String getCorrentista() {
        return correntista;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getStatus() {
        return estado.getStatus().ordinal();
    }

    public String getStrStatus() {
        return estado.getStatus().nome;
    }

    public double getLimRetiradaDiaria() {
        return estado.getStatus().limRetirada;
    }

    public void deposito(double valor) {
        estado.deposito(valor);
    }

    public void retirada(double valor) {
        if (saldo - valor >= 0.0) {
            estado.retirada(valor);
        }
    }

    @Override
    public String toString() {
        return "Conta [numero=" + numero + ", correntista=" + correntista +
            ", saldo=" + saldo + ", status=" + estado.getStatus() + "]";
    }
}
