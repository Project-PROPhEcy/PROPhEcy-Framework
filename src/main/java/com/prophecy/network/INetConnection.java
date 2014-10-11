/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.network;

/**
 * Created by Christian Winkel on 24.04.14.
 */
public interface INetConnection {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------


    /**
     * Initializes the connection
     * with the specific client.
     * @param client The  client.
     */
    public void initialize(NetClient client);


    /**
     * Disconnects the network connection.
     */
    public void disconnect();

} // interface INetConnection