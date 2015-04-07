package com.lacys;

/**
 * Created by Derick on 4/1/2015.
 */
public class System {
    private static int userID;
    private static System system;

    private System() {
        userID = 0;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int uid) {
        userID = uid;
    }

    public static System getInstance()
    {
        if(system == null)
            system = new System();
        return system;
    }


}
