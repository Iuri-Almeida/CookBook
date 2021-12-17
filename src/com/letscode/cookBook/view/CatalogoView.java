package com.letscode.cookBook.view;

import com.letscode.cookBook.controller.Catalogo;
import com.letscode.cookBook.domain.Ingrediente;
import com.letscode.cookBook.domain.Receita;
import com.letscode.cookBook.domain.Rendimento;
import com.letscode.cookBook.enums.Categoria;
import com.letscode.cookBook.enums.TipoMedida;
import com.letscode.cookBook.enums.TipoRendimento;

import java.util.Scanner;

public class CatalogoView {
    private final Receita NONE_FOUND = new Receita("Nenhuma receita encontrada", Categoria.PRATO_UNICO);
    private Receita receita;
    Catalogo controller = new Catalogo();
    private int curIndex = -1;

    public CatalogoView() {
        Receita receita01 = new Receita("Pudim de Chocolate", Categoria.DOCES_E_SOBREMESAS);
        receita01.setTempoPreparo(45 * 60);
        receita01.setRendimento(new Rendimento(4, TipoRendimento.PORCOES));
        receita01.setIngredientes(new Ingrediente[]{
                new Ingrediente("Leite Condensado", 2, TipoMedida.UNIDADES),
                new Ingrediente("Chocolate", 6, TipoMedida.COLHER_DE_SOPA)
        });
        receita01.setModoPreparo(new String[]{
                "despejar tudo em uma panela",
                "ferver até desgrudar do fundo",
                "nunca parar de mexer"
        });

        Receita receita02 = new Receita("Doce de Leite", Categoria.DOCES_E_SOBREMESAS);
        receita02.setTempoPreparo(5 * 60);
        receita02.setRendimento(new Rendimento(1, TipoRendimento.UNIDADES));
        receita02.setIngredientes(new Ingrediente[]{
                new Ingrediente("Doce de Leite", 1, TipoMedida.UNIDADES),
        });
        receita02.setModoPreparo(new String[]{
                "comprar o doce de leite"
        });

        controller.add(receita01);
        controller.add(receita02);

    }

    private void showHeader() {
        ScreenUtil.printTextLine("", 80, true, '=');
        ScreenUtil.printTextLine("#### #### #### #  #  ###  #### #### #  #", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # # #   #  # #  # #  # # # ", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # ##    ###  #  # #  # ##  ", 80, true, ' ');
        ScreenUtil.printTextLine("#    #  # #  # # #   #  # #  # #  # # # ", 80, true, ' ');
        ScreenUtil.printTextLine("#### #### #### #  #  ###  #### #### #  #", 80, true, ' ');
        ScreenUtil.printTextLine("", 80, true, '=');
    }

    private void showReceita(Receita receita) {
        System.out.println(receita.toString());
    }

    private void showAnterior() {
        if (curIndex > 0) {
            this.receita = controller.getReceita(curIndex - 1);
            if (receita != null) {
                curIndex--;
            } else {
                this.receita = controller.getReceita(curIndex);
            }
        }
    }

    private void showSeguinte() {
        this.receita = controller.getReceita(curIndex + 1);
        if (receita != null) {
            curIndex++;
        } else {
            this.receita = controller.getReceita(curIndex);
        }
    }

    private void add() {
        NovaReceitaView nr = new NovaReceitaView();
        nr.askParams();

        controller.add(nr.getReceita());
        showSeguinte();
    }

    private void search(Scanner sc) {
        ScreenUtil.clearScreen();

        System.out.print("Pesquise a receita pelo nome: ");
        String nome = sc.nextLine();

        Receita receita = controller.getReceita(nome);

        if (receita != null) {
            this.receita = receita;
        }

    }

    private void del() {
        if (curIndex >= 0 && receita != null) {
            controller.del(receita.getNome());
            curIndex = 0;
            receita = controller.getReceita(curIndex);
        }
    }

    public void show() {
        Scanner sc = new Scanner(System.in);

        showHeader();
        String option;
        do {
            ScreenUtil.clearScreen();

            showReceita(receita == null ? NONE_FOUND : receita);

            ScreenUtil.printTextLine("", 80, true, '=');
            ScreenUtil.printTextLine("P: Receita anterior", 80, true);
            ScreenUtil.printTextLine("N: Receita seguinte", 80, true);
            ScreenUtil.printTextLine("+: Adicionar nova receita", 80, true);
            ScreenUtil.printTextLine("-: Remover receita", 80, true);
            ScreenUtil.printTextLine("S: Pesquisar receita", 80, true);
            ScreenUtil.printTextLine("", 80, true, '=');
            ScreenUtil.printTextLine("#: ", 80);

            option = sc.next();
            sc.nextLine();

            switch (option.toUpperCase()) {
                case "P":
                    showAnterior();
                    break;
                case "N":
                    showSeguinte();
                    break;
                case "+":
                    add();
                    break;
                case "-":
                    del();
                    break;
                case "S":
                    search(sc);
                    break;
                default:
                    ScreenUtil.printTextLine("Opção inválida\n", 80);
                    ScreenUtil.printTextLine("Clique ENTER para continuar");
                    sc.nextLine();
            }
        } while (true);
    }
}
