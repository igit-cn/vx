package com.microsoft.appcenter.utils;

import android.os.Process;
import android.support.annotation.VisibleForTesting;

public class ShutdownHelper {
    @VisibleForTesting
    ShutdownHelper() {
    }

    public static void shutdown(int i) {
        Process.killProcess(Process.myPid());
        System.exit(i);
    }
}
