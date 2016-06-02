package br.com.mindbit.controleacesso.persistencia;

import java.util.HashMap;

public class Criptografia{

    private char[] alfa = new char[52];
    private char[] nume = new char[10];

    private String letras = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    private String numeros = "0123456789";

    private HashMap<Character, String> valores = new HashMap<Character, String>();
    private String senha = "senha";

    private static Criptografia criptografia = new Criptografia();

    public static Criptografia getInstancia(String senha) {
        criptografia.setSenhaOriginal(senha);
        return criptografia;
    }

    public String getSenhaCriptografada() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < senha.length(); i++) {
            sb.append(valores.get(senha.charAt(i)));
        }
        return sb.toString();
    }

    private void setSenhaOriginal(String senha) {
        this.senha = senha;

        for (int i = 0; i < 52; i++) {
            if ((i % 2) == 0) { // minúsculas
                valores.put(alfa[i], String.format("%02X", logica((senha.length() * i) % 2014)));
            }
            else if ((i % 2) != 0) { // maiúsculas
                valores.put(alfa[i], String.format("%02x", logica((senha.length() * i) % 2013)));
            }
        }
        for (int i = 0; i < 10; i++) {
            if ((i % 2) == 0) { // pares
                valores.put(nume[i], String.format("%02X", logica((senha.length() * i) % 2012)));
            }
            else if ((i % 2) != 0) { // ímpares
                valores.put(nume[i], String.format("%02x", logica((senha.length() * i) % 2011)));
            }
        }
    }

    // Método com a lógica pra gerar os valores
    private long logica(long n) {
        long cubo = n * n * n;

        long x = cubo + 157;
        long y = (cubo * n) * (21 * 2007);

        return 2 + x + y;
    }

    private Criptografia() {
        for (int i = 0; i < 52; i++) {
            alfa[i] = letras.charAt(i);
        }
        for (int i = 0; i < 10; i ++) {
            nume[i] = numeros.charAt(i);
        }
    }
}