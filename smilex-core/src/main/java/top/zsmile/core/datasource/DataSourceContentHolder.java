package top.zsmile.core.datasource;

public class DataSourceContentHolder {
    private static final ThreadLocal<String> contentHolder = new ThreadLocal<>();

    public synchronized static void setDataSource(String dataSource) {
        contentHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contentHolder.get();
    }

    public static void clear() {
        contentHolder.remove();
    }
}
