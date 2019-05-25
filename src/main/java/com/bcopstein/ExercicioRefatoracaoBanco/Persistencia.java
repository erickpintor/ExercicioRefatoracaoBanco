package com.bcopstein.ExercicioRefatoracaoBanco;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Persistencia {

    private final String NomeBDContas = "BDContasBNG.txt";
    private final String NomeBDOperacoes = "BDOperBNG.txt";

    public Map<Integer, Conta> loadContas() {
        Map<Integer, Conta> contas = new HashMap<>();
        Path path = Paths.get(NomeBDContas);

        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.defaultCharset()))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha

            while (sc.hasNext()) {
                int numero = Integer.parseInt(sc.next());
                String nome = sc.next();
                double saldo = Double.parseDouble(sc.next());
                int status = Integer.parseInt(sc.next());
                contas.put(numero, new Conta(numero, nome, saldo, status));
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        }

        return contas;
    }

    public void saveContas(Collection<Conta> contas) {
        Path path1 = Paths.get(NomeBDContas);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.defaultCharset())))
        {
            for(Conta c: contas)
                writer.format(Locale.ENGLISH,
                		      "%d;%s;%f;%d;",
                		      c.getNumero(),c.getCorrentista(),
                              c.getSaldo(),c.getStatus());
        }
        catch (IOException x)
        {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void saveOperacoes(Collection<Operacao> operacoes) {
        Path path1 = Paths.get(NomeBDOperacoes);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.defaultCharset())))
        {
            for(Operacao op:operacoes)
                writer.format(Locale.ENGLISH,
                		      "%d;%d;%d;%d;%d;%d;%d;%d;%f;%d;",
                              op.getDia(),op.getMes(),op.getAno(),
                              op.getHora(),op.getMinuto(),op.getSegundo(),
                              op.getNumeroConta(),op.getStatusConta(),
                              op.getValorOperacao(),op.getTipoOperacao()
                             );
        }
        catch (IOException x)
        {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public List<Operacao> loadOperacoes() {
        List<Operacao> operacoes = new LinkedList<>();
        Path path = Paths.get(NomeBDOperacoes);

        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.defaultCharset()))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha

            while (sc.hasNext()) {
                int dia = Integer.parseInt(sc.next());
                int mes = Integer.parseInt(sc.next());
                int ano = Integer.parseInt(sc.next());
                int hora = Integer.parseInt(sc.next());
                int minuto = Integer.parseInt(sc.next());
                int segundo = Integer.parseInt(sc.next());
                int numero = Integer.parseInt(sc.next());
                int status = Integer.parseInt(sc.next());
                double valor = Double.parseDouble(sc.next());
                int tipo = Integer.parseInt(sc.next());

                operacoes.add(new Operacao(
                    dia, mes, ano, hora, minuto, segundo,
                    numero, status, valor, tipo
                ));
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        }

        return operacoes;
    }
}
