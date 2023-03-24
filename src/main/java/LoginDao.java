import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.microsoft.aad.msal4j.AuthenticationResult;

public class LoginDao {

	private static Connection connection = null;

	public static boolean validate(String name, String pass) {
		boolean status = false;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String dbName = "COMPOSITEAPPS?useSSL=true";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String userName = "loyalist";
		String password = "*TeamB1*";

		String url = "jdbc:sqlserver://khupragenics.database.windows.net:1433;"
				+ "database=khupragenics-db;user=loyalist@khupragenics;" + "password=" + password + ";encrypt=true;"
				+ "trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		System.out.println(url);

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url);
			System.out.println(url + dbName + userName + password);

			pst = conn.prepareStatement("SELECT * FROM PATIENTS WHERE USERNAME=? and PASSWORD=?");
			pst.setString(1, name);
			pst.setString(2, pass);

			rs = pst.executeQuery();
			status = rs.next();
			System.out.println(status);
		}

		catch (Exception e) {
			System.out.println(e);
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return status;
	}

	public static boolean validateADUser(String name, String pass) {
		try {
			PublicClient pc = new PublicClient();
			AuthenticationResult result = pc.getAccessToken(name, pass);

			return pc.validateADUser(result.accessToken());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}

	// Create permanent connection
	public static Connection getConnection() {
		if (connection != null)
			return connection;
		else {
			try {

				String dbName = "COMPOSITEAPPS?useSSL=true";
				String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				String userName = "loyalist";
				String password = "*TeamB1*";

				String url = "jdbc:sqlserver://10.0.2.15:1433;"
						+ "database=khupragenics-db;user=loyalist@khupragenics;" + "password=" + password
						+ ";encrypt=true;"
						+ "trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

				System.out.println(url);
				Class.forName(driver);

				connection = DriverManager.getConnection(url);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connection;
		}
	}

	public static ArrayList<Patient> getAllPatients() {

		connection = LoginDao.getConnection();
		ArrayList<Patient> patientList = new ArrayList<Patient>();
		Statement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT TOP 100 * FROM PATIENTS");

			while (rs.next()) {
				Patient patient = new Patient();
				patient.setFile_id(rs.getInt("FILE_ID"));
				patient.setName(rs.getString("NAME"));
				patient.setPhone(rs.getString("PHONE_NUMBER"));
				patient.setAssigned_doctor(rs.getString("ASSIGNED_DOCTOR"));
				patient.setBlood_group(rs.getString("BLOOD_GROUP"));
				;
				patient.setUsername(rs.getString("USERNAME"));
				patient.setPassword(rs.getString("PASSWORD"));
				System.out.println(patient.getFile_id() + "::" + patient.getName() + "::" + patient.getPhone() + "::"
						+ patient.getAssigned_doctor() + "::" + patient.getBlood_group() + "::" + patient.getUsername()
						+ "::" + patient.getPassword());

				patientList.add(patient);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return patientList;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		LoginDao login = new LoginDao();
//		login.validate("driley0", "XBXA3XgR");
		login.validateADUser("AlanJ@khupragenicstest.onmicrosoft.com", "Dummy123!");
//		login.getAllPatients();

	}
}
