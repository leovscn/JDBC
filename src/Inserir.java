import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Inserir {

    public static void main(String[] args) {
        final String obrigatorio =
                "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";

//define a url de banco de dados // 3309 é o port utilizado
        final String url = "jdbc:mysql://localhost:3309/trabJDBC"+obrigatorio;
        final String sql = "Select * FROM usuarios";

        try {
            Connection conexao = DriverManager.getConnection(url, "root", "");
//É usado para criar um objeto que representa a instrução SQL que será executada, sendo que é invocado através do objeto Connetion.

            PreparedStatement insert = (PreparedStatement)
                    conexao.prepareStatement("INSERT INTO usuarios (nome, email) VALUES (?, ?)");

//substitui a ? do insert por teste
            insert.setString(1, "teste2");
            insert.setString(2, "teste2@teste.com");

//retorna numero de linhas atualizadas
            int retorno = insert.executeUpdate();

            if(retorno>0)
                System.out.println("Sucesso");
            else
                System.out.println("Sem sucesso");

//seleciona todos os usuarios do banco e imprime.
            Statement select = conexao.createStatement();
            ResultSet resultSet = select.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numeroColunas = metaData.getColumnCount();
            while(resultSet.next()) {
                for (int i = 1; i <= numeroColunas; i++) {
                    System.out.printf("%-8s\t",
                            resultSet.getObject(i));
                }
                System.out.println();

            }
//encerra as conexões o objetos
            conexao.close();
            insert.close();
            select.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
