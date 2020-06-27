package com.microsoft.appcenter.http;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.microsoft.appcenter.utils.NetworkStateHelper;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;

public class HttpUtils {
    private static final Pattern API_KEY_PATTERN = Pattern.compile("-[^,]+(,|$)");
    private static final Pattern CONNECTION_ISSUE_PATTERN = Pattern.compile("connection (time|reset|abort)|failure in ssl library, usually a protocol error|anchor for certification path not found");
    public static final int CONNECT_TIMEOUT = 10000;
    @VisibleForTesting
    static final int MAX_CHARACTERS_DISPLAYED_FOR_SECRET = 8;
    public static final int READ_BUFFER_SIZE = 1024;
    public static final int READ_TIMEOUT = 10000;
    private static final Class[] RECOVERABLE_EXCEPTIONS = {EOFException.class, InterruptedIOException.class, SocketException.class, UnknownHostException.class, RejectedExecutionException.class};
    public static final int THREAD_STATS_TAG = -667034599;
    private static final Pattern TOKEN_VALUE_PATTERN = Pattern.compile(":[^\"]+");
    public static final int WRITE_BUFFER_SIZE = 1024;

    @VisibleForTesting
    HttpUtils() {
    }

    public static boolean isRecoverableError(Throwable th) {
        boolean z = false;
        if (th instanceof HttpException) {
            int statusCode = ((HttpException) th).getHttpResponse().getStatusCode();
            if (statusCode >= 500 || statusCode == 408 || statusCode == 429) {
                z = true;
            }
            return z;
        }
        for (Class isAssignableFrom : RECOVERABLE_EXCEPTIONS) {
            if (isAssignableFrom.isAssignableFrom(th.getClass())) {
                return true;
            }
        }
        Throwable cause = th.getCause();
        if (cause != null) {
            for (Class isAssignableFrom2 : RECOVERABLE_EXCEPTIONS) {
                if (isAssignableFrom2.isAssignableFrom(cause.getClass())) {
                    return true;
                }
            }
        }
        if (th instanceof SSLException) {
            String message = th.getMessage();
            return message != null && CONNECTION_ISSUE_PATTERN.matcher(message.toLowerCase(Locale.US)).find();
        }
    }

    public static String hideSecret(@NonNull String str) {
        int length = str.length();
        int i = 8;
        if (str.length() < 8) {
            i = 0;
        }
        int i2 = length - i;
        char[] cArr = new char[i2];
        Arrays.fill(cArr, '*');
        StringBuilder sb = new StringBuilder();
        sb.append(new String(cArr));
        sb.append(str.substring(i2));
        return sb.toString();
    }

    public static String hideApiKeys(@NonNull String str) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = API_KEY_PATTERN.matcher(str);
        int i = 0;
        while (matcher.find()) {
            sb.append(str.substring(i, matcher.start()));
            sb.append("-***");
            sb.append(matcher.group(1));
            i = matcher.end();
        }
        if (i < str.length()) {
            sb.append(str.substring(i));
        }
        return sb.toString();
    }

    public static String hideTickets(@NonNull String str) {
        return TOKEN_VALUE_PATTERN.matcher(str).replaceAll(":***");
    }

    public static HttpClient createHttpClient(@NonNull Context context) {
        return createHttpClient(context, true);
    }

    public static HttpClient createHttpClient(@NonNull Context context, boolean z) {
        return new HttpClientRetryer(createHttpClientWithoutRetryer(context, z));
    }

    public static HttpClient createHttpClientWithoutRetryer(@NonNull Context context, boolean z) {
        return new HttpClientNetworkStateHandler(new DefaultHttpClient(z), NetworkStateHelper.getSharedInstance(context));
    }

    @NonNull
    public static HttpsURLConnection createHttpsConnection(@NonNull URL url) throws IOException {
        if ("https".equals(url.getProtocol())) {
            URLConnection openConnection = url.openConnection();
            if (openConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) openConnection;
                if (VERSION.SDK_INT <= 21) {
                    httpsURLConnection.setSSLSocketFactory(new TLS1_2SocketFactory());
                }
                httpsURLConnection.setConnectTimeout(10000);
                httpsURLConnection.setReadTimeout(10000);
                return httpsURLConnection;
            }
            throw new IOException("App Center supports only HTTPS connection.");
        }
        throw new IOException("App Center support only HTTPS connection.");
    }
}
