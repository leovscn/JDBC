import java.sql.*;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {


        final String obrigatorio = "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";
        final String url = "jdbc:mysql://localhost:3309/trabjdbc" + obrigatorio;
        final String sql = "SELECT email,senha FROM Administradores";
        boolean next = false;

        try (Connection conexao = DriverManager.getConnection(url, "root", "");
             Statement statement = conexao.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                while (!next) {
                    System.out.println("Insira seu email:");
                    String email = new Scanner(System.in).nextLine();
                    email = email.toLowerCase();
                    System.out.println("Insira sua senha: ");
                    String senha = new Scanner(System.in).nextLine();
                    senha = senha.toLowerCase();
                    if (resultSet.getObject(1).equals(email)
                            && resultSet.getObject(2).equals(senha))
                        next = true;
                    else
                        System.out.println("email ou senha inválidos");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int opcao = 0;
        // MENU

        while (opcao != 6) {
            try {
                System.out.println("Insira a opção desejada\n1 - LISTA DEVEDORES \n2 - CADASTRAR DEVEDOR\n3 - ATUALIZA DÍVIDA\n4 - BUSCA DEVEDOR\n5 - EXCLUIR DEVEDOR\n6 - SAIR");
                opcao = new Scanner(System.in).nextInt();
                switch (opcao) {
                    case 1:
                        listarDevedores();
                        break;
                    case 2:
                        cadastrarDevedor();
                        break;
                    case 3:
                        atualizaDivida();
                        break;
                    case 4:
                        buscarDevedor();
                        break;
                    case 5:
                        excluirDevedor();
                        break;
                    case 6:
                        System.out.println("Finalizando...");
                        break;
                    default:
                        System.out.println("insira um número válido");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Insira um valor válido!");

            }
        }
    }


    private static void listarDevedores() {
        final String obrigatorio = "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";
        final String url = "jdbc:mysql://localhost:3309/trabjdbc" + obrigatorio;
        final String sql = "SELECT * FROM devedores";

        try (Connection conexao = DriverManager.getConnection(url, "root", "");
             Statement statement = conexao.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numeroColunas = metaData.getColumnCount();
            for (int i = 1; i <= numeroColunas; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= numeroColunas; i++) {
                    System.out.printf("%-8s\t",
                            resultSet.getObject(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarDevedor() {
        final String obrigatorio =
                "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";
        final String url = "jdbc:mysql://localhost:3309/trabJDBC" + obrigatorio;
        String nome = "", email = "";
        double divida = 0;
        try {
            System.out.println("insira o nome: ");
            nome = new Scanner(System.in).nextLine();
            System.out.println("insira o Email:");
            email = new Scanner(System.in).nextLine();
            System.out.println("Insita o valor da dívida:");
            divida = new Scanner(System.in).nextDouble();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection conexao = DriverManager.getConnection(url, "root", "");
            PreparedStatement insert = (PreparedStatement)
                    conexao.prepareStatement("INSERT INTO Devedores (nome,email,divida) VALUES (?,?,?)");
            insert.setString(1, nome);
            insert.setString(2, email);
            insert.setDouble(3, divida);

            int retorno = insert.executeUpdate();

            if (retorno > 0)
                System.out.println("Sucesso");
            else
                System.out.println("Sem sucesso");

            conexao.close();
            insert.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void atualizaDivida() {
        final String obrigatorio = "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";
        final String url = "jdbc:mysql://localhost:3309/trabjdbc" + obrigatorio;

        int id = 0;
        double divida = 0;
        try {
            System.out.println("Insira o ID do devedor:");
            id = new Scanner(System.in).nextInt();
            System.out.println("Insira o valor pago que será descontado da dívida:");
             divida = new Scanner(System.in).nextDouble();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try (Connection conexao = DriverManager.getConnection(url, "root", "");
             PreparedStatement insert = (PreparedStatement) conexao.prepareStatement("UPDATE devedores SET divida = divida -"+divida+" WHERE id ="+id))
              {
                  int retorno = insert.executeUpdate();

                  if (retorno > 0)
                      System.out.println("Sucesso");
                  else
                      System.out.println("Sem sucesso");


              } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void excluirDevedor(){
        final String obrigatorio = "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";
        final String url = "jdbc:mysql://localhost:3309/trabjdbc" + obrigatorio;
        int id = 0;
        try {
            System.out.println("Insira o ID do devedor:");
            id = new Scanner(System.in).nextInt();
             } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try (Connection conexao = DriverManager.getConnection(url, "root", "");
             PreparedStatement insert = (PreparedStatement) conexao.prepareStatement("DELETE FROM devedores WHERE id ="+id))
        {
            int retorno = insert.executeUpdate();

            if (retorno > 0)
                System.out.println("Sucesso");
            else
                System.out.println("Sem sucesso");


        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void buscarDevedor(){
        System.out.println("Insira o nome a ser buscado:");
        String nome = new Scanner(System.in).nextLine();


        final String obrigatorio = "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";
        final String url = "jdbc:mysql://localhost:3309/trabjdbc" + obrigatorio;
        final String sql = "SELECT * FROM devedores WHERE nome like '"+nome+"'";

        try (Connection conexao = DriverManager.getConnection(url, "root", "");
             Statement statement = conexao.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numeroColunas = metaData.getColumnCount();
            for (int i = 1; i <= numeroColunas; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= numeroColunas; i++) {
                    System.out.printf("%-8s\t",
                            resultSet.getObject(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}