package top.zsmile.common.core.builder;

/**
 * Builder 方法接口
 *
 * @param <T>
 */
@FunctionalInterface
public interface SBuilder<T> {
    T build();
}
