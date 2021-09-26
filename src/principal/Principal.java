package principal;

import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

import controllers.servicos.PlanoDeRefeicao;
import controllers.servicos.ProdutoMercado;
import database.servicos.DBPlanoDeRefeicao;
import database.servicos.DBProdutoMercado;
import controllers.servicos.frete.Frete;
import controllers.usuarios.LoginUsuario;
import controllers.servicos.Refeicao;
import database.servicos.DBRefeicao;
import database.servicos.frete.DBFrete;
import database.usuario.DBUsuario;
import views.menus.MenuFrete;
import views.menus.MenuMain;
import views.menus.MenusServicos;
import views.menus.MenusUsuarios;

public class Principal {

    public static void main(String[] args) {

        // Instância de DBUsuario
        DBUsuario modelUsuario = new DBUsuario();
        // Instância de DBProdutoMercado
        DBProdutoMercado modelProdutoMercado = new DBProdutoMercado();
        // Instância de DBRefeicao
        DBRefeicao modelRefeicao = new DBRefeicao();
        // Instância de DBPlanoDeRefeicao
        DBPlanoDeRefeicao modelPlanoDeRefeicao = new DBPlanoDeRefeicao();
        // Instância de DBFrete
        DBFrete modelFrete = new DBFrete();

        // Array de objectos do tipo ProdutoMercado
        ArrayList<ProdutoMercado> produtoMercado = modelProdutoMercado.returnProdutoMercado();
        // Array de objetos do tipo Refeicao
        ArrayList<Refeicao> refeicao = modelRefeicao.returnRefeicao();
        // Array de objectos do tipo PlanoDeRefeicao
        ArrayList<PlanoDeRefeicao> planoDeRefeicao = modelPlanoDeRefeicao.returnPlanoDeRefeicao();
        // Array de objetos do tipo Frete
        ArrayList<Frete> frete = modelFrete.returnFretes();

        Scanner input = new Scanner(System.in);
        Scanner inputString = new Scanner(System.in);

        // Instancia da classe MenuMain
        MenuMain menu = new MenuMain();
        // Instância de MenuUsuarios
        MenusUsuarios menuUser = new MenusUsuarios();
        // Instância de LoginUsuario
        LoginUsuario loginUser = new LoginUsuario();
        // instancia de MenusServicos
        MenusServicos menuServicos = new MenusServicos();

        while (true) {
            int opcao = menu.exibirMenuInicial();
            // Sair do programa
            if (opcao == 0) {
                System.out.println("Obrigado por utilizar nossos serviços!\nAcesse sempre!");
                break;
            }
            // Cadastrar usuario
            else if (opcao == 2) {
                // Adiciona os dados de cadastro no banco de dados do sistema
                modelUsuario.adicionarUsuario(menuUser.cadastrarUsuario());
            }
            // Fazer login no sistema
            else if (opcao == 1) {
                // Recebe a resposta do método de login
                boolean verification = false;
                // Recebe o email e senha informados pelo usuario
                ArrayList<String> inputLogin = menuUser.login();
                // Analisa as informações de login
                verification = loginUser.authentication(inputLogin.get(0), inputLogin.get(1));

                // Se os dados de login estiverem corretos entra no sistema, se não, tenta
                // novamente
                if (!verification) {
                    // Adicionar alguma coisa para o login se repetir
                    System.out.println("Não autenticado");
                } else {
                    // loop do Menu Principal
                    while (true) {

                        int num;

                        try {
                            num = menu.exibirMenuPrincipal();
                        } catch (Exception e) {
                            System.out.println("Caracter inserido não compatível!");
                            continue;
                        }

                        if (num == 0) {
                            System.out.println("fim do programa");
                            break;
                        } else {

                            // switch para os casos de uso do usuario
                            switch (num) {

                                // imprimir toda a lista de produtos e serviços disponiveis
                                case 1:
                                    int categoriaCase1 = menuServicos.selecionarCategoria();

                                    if (categoriaCase1 == 1) {
                                        System.out.println("------------------ Catálogo de Produto ----------------\n");
                                        for (ProdutoMercado element : produtoMercado) {
                                            System.out.println(element.toString());
                                        }
                                    } else if (categoriaCase1 == 2) {
                                        System.out
                                                .println("-------------------Catálogo de Refeições ---------------\n");
                                        for (Refeicao element : refeicao) {
                                            System.out.println(element.toString());
                                        }
                                    } else if (categoriaCase1 == 3) {
                                        System.out
                                                .println("------------ Catálogo de Planos de Refeição -------------\n");
                                        for (PlanoDeRefeicao element : planoDeRefeicao) {
                                            System.out.println(element.toString());
                                        }
                                    }

                                    break;

                                // ver item especifico pesquisando o id do produto
                                case 2:
                                    int categoriaCase2 = menuServicos.selecionarCategoria();
                                    try {
                                        if (categoriaCase2 == 1) {
                                            String codigoDoProduto = menuServicos.selecionarProduto();
                                            for (ProdutoMercado element : produtoMercado) {
                                                if (element.getCodigo().equals(codigoDoProduto)) {
                                                    System.out.println(
                                                            "----------------- Detalhes do Produto -----------------\n");
                                                    System.out.println(element);
                                                }
                                            }

                                        } else if (categoriaCase2 == 2) {
                                            String codigoDaRefeicao = menuServicos.selecionarProduto();
                                            for (Refeicao element : refeicao) {
                                                if (element.getCodigo().equals(codigoDaRefeicao)) {
                                                    System.out.println(
                                                            "------------------- Detalhes da Refeição -----------------\n");
                                                    System.out.println(element);
                                                }
                                            }
                                        } else if (categoriaCase2 == 3) {
                                            String codigoDoPlano = menuServicos.selecionarProduto();
                                            for (PlanoDeRefeicao element : planoDeRefeicao) {
                                                if (element.getCodigo().equals(codigoDoPlano)) {
                                                    System.out.println(
                                                            "--------------- Detalhes do Plano de Refeição-------------\n");
                                                    System.out.println(element);
                                                }
                                            }
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Caracter inserido não compatível!");
                                    }

                                    break;

                                // Adicionar Frete
                                case 3:
                                    try {
                                        // Instância de
                                        MenuFrete freteMenu = new MenuFrete();

                                        // Adiciona o frete retornado pelo método de menu à lista de fretes
                                        frete.add(freteMenu.cadastrarFrete());

                                    } catch (Exception e) {
                                        System.out.println("Digitou algo errado, tente novamente!");
                                    }

                                    break;
                                // Ver lista completa de fretes disponiveis
                                case 4:
                                    for (Frete element : frete) {
                                        System.out.println(element.toString());
                                    }
                                    break;

                                // excluir frete com base no indice (lembre q se inicia no 0 e não no 1)
                                case 6:
                                    int j;
                                    try {
                                        System.out.printf("digite a posição a ser excluida: ");
                                        j = input.nextInt();
                                        frete.remove(j);
                                    } catch (Exception e) {
                                        System.out.println("Caracter inserido não compatível!");

                                    }
                                    break;

                                // adicionar um anuncio de um produto de acordo com a categoria
                                case 7:

                                    int categoriaCase7 = menuServicos.selecionarCategoria();
                                    try {
                                        if (categoriaCase7 == 1) {
                                            System.out.println("------- Cadastrar Produto ----------");
                                            produtoMercado.add(menuServicos.cadastrarProduto());
                                        } else if (categoriaCase7 == 2) {
                                            System.out.println("------- Cadastrar Refeição ----------");
                                            refeicao.add(menuServicos.cadastrarRefeicao());
                                        } else if (categoriaCase7 == 3) {
                                            System.out.println("------- Cadastrar Plano de Refeição ----------");
                                            planoDeRefeicao.add(menuServicos.cadastrarPlanoDeRefeicao());
                                        } else {
                                            System.out.println("Opção invalida.");
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Caracter inserido não compatível!");

                                    }

                                    break;

                                case 8:
                                    try {

                                    } catch (Exception e) {
                                        System.out.println("Houve algum erro!!");
                                    }

                                    // remover um anuncio com base no id dele
                                case 9:

                                    int categoriaCase9 = menuServicos.selecionarCategoria();
                                    try {

                                        // adcionando anuncio

                                        if (categoriaCase9 == 1) {
                                            System.out.println("------- Remover Produto ----------");

                                            // modelProdutoMercado.removerProduto();;
                                        } else if (categoriaCase9 == 2) {
                                            System.out.println("------- Remover Refeição ----------");

                                            // modelRefeicao.removerRefeicao();;
                                        } else if (categoriaCase9 == 3) {
                                            System.out.println("------- Remover Plano de Refeição ----------");

                                            // modelPlanoDeRefeicao.removerPlanoDeRefeicao();;
                                        } else {
                                            System.out.println("Opção invalida.");
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Caracter inserido não compatível!");

                                    }

                                    break;

                                default:
                                    System.out.println("opção invalida");

                            }

                        }

                    }
                }
            }
        }
        input.close();
        inputString.close();

    }

}