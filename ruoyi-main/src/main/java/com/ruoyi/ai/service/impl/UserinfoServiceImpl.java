package com.ruoyi.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.ai.domain.Userinfo;
import com.ruoyi.ai.mapper.UserinfoMapper;
import com.ruoyi.ai.service.IUserinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.MinioUtil;
import com.ruoyi.common.utils.UserNicknameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ruoyi
 * @since 2025-09-10
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.url-prefix}")
    private String urlPrefix;

    @Autowired
    private MinioUtil minioUtil;
    @Override
    public Userinfo getProfile(String openid) {
        LambdaQueryWrapper<Userinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Userinfo::getOpenid, openid);
        queryWrapper.eq(Userinfo::getDelFlag, 1);
        queryWrapper.select(Userinfo::getUsername,Userinfo::getAvatarUrl);
        List<Userinfo> list = list(queryWrapper);
        if(list == null || list.isEmpty()){
            //第一次获取 则创建新用户info
            Userinfo userinfo = new Userinfo();
            userinfo.setOpenid(openid);
            userinfo.setUsername(UserNicknameUtils.generateRandomChineseNickname());
            save(userinfo);
        }
        return list.get(0);
    }




    @Override
    public Userinfo updateUserProfile(String openid, String username, MultipartFile avatar) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        LambdaQueryWrapper<Userinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Userinfo::getOpenid, openid);
        queryWrapper.eq(Userinfo::getDelFlag, 1);
        Userinfo user = getOne(queryWrapper);
        if (user == null){
            throw new IllegalArgumentException("用户不存在");
        }

        // 更新用户名
        user.setUsername(username);

        // 处理头像上传（如果提供了头像）
        if (avatar != null && !avatar.isEmpty()) {
            minioUtil.deleteFile(user.getAvatarUrl());
            String originalFilename = avatar.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = openid + fileExtension;
            user.setAvatarUrl(minioUtil.uploadFile(avatar, newFilename));
        }

        // 保存更新
        // 构造更新条件
        UpdateWrapper<Userinfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId());
        updateWrapper.set("username", user.getUsername());
        updateWrapper.set("avatar_url", user.getAvatarUrl());


        // 执行更新
        boolean success = update(updateWrapper);
        if (!success) {
            throw new RuntimeException("更新用户信息失败");
        }

        // 只返回需要的字段
        Userinfo result = new Userinfo();
        result.setUsername(user.getUsername());
        result.setAvatarUrl(user.getAvatarUrl());
        return result;
    }

    @Override
    public void saveUserProfile(String openid, String name) {
        LambdaQueryWrapper<Userinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Userinfo::getOpenid, openid);
        queryWrapper.eq(Userinfo::getDelFlag, 1);
        List<Userinfo> list = list(queryWrapper);
        if(list == null || list.isEmpty()){
            Userinfo userinfo = new Userinfo();
            userinfo.setOpenid(openid);
            userinfo.setUsername(name);
            save(userinfo);
        }


    }
}
