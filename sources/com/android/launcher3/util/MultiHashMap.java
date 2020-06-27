package com.android.launcher3.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class MultiHashMap<K, V> extends HashMap<K, ArrayList<V>> {
    public MultiHashMap() {
    }

    public MultiHashMap(int i) {
        super(i);
    }

    public void addToList(K k, V v) {
        ArrayList arrayList = (ArrayList) get(k);
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(v);
            put(k, arrayList2);
            return;
        }
        arrayList.add(v);
    }

    public MultiHashMap<K, V> clone() {
        MultiHashMap<K, V> multiHashMap = new MultiHashMap<>(size());
        for (Entry entry : entrySet()) {
            multiHashMap.put(entry.getKey(), new ArrayList((Collection) entry.getValue()));
        }
        return multiHashMap;
    }
}
