package org.example.extension;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 23:48
 ***/
public class Holder<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
