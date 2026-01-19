package com.ruoyi.ai.service;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.ruoyi.ai.domain.Banner;
import com.ruoyi.ai.mapper.BannerMapper;
import com.ruoyi.ai.service.impl.BannerServiceImpl;
import com.ruoyi.common.utils.MinioUtil;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * BannerServiceImpl 的示例单元测试
 *
 * 关注点：
 * - getAllBannerList：构造查询条件后委托给 MyBatis-Plus 的 list()，这里主要演示调用不抛错。
 * - checkTitleExsit：基于标题与 delFlag 的存在性判断。
 * - deleteBanner：先查 URL，调用 Minio 删除文件，再做逻辑删除更新。
 */
@ExtendWith(MockitoExtension.class)
public class BannerServiceImplTest {

    @Mock
    private BannerMapper bannerMapper;

    @Mock
    private MinioUtil minioUtil;

    private BannerServiceImpl bannerService;

    /**
     * 初始化 MyBatis-Plus 的实体缓存，解决 Lambda 表达式解析问题
     * 必须在所有测试方法执行前初始化，所以使用 @BeforeAll
     * 
     * 原理：MyBatis-Plus 的 LambdaQueryWrapper 
     * 需要 TableInfo 缓存才能解析方法引用（如 Banner::getDelFlag）
     * 在纯 Mockito 测试环境中，没有 Spring 容器自动初始化，需要手动初始化
     */
    @BeforeAll
    static void initMybatisPlusCache() {
        try {
            // 创建 MyBatis Configuration
            Configuration configuration = new Configuration();
            // 创建 MapperBuilderAssistant（MyBatis-Plus 需要它来初始化 TableInfo）
            MapperBuilderAssistant assistant = new MapperBuilderAssistant(configuration, "");
            // 使用反射调用 initTableInfo 方法初始化 Banner 实体的 TableInfo
            Method initMethod = TableInfoHelper.class.getDeclaredMethod(
                    "initTableInfo", MapperBuilderAssistant.class, Class.class);
            initMethod.setAccessible(true);
            initMethod.invoke(null, assistant, Banner.class);
            
            // 验证初始化是否成功
            TableInfo tableInfo = TableInfoHelper.getTableInfo(Banner.class);
            if (tableInfo == null) {
                throw new RuntimeException("Failed to initialize MyBatis-Plus TableInfo for Banner");
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // 如果反射失败，尝试直接获取 TableInfo（可能会触发自动初始化）
            TableInfo tableInfo = TableInfoHelper.getTableInfo(Banner.class);
            if (tableInfo == null) {
                throw new RuntimeException("Failed to initialize MyBatis-Plus TableInfo for Banner. " +
                        "Please ensure MyBatis-Plus version is compatible.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error initializing MyBatis-Plus TableInfo for Banner", e);
        }
    }

    /**
     * 在每个测试方法执行前，设置 ServiceImpl 的 baseMapper 字段
     * 
     * 原理：BannerServiceImpl 继承了 ServiceImpl<BannerMapper, Banner>，
     * ServiceImpl 内部使用 baseMapper 字段来执行 list()、exists()、update() 等方法。
     * 在 Mockito 测试中，@InjectMocks 不会自动设置父类的 baseMapper 字段，需要手动设置
     */
    @BeforeEach
    void setUp() {
        // Instantiate the service
        bannerService = new BannerServiceImpl();

        try {
            // 通过反射设置 ServiceImpl 的 baseMapper 字段
            Field baseMapperField = bannerService.getClass().getSuperclass().getDeclaredField("baseMapper");
            baseMapperField.setAccessible(true);
            baseMapperField.set(bannerService, bannerMapper);

            // 通过反射设置 @Autowired 字段
            Field bannerMapperField = bannerService.getClass().getDeclaredField("bannerMapper");
            bannerMapperField.setAccessible(true);
            bannerMapperField.set(bannerService, bannerMapper);

            Field minioUtilField = bannerService.getClass().getDeclaredField("minioUtil");
            minioUtilField.setAccessible(true);
            minioUtilField.set(bannerService, minioUtil);

            // 验证 baseMapper 是否设置成功
            Object actualBaseMapper = baseMapperField.get(bannerService);
            if (actualBaseMapper != bannerMapper) {
                throw new RuntimeException("Failed to set baseMapper: expected " + bannerMapper +
                    " but got " + actualBaseMapper);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set fields for BannerServiceImpl", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error setting fields", e);
        }
    }

    @Test
    void getAllBannerList_shouldDelegateToList() {
        Banner banner = new Banner();
        banner.setTitle("t");
        banner.setUrl("u");
        banner.setImage("i");

        when(bannerMapper.selectList(any()))
                .thenReturn(Collections.singletonList(banner));

        List<Banner> list = bannerService.getAllBannerList();

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("t", list.get(0).getTitle());
        verify(bannerMapper).selectList(any());
    }


    @Test
    void checkTitleExsit_shouldReturnTrue_whenSameTitleExists() {
        Banner banner = new Banner();
        banner.setTitle("banner-1");
        // banner.getId() 为 null，所以不会添加 ne(Banner::getId, ...) 条件

        // Mock exists 返回 true (since the service calls exists(), not selectCount())
        when(bannerMapper.exists(any())).thenReturn(true);

        boolean exists = bannerService.checkTitleExsit(banner);

        // 验证 exists 被调用
        verify(bannerMapper, atLeastOnce()).exists(any());

        // 检查返回值
        Assertions.assertTrue(exists, "如果 selectCount 返回 1，则 exists 应为 true");
    }
    
    @Test
    void checkTitleExsit_shouldReturnFalse_whenTitleNotExists() {
        Banner banner = new Banner();
        banner.setTitle("banner-1");

        // 当 exists 返回 false 时，checkTitleExsit 应该返回 false
        when(bannerMapper.exists(any())).thenReturn(false);

        boolean exists = bannerService.checkTitleExsit(banner);

        Assertions.assertFalse(exists, "Expected exists to be false when exists returns false");
        verify(bannerMapper).exists(any());
    }
    
    @Test
    void checkTitleExsit_shouldExcludeSelf_whenIdIsNotNull() {
        Banner banner = new Banner();
        banner.setId(1);
        banner.setTitle("banner-1");

        // 当有 ID 时，应该排除自身，所以即使存在同名，如果只有自己，应该返回 false
        when(bannerMapper.exists(any())).thenReturn(false);

        boolean exists = bannerService.checkTitleExsit(banner);

        Assertions.assertFalse(exists, "Expected exists to be false when only self exists");
        verify(bannerMapper).exists(any());
    }

    @Test
    void deleteBanner_shouldDeleteFilesAndLogicalDelete() {
        Long[] ids = {1L, 2L};

        Banner b1 = new Banner();
        b1.setId(1);
        b1.setUrl("http://minio/b1.png");
        Banner b2 = new Banner();
        b2.setId(2);
        b2.setUrl("http://minio/b2.png");

        // list(...) 查询要删除的 banner 列表
        when(bannerMapper.selectList(any()))
                .thenReturn(Arrays.asList(b1, b2));

        when(minioUtil.extractFileNameFromUrl("http://minio/b1.png")).thenReturn("b1.png");
        when(minioUtil.extractFileNameFromUrl("http://minio/b2.png")).thenReturn("b2.png");

        // 逻辑删除 update(...)
        when(bannerMapper.update(any(), any())).thenReturn(2);

        boolean result = bannerService.deleteBanner(ids);

        Assertions.assertTrue(result);

        // 校验删除了两个文件
        verify(minioUtil, times(1)).deleteFile(eq("b1.png"));
        verify(minioUtil, times(1)).deleteFile(eq("b2.png"));

        // 校验 update 被调用
        verify(bannerMapper).update(any(), any());
    }
}

