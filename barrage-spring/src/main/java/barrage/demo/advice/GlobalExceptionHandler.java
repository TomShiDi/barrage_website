package barrage.demo.advice;

import barrage.demo.dao.ExceptionDao;
import barrage.demo.exception.BarrageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tomshidi
 * @date 2020年5月17日21:57:23
 * @description
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ExceptionDao handlerRuntimeException(Exception exception) {
        BarrageException barrageException;
        ExceptionDao exceptionDao = new ExceptionDao();
        barrageException = exception instanceof BarrageException ? (BarrageException) exception : new BarrageException(500, exception.getMessage());
        exceptionDao.setCode(barrageException.getCode());
        exceptionDao.setMessage(exception.getMessage());
        exceptionDao.setData(barrageException.getData());
        return exceptionDao;
    }
}
