package com.letscode.cookBook.view;

import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;
import com.letscode.cookBook.enums.TipoMedida;
import com.letscode.cookBook.enums.TipoRendimento;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NovaReceitaView {
    Scanner scanner;
    private Receita receita;
    private String nome;
    private Categoria categoria;

    public NovaReceitaView() {
        this.scanner = new Scanner(System.in);
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Receita getReceita() {
        return receita;
    }

    public void askParams() {
        askNome();
        askCategoria();

        receita = new Receita(nome, categoria);

        askTempoPreparo();
        askRendimento();
        askIngredientes();
        askModoPreparo();
    }

    private void askNome() {
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("NOME\n");

        System.out.print("Qual o nome da receita? ");
        nome = scanner.nextLine();
    }

    private void askCategoria() {
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("CATEGORIA\n");

        System.out.println("Qual a categoria da receita?\n");
        for (Categoria cat : Categoria.values()) {
            System.out.printf("%d - %s\n", cat.ordinal(), cat.name());
        }

        System.out.println();
        int cat = scanner.nextInt();

        categoria = Categoria.values()[cat];
    }

    private void askTempoPreparo() {
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("TEMPO DE PREPARO\n");

        System.out.print("Qual o tempo de preparo? (minutos) ");
        receita.setTempoPreparo(scanner.nextInt());
    }

    private void askRendimento() {
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("RENDIMENTO\n");

        System.out.print("Qual a quantidade? ");
        int quantidade = scanner.nextInt();

        System.out.println("Qual o tipo? ");
        for (TipoRendimento rend : TipoRendimento.values()) {
            System.out.printf("%d - %s\n", rend.ordinal(), rend.name());
        }

        int rend = scanner.nextInt();

        receita.setRendimento(new Rendimento(quantidade, TipoRendimento.values()[rend]));
    }

    private void askIngredientes() {
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("INGREDIENTES\n");

        System.out.print("São quantos ingredientes? ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.printf("\n%d⁰ Ingrediente\n", i + 1);

            System.out.print("Qual o nome? ");
            String nome = scanner.nextLine();

            System.out.print("Qual a quantidade? ");
            double quantidade = scanner.nextDouble();

            System.out.println("Qual o tipo? ");
            for (TipoMedida med : TipoMedida.values()) {
                System.out.printf("%d - %s\n", med.ordinal(), med.name());
            }

            int med = scanner.nextInt();
            scanner.nextLine();

            Ingrediente[] ingredientes = new Ingrediente[]{new Ingrediente(nome, quantidade, TipoMedida.values()[med])};
            receita.setIngredientes(ingredientes);
        }
    }

    private void askModoPreparo() {
        ScreenUtil.clearScreen();
        ScreenUtil.printTextLine("MODO DE PREPARO\n");

        System.out.print("Quais são os modos de preparo? (separe por vírgulas) ");
        receita.setModoPreparo(scanner.nextLine().strip().split(","));
    }
}
