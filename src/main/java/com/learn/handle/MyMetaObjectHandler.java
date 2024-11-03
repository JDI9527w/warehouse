package com.learn.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.DTO.CurrentUser;
import com.learn.DTO.RequestContext;
import com.learn.exception.ServiceException;
import com.learn.util.TokenUtils;
import com.learn.util.WarehouseConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        try {
            CurrentUser currentUser = tokenUser();
            this.strictInsertFill(metaObject, "userState", Integer.class, WarehouseConstants.USER_STATE_NOT_PASS);
            this.strictInsertFill(metaObject, "isDelete", Integer.class, WarehouseConstants.LOGIC_NOT_DELETE_VALUE);
            this.strictInsertFill(metaObject, "createBy", String.class, String.valueOf(currentUser.getUserId())); // 创建人
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 创建时间
            log.info("公共字段自动填充[insert]...param: {}", metaObject);
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        try {
            CurrentUser currentUser = tokenUser();
            // 修改人
            this.strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(currentUser.getUserId()));
            // 修改时间
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage());
        }
    }

    public CurrentUser tokenUser() {
        HttpServletRequest request = RequestContext.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            throw new ServiceException("用户未登录,无法操作");
        }
        RequestContext.clear();
        return tokenUtils.getCurrentUser(token);
    }
}
