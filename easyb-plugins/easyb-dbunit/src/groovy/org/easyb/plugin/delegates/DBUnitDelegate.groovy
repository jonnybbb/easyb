package org.easyb.plugin.delegates

import groovy.lang.Closure

import java.io.ByteArrayInputStream
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException

import org.dbunit.database.DatabaseConnection
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.DataSetException
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSet
import org.dbunit.operation.DatabaseOperation

import java.sql.Connection
import org.easyb.plugin.EasybPlugin
import org.easyb.plugin.EasybPlugin

/**
 * @author aglover
 * 
 */
public class DBUnitDelegate implements EasybPlugin {
	/**
	 *
	 * @param driver
	 * @param url
	 * @param user
	 * @param pssword
	 * @param model
	 * @throws Throwable
	 */
	public void database_model(final String driver, final String url, final String user,
			final String pssword, final Closure model) throws Throwable{
        final IDatabaseConnection conn =
				this.getConnection(driver, url, user, pssword)
        this.doDatabaseOperation(conn, model)
	}
    /**
     *
     */
    public void database_model(final Connection connection, final Closure model){
        this.doDatabaseOperation(new DatabaseConnection(connection), model)
    }
    /**
     *
     */ 
    private void doDatabaseOperation(final IDatabaseConnection iconn, final Closure model){
		try {
            DatabaseOperation.CLEAN_INSERT.execute(iconn, this.getDataSet(model))
		} finally {
			iconn.close()
		}
    }
    /**
	 * @param Closure model-- which could be anything that returns a String
	 * @return DbUnit's IDataSet type
	 * @throws IOException
	 * @throws DataSetException
	 */
	private IDataSet getDataSet(final Closure model) throws IOException, DataSetException {
		final String datastr = (String)model.call()
		return new FlatXmlDataSet(new ByteArrayInputStream(datastr.getBytes()))
	}
	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private IDatabaseConnection getConnection(final String driver,
			final String url, final String user, final String pssword ) throws ClassNotFoundException,
			SQLException {
		Class.forName(driver)
		return new DatabaseConnection(DriverManager.getConnection(url, user, pssword))
	}

  public String getName() {
    return "dbunit";
  }

  public void beforeScenario(Binding binding) {

  }

  public void afterScenario(Binding binding) {

  }
}
