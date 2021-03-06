package mirror;

import java.lang.reflect.Field;

public class RefInt {
    private Field field;

    public RefInt(Class cls, Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public int get(Object obj) {
        try {
            return this.field.getInt(obj);
        } catch (Exception unused) {
            return 0;
        }
    }

    public void set(Object obj, int i) {
        try {
            this.field.setInt(obj, i);
        } catch (Exception unused) {
        }
    }
}
