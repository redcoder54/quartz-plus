package redcoder.quartzextendcommon.exception;

/**
 * Bean和Json字符串转换异常
 *
 * @author redcoder54
 * @since 1.0.0
 */
public class JacksonApiException extends RuntimeException {

    public JacksonApiException(String message) {
        super(message);
    }
}