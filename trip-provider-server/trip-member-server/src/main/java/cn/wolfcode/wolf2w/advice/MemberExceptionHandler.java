package cn.wolfcode.wolf2w.advice;

import cn.wolfcode.wolf2w.common.exception.CommonExceptionHandler;
import cn.wolfcode.wolf2w.common.exception.LogicException;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

//member服务自己独有的统一异常处理逻辑
@RestControllerAdvice
public class MemberExceptionHandler extends CommonExceptionHandler {
    //抓获member服务 自己独有异常
    @ExceptionHandler(LogicException.class)
    public JsonResult logicExp(Exception e, HttpServletResponse resp) {
        e.printStackTrace();
        resp.setContentType("application/json;charset=utf-8");
        return JsonResult.error(JsonResult.CODE_ERROR_PARAM, e.getMessage(), null);
    }
}
