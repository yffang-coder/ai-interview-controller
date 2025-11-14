package com.ruoyi.common.utils.file;

/**
 * @author yffang
 * @version 1.0
 * @description:
 * @date 2025/11/6 14:55
 */

import org.springframework.web.multipart.MultipartFile;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageCompressUtils {

    private static final float QUALITY = 0.75f;     // 75% 质量（推荐）
    private static final int MAX_WIDTH = 1920;      // 最大宽度（可选）

    /**
     * 压缩图片（覆盖原文件流）
     */
    public static MultipartFile compress(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf(".")).toLowerCase();

        // 只压缩 JPEG/PNG
        if (!ext.matches("\\.(jpg|jpeg|png)$")) {
            return file; // 不压缩
        }

        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) throw new IllegalArgumentException("无效图片");

        // 1. 缩放（可选）
        if (image.getWidth() > MAX_WIDTH) {
            image = resizeImage(image, MAX_WIDTH);
        }

        // 2. 压缩质量
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            ImageWriter writer = ImageIO.getImageWritersByFormatName(getFormatName(ext)).next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(QUALITY);
            }

            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
            writer.dispose();
        }

        byte[] compressedBytes = baos.toByteArray();

        return new MockMultipartFile(filename, compressedBytes);
    }

    private static String getFormatName(String ext) {
        return ext.equals(".png") ? "png" : "jpeg";
    }

    private static BufferedImage resizeImage(BufferedImage src, int maxWidth) {
        double ratio = (double) maxWidth / src.getWidth();
        int newHeight = (int) (src.getHeight() * ratio);

        BufferedImage resized = new BufferedImage(maxWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(src, 0, 0, maxWidth, newHeight, null);
        g2d.dispose();
        return resized;
    }

    // 模拟 MultipartFile
    private static class MockMultipartFile implements MultipartFile {
        private final String filename;
        private final byte[] content;

        MockMultipartFile(String filename, byte[] content) {
            this.filename = filename;
            this.content = content;
        }

        @Override public String getName() { return filename; }
        @Override public String getOriginalFilename() { return filename; }
        @Override public String getContentType() { return "image/jpeg"; }
        @Override public boolean isEmpty() { return content.length == 0; }
        @Override public long getSize() { return content.length; }
        @Override public byte[] getBytes() { return content; }
        @Override public InputStream getInputStream() { return new ByteArrayInputStream(content); }
        @Override public void transferTo(java.io.File dest) { /* 忽略 */ }
    }
}
