package mirror.android.providers;

import android.annotation.TargetApi;
import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;
import mirror.RefStaticObject;

public class Settings {
    public static Class<?> TYPE = RefClass.load(Settings.class, android.provider.Settings.class);

    public static class ContentProviderHolder {
        public static Class<?> TYPE = RefClass.load(ContentProviderHolder.class, "android.provider.Settings$ContentProviderHolder");
        public static RefObject<IInterface> mContentProvider;
    }

    @TargetApi(17)
    public static class Global {
        public static Class<?> TYPE = RefClass.load(Global.class, android.provider.Settings.Global.class);
        public static RefStaticObject<Object> sNameValueCache;
    }

    public static class NameValueCache {
        public static Class<?> TYPE = RefClass.load(NameValueCache.class, "android.provider.Settings$NameValueCache");
        public static RefObject<Object> mContentProvider;
    }

    public static class NameValueCacheOreo {
        public static Class<?> TYPE = RefClass.load(NameValueCacheOreo.class, "android.provider.Settings$NameValueCache");
        public static RefObject<Object> mProviderHolder;
    }

    public static class Secure {
        public static Class<?> TYPE = RefClass.load(Secure.class, android.provider.Settings.Secure.class);
        public static RefStaticObject<Object> sNameValueCache;
    }

    public static class System {
        public static Class<?> TYPE = RefClass.load(System.class, android.provider.Settings.System.class);
        public static RefStaticObject<Object> sNameValueCache;
    }
}
