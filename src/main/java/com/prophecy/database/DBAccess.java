/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.database;

import com.prophecy.utility.ThrowableConsumer;
import oracle.jdbc.OracleConnection;

import java.sql.*;
import java.util.function.Consumer;

/**
 * Created by alpha_000 on 06.05.2014.
 */
public class DBAccess {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the connection driver.
     */
    private String _driver = null;


    /**
     * Saves the connection url.
     */
    private String _url = null;


    /**
     * Saves the connection user.
     */
    private String _user = null;


    /**
     * Saves the connection password.
     */
    private String _password = null;


    /**
     * Saves the responsible meta manager.
     */
    private MetaManager _meta = null;


    /**
     * Saves the current sql connection.
     */
    private Connection _connection = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the connection id, which
     * is identical for equal driver,
     * url and user.
     */
    public int getId() {
        return (getDriver() + getUrl() + getUser()).hashCode();
    }


    /**
     * Gets the connection driver.
     */
    public String getDriver() {
        return _driver;
    }


    /**
     * Gets the connection url.
     */
    public String getUrl() {
        return _url;
    }


    /**
     * Gets the connection user.
     */
    public String getUser() {
        return _user;
    }


    /**
     * Gets responsible meta manager.
     */
    public MetaManager getMeta() {
        return _meta;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param driver The connection driver.
     * @param url The connection url.
     * @param user The connection user.
     * @param password The connection password.
     */
    public DBAccess(String driver, String url, String user, String password)
            throws ClassNotFoundException {

        _url = url;
        _user = user;
        _driver = driver;
        _password = password;

        _meta = new MetaManager(this);

        // Load the required driver class.
        Class.forName(getDriver());
    }


    /**
     * Requests a connection to the database,
     * which is managed by the session.
     */
    // TODO Verbindung nach einem Timeout wieder schließen
    public void requestConnection(ThrowableConsumer<Connection, Exception> consumer)
            throws Exception {

        if(_connection == null
                || _connection.isClosed()) {
            _connection = DriverManager.getConnection(
                    getUrl(), getUser(), _password);
        }

        // Todo sollte nicht hier verwendet werden!
        ((OracleConnection)_connection)
                .setDefaultRowPrefetch(100000);

        consumer.apply(_connection);
    }
}