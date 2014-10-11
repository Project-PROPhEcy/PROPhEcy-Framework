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
public class NetManager {

    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Activates the specific network listener.
     * @param listener The network listener.
     */
    public <T extends INetListener> void activate(Class<T> listener) {

    } // activate


    /**
     * Deactivates the specific network listener.
     * @param listener The network listener.
     */
    public <T extends INetListener> void deactivate(Class<T> listener) {

    } // deactivate

} // class NetManager
