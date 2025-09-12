package com.ruoyi.ai.service;

import com.ruoyi.ai.domain.Userinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ruoyi
 * @since 2025-09-10
 */
public interface IUserinfoService extends IService<Userinfo> {

    Userinfo getProfile(String openid);

    Userinfo updateUserProfile(String openid, String username, MultipartFile avatar) throws IOException;

    void saveUserProfile(String openid, String s);
}
