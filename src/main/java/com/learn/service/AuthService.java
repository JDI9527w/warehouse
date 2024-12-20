package com.learn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.DTO.AssignAuthDTO;
import com.learn.entity.Auth;

import java.util.List;

public interface AuthService extends IService<Auth> {
    List<Auth> treeUserAuthById(Integer userId);

    List<Auth> listUserAuthById(Integer userId);

    List<Auth> listAuthTree();

    List<Integer> listUserAuthIdByUserId(Integer userId);

    boolean assignAuth(AssignAuthDTO assignAuthDTO);

    boolean deleteAuthChildByAuthId(Integer authId);

    boolean deleteAuthByAuthId(Integer authId);
}
