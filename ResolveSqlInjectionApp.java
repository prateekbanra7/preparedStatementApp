package in.abc.dynamicinput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//usage of PreparedStatement object leads to SQLInjection
public class ResolveSqlInjectionApp {

	public static void main(String[] args) throws SQLException {

		//Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "System", "root");

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the username :: ");
		String uname = scanner.next();

		System.out.print("Enter the password :: ");
		String password = scanner.next();
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc", "root", "root");
		
		String sqlSelectQuery = "select count(*) from users where username = ? and pwd = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectQuery);

		preparedStatement.setString(1, uname);
		preparedStatement.setString(2, password);
		ResultSet resultSet = preparedStatement.executeQuery();

		int count = 0;
		if (resultSet.next()) {
			count = resultSet.getInt(1);
		}

		if (count == 1)
			System.out.println("valid credentials");
		else
			System.out.println("invalid credentials");

		scanner.close();
		resultSet.close();
		preparedStatement.close();
		connection.close();

	}

}
