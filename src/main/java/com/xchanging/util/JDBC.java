package com.xchanging.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {

  private String url      = null;
  private String userName = null;
  private String password = null;
  Connection     conn     = null;
  ResultSet      rs       = null;

  public JDBC() {
    url = ConfigurationManager.getString("DB_URL");
    userName = ConfigurationManager.getString("DB_USER");
    password = ConfigurationManager.getString("DB_PASSWORD");
  }

  // private static Logger log =
  // Logger.getLogger(MyConnection.class.getName());

  public String getHVC(final long number) {
    /* code for JDBC */
    String hvc = "";
    try {
    	
      jdbcConnection();
      final String sql = "select activation_code from customer_provision_profile where client_customer_id in ( select customer_id from customer_identifier where value ='"+ number + "'and type = 'MSISDN')";
      final PreparedStatement st = conn.prepareStatement(sql);
      rs = st.executeQuery();
      while (rs.next()) {
        hvc = rs.getString(1);
      }
      // log.debug("hvc in connection "+hvc);
    } catch (final Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (final SQLException e) {
      }
    }
    System.out.println("HVC: " + hvc);
    /* End of code for JDBC */
    return hvc;
  }

  public String getverificationcode(final String email) {
    /* code for JDBC */
    String verfc = "";
    try {

      jdbcConnection();
      final String sql = "select verification_code from identifier_verification where customer_identifier_id in (select customer_identifier_id from customer_identifier where value ='"
          + email + "' and state ='1')";
      final PreparedStatement st = conn.prepareStatement(sql);
      rs = st.executeQuery();
      while (rs.next()) {
        verfc = rs.getString(1);
      }
      // log.debug("hvc in connection "+hvc);
    } catch (final Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (final SQLException e) {
      }
    }
    System.out.println("HVC: " + verfc);
    /* End of code for JDBC */
    return verfc;
  }

  public void jdbcConnection() throws Exception {
    // log.debug("MY CONNECTION=-=-=-=-=-");
    Class.forName("oracle.jdbc.driver.OracleDriver");
    System.out.print(url + " " + userName + " " + password);
    conn = DriverManager.getConnection(url, userName, password);
    // log.debug("Connection="+conn);
  }
}
