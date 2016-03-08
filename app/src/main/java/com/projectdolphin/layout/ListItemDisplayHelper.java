package com.projectdolphin.layout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 3/8/2016.
 */
public class ListItemDisplayHelper {

    public static ListItemDisplayHelper getInstance() {
        if(instance == null)
            instance = new ListItemDisplayHelper();
        return instance;
    }

    public int getNumberOfPairs() {
        return methodIndexes.entrySet().size();
    }

    public Pair getNthPair(int n, ListItem item) {
        MethodInfo methodInfo = methodIndexes.get(n);
        Pair pair;
        try {
            Method method = ListItem.class.getMethod(methodInfo.getMethodName());
            pair = new Pair(methodInfo.getDisplayName(), method.invoke(item).toString());
        } catch (NoSuchMethodException e) {
            pair = new Pair(methodInfo.getDisplayName(), "Unaddressable");
        } catch (IllegalAccessException e) {
            pair = new Pair(methodInfo.getDisplayName(), "Unaddressable");
        } catch (InvocationTargetException e) {
            pair = new Pair(methodInfo.getDisplayName(), "Unaddressable");
        }
        return pair;
    }

    public class Pair {
        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private String key, value;
    }

    private ListItemDisplayHelper() {
        methodIndexes = new HashMap<>();
        methodIndexes.put(0, new MethodInfo("Grade", "Grade"));
        methodIndexes.put(1, new MethodInfo("Weight", "Weight"));
        methodIndexes.put(2, new MethodInfo("TimeSpent", "TimeSpent"));
    }

    private Map<Integer, MethodInfo> methodIndexes;
    private static ListItemDisplayHelper instance;

    private class MethodInfo {
        public MethodInfo(String methodName, String displayName) {
            this.methodName = methodName;
            this.displayName = displayName;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodInfo that = (MethodInfo) o;

            if (!methodName.equals(that.methodName)) return false;
            return displayName.equals(that.displayName);

        }

        @Override
        public int hashCode() {
            int result = methodName.hashCode();
            result = 31 * result + displayName.hashCode();
            return result;
        }

        private String methodName;
        private String displayName;
    }
}
