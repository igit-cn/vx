package com.microsoft.appcenter.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

public class AppCenterLog {
    public static final String LOG_TAG = "AppCenter";
    public static final int NONE = 8;
    private static int sLogLevel = 7;

    @IntRange(from = 2, mo452to = 8)
    public static int getLogLevel() {
        return sLogLevel;
    }

    public static void setLogLevel(@IntRange(from = 2, mo452to = 8) int i) {
        sLogLevel = i;
    }

    public static void verbose(@NonNull String str, @NonNull String str2) {
        if (sLogLevel <= 2) {
            Log.v(str, str2);
        }
    }

    public static void verbose(@NonNull String str, @NonNull String str2, Throwable th) {
        if (sLogLevel <= 2) {
            Log.v(str, str2, th);
        }
    }

    public static void debug(@NonNull String str, @NonNull String str2) {
        if (sLogLevel <= 3) {
            Log.d(str, str2);
        }
    }

    public static void debug(@NonNull String str, @NonNull String str2, Throwable th) {
        if (sLogLevel <= 3) {
            Log.d(str, str2, th);
        }
    }

    public static void info(@NonNull String str, @NonNull String str2) {
        if (sLogLevel <= 4) {
            Log.i(str, str2);
        }
    }

    public static void info(@NonNull String str, @NonNull String str2, Throwable th) {
        if (sLogLevel <= 4) {
            Log.i(str, str2, th);
        }
    }

    public static void warn(@NonNull String str, @NonNull String str2) {
        if (sLogLevel <= 5) {
            Log.w(str, str2);
        }
    }

    public static void warn(@NonNull String str, @NonNull String str2, Throwable th) {
        if (sLogLevel <= 5) {
            Log.w(str, str2, th);
        }
    }

    public static void error(@NonNull String str, @NonNull String str2) {
        if (sLogLevel <= 6) {
            Log.e(str, str2);
        }
    }

    public static void error(@NonNull String str, @NonNull String str2, Throwable th) {
        if (sLogLevel <= 6) {
            Log.e(str, str2, th);
        }
    }

    public static void logAssert(@NonNull String str, @NonNull String str2) {
        if (sLogLevel <= 7) {
            Log.println(7, str, str2);
        }
    }

    public static void logAssert(@NonNull String str, @NonNull String str2, Throwable th) {
        if (sLogLevel <= 7) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("\n");
            sb.append(Log.getStackTraceString(th));
            Log.println(7, str, sb.toString());
        }
    }
}
