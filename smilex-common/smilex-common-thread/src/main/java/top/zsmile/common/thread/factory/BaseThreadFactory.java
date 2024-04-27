package top.zsmile.common.thread.factory;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zsmile.common.core.builder.SBuilder;
import top.zsmile.common.core.utils.Asserts;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RPC綫程工厂
 */
public final class BaseThreadFactory implements ThreadFactory {

    private final static Logger logger = LoggerFactory.getLogger(BaseThreadFactory.class);

    private String prefix;
    private final AtomicInteger threadCount;
    private Integer priority;
    private Boolean daemon;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    private BaseThreadFactory(Builder builder) {
        this.prefix = builder.prefix;
        this.priority = builder.priority;
        this.daemon = builder.daemon;
        this.uncaughtExceptionHandler = builder.exceptionHandler;
        this.threadCount = new AtomicInteger(1);
    }

    /**
     * 获取创建线程数量
     *
     * @return 返回创建数量
     */
    public Integer getThreadCount() {
        return this.threadCount.get();
    }

    @Override
    public Thread newThread(Runnable r) {
        Asserts.isNull(r, "Runnable 不能为空");
        Thread thread = new Thread(r);
        initThread(thread);
        return thread;
    }

    /**
     * 初始化线程属性
     *
     * @param thread 线程
     */
    private void initThread(Thread thread) {
        if (StringUtils.isNotBlank(prefix)) {
            thread.setName(prefix + threadCount.getAndAdd(1));
        } else {
            thread.setName("Base-" + threadCount.getAndAdd(1));
        }
        if (this.uncaughtExceptionHandler != null) {
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        } else {
            thread.setUncaughtExceptionHandler((t, err) -> {
                logger.error("Thread {} caught error -> {}", t.getName(), err.getMessage());
            });
        }

        if (this.priority != null) {
            thread.setPriority(this.priority);
        }

        if (this.daemon != null) {
            thread.setDaemon(this.daemon);
        }
    }

    /**
     * Builder工厂
     */
    public static final class Builder implements SBuilder<BaseThreadFactory> {
        private String prefix;
        private Integer priority;
        private Boolean daemon;
        private Thread.UncaughtExceptionHandler exceptionHandler;

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
            Validate.notNull(handler, "handler", new Object[0]);
            this.exceptionHandler = handler;
            return this;
        }

        public Builder daemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public void reset() {
            this.exceptionHandler = null;
            this.priority = null;
            this.daemon = null;
            this.prefix = null;
        }

        public BaseThreadFactory build() {
            BaseThreadFactory baseThreadFactory = new BaseThreadFactory(this);
            this.reset();
            return baseThreadFactory;
        }
    }
}

