package top.zsmile.meta;

import jdk.nashorn.internal.objects.annotations.Function;

import java.io.Serializable;

@FunctionalInterface
public interface SFunction<T> extends Serializable {
    Object get(T source);
}
