/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alpha_000 on 06.05.2014.
 */
public final class DBAccessManager {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private final Pattern _commandPattern;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     */
    public DBAccessManager() {
        _commandPattern = Pattern.compile(
                "\\s*(driver|url|user|password)\\s*=\\s*(.*)");
    }


    /**
     * Returns the connection string specific database access object.
     * @param connectionString The connection string.
     * @return The database access object.
     */
    final public DBAccess open(final String connectionString)
            throws ClassNotFoundException {

        String user = null;
        String password = null;
        String driver = null;
        String url = null;

        // Split the connection string into commands.
        final String[] commands = connectionString.split(";");

        for(final String command: commands) {

            final Matcher m = _commandPattern
                    .matcher(command);

            if(m.matches()) {
                switch (m.group(1)) {

                    case "user": user = m.group(2); break;
                    case "password": password = m.group(2); break;
                    case "driver": driver = m.group(2); break;
                    case "url": url = m.group(2); break;
                }
            }
            else {

                System.out.println(String.format(
                        "The command: %s in the connection " +
                                "string isn't valid.", command));
            }
        }

        return new DBAccess(
                driver, url, user, password);
    }
}