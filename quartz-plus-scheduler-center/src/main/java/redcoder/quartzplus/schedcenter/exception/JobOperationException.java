package redcoder.quartzplus.schedcenter.exception;

/**
 * 表示执行job管理相关操作时遇到的错误
 *
 * @author redcoder54
 * @since 2022-01-09
 */
public class JobOperationException extends RuntimeException {

    public JobOperationException(String message) {
        super(message);
    }
}
