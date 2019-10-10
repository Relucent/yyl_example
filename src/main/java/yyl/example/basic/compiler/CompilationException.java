package yyl.example.basic.compiler;

/**
 * 类编译异常
 */
@SuppressWarnings("serial")
public class CompilationException extends RuntimeException {
    /**
     * 构造函数，指定错误信息
     * @param msg 错误信息
     */
    public CompilationException(String msg) {
        super(msg);
    }

    /**
     * 构造函数，指定的原因和的详细消息
     * @param message 异常的详细信息， 可以使用{@link #getMessage()} 方法获取
     * @param cause 原因异常，可以通过{@link #getCause()} 方法获取
     */
    public CompilationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
