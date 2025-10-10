package com.ruoyi.web.controller.ai_interview;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author yffang
 * @version 1.0
 * @description:
 * @date 2025/10/10 10:40
 */

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private MinioUtil minioUtil;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws IOException {
        try {
            // 处理上传的文件
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = sdf.format(System.currentTimeMillis()) + fileExtension;
            String uploadFile = minioUtil.uploadFile(file, newFilename);
            return AjaxResult.success(uploadFile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("上传失败");
        }
    }

}
