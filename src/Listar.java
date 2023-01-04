import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Listar {

    public static void main(String[] args) {

        final String obrigatorio =
                "?autoReconnect=true&useSSL=false&&serverTimezone=UTC";

//define a url de banco de dados // teste é o nome dobanco
        final String url =
                "jdbc:mysql://localhost:3309/trabjdbc"+obrigatorio;


//define uma sql para ser executada
        final String sql = "SELECT * FROM Administradores";

//As interfaces JDBC usadas estendem a interface AutoCloseable, e assim, ao usar o try com (),
//todas serão encerradas
        try (

//variavel conexao gerencia a conexao com o bd
//url, user, pass
                Connection conexao = DriverManager.getConnection(url, "root", "");

//cria um objeto Statement que executa instruções SQL ao banco de dados.
                Statement statement = conexao.createStatement();

//Representa o conjunto de resultados de uma tabela o banco de dados.
//Esse objeto mantém o cursor apontando para a sualinha atual de dados,

//sendo que seu início fica posicionado na primeira linha.
                                ResultSet resultSet = statement.executeQuery(sql))
        {
//Obtem o objeto ResultSetMetaData do ResultSet. Os metadados descrevem o conteúdo do ResultSet

//retorna o número de colunas
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numeroColunas = metaData.getColumnCount();
            for (int i = 1; i <= numeroColunas; i++) {

//gera o cabeçalho de uma tabela com o nome das colunas vindas do banco
                System.out.printf("%-8s\t",
                        metaData.getColumnName(i));
            }
            System.out.println();

//imprime os conteúdos dos objetos vindos do banco
            while(resultSet.next()) {
                for (int i = 1; i <= numeroColunas; i++) {
                    System.out.printf("%-8s\t",
                            resultSet.getObject(i));
                }
                System.out.println();
            }
        }
catch(SQLException e) {
            e.printStackTrace();
        }
    }
}