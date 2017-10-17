package rle;

import RegEx.RegEx;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        String text = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000000000000000000110101111010110000000000000000000000000000000000000000000000000110100110011001100110011001100110011001101001100110011001100111111111111101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010111110011011110101011011110111101111011110111101111011";
        //String text = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111110000000000000000000000000000000000000000000000000110101111010110000000000000000000000000000000000000000000000000110100110011001100110011001100110011001101001100110011001100111111111111101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010111110011011110101011011110111101111011110111101111011";


        //String text = readFile(args[0]);
        String textoComprimido;
        String padrao = "([\\S\\d]{1,}?)\\1+";
        RegEx regEx = new RegEx(text, padrao);
        textoComprimido = regEx.regExToERL();

        String textoDescomprimido;
        textoDescomprimido = regEx.rleDecompress(text);
        out.println("TEXTO ORIGINAL => " + text);
        out.println("RESULTADO DA COMPRESSÃO RLE => " + textoComprimido);
        out.println("RESULTADO DA DESCOMPRESSÃO RLE => " + text);

        //writeFile(args[1], );
    }

    public static String readFile(String nome) {
        StringBuilder texto = new StringBuilder();

        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(nome), "UTF-8"));
            String linha = lerArq.readLine();

            while (linha != null) {
                texto.append(linha);
                linha = lerArq.readLine();
            }

            arq.close();

            return texto.toString();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s. \n", e.getMessage());
        }

        return "";
    }

    public static void writeFile(String nome, String bits) throws IOException {
        int i, n;

        FileWriter arq = new FileWriter(nome);
        PrintWriter gravarArq = new PrintWriter(arq);

        gravarArq.printf("%s", bits);

        arq.close();

        System.out.println("Arquivo gerado com sucesso!");
    }
}