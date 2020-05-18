package com.leo.device.root;

import com.orhanobut.logger.Logger;

public class RootNative {

    private static boolean libraryLoaded = false;

    static {
        try {
            System.loadLibrary("device");
            libraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
           e.printStackTrace();
            Logger.e(e,"","");
        }
    }

    boolean wasNativeLibraryLoaded() {
        return libraryLoaded;
    }

    public native int checkForRoot(Object[] pathArray);

    public native void setLogDebugMessages(boolean logDebugMessages);

}
