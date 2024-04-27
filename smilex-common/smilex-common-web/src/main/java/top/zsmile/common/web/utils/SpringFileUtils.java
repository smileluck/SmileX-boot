package top.zsmile.common.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.AntPathMatcher;
import top.zsmile.common.core.utils.file.FileUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring 框架下的文件工具
 */
@Slf4j
public class SpringFileUtils {

    /**
     * 获取符合条件的路径列表
     *
     * @param searchPath 路径，支持ANT
     * @return
     */
    public static List<String> getDirPaths(String searchPath) throws IOException {
        List<String> paths = FileUtils.parsePath(searchPath);
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> matchList = new ArrayList<>();
        Files.walkFileTree(Paths.get(paths.get(0)), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                String absolutePath = dir.toAbsolutePath().toString();
                if (antPathMatcher.match(searchPath, absolutePath)) {
                    log.debug("文件夹：" + absolutePath);
                    matchList.add(absolutePath);
                }
                return super.preVisitDirectory(dir, attrs);
            }
        });
        return matchList;
    }


    /**
     * 获取符合条件的文件列表
     *
     * @param searchPath 路径，支持ANT
     * @return
     */
    public static List<String> getFilePaths(String searchPath) throws IOException {
        List<String> paths = FileUtils.parsePath(searchPath);
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<String> matchList = new ArrayList<>();
        Files.walkFileTree(Paths.get(paths.get(0)), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String absolutePath = file.toAbsolutePath().toString();
                if (antPathMatcher.match(searchPath, absolutePath)) {
                    log.debug("文件夹：" + absolutePath);
                    matchList.add(absolutePath);
                }
                return super.visitFile(file, attrs);
            }
        });
        return matchList;
    }

    /**
     * 查找包下面的类
     * 规则 top.zsmile\**\*.class
     *
     * @param searchPath 路径，支持ANT
     * @return
     */
    public static Map<String, Class> getClassByAnnotation(String searchPath, Class annotationClass) throws IOException {
        Map<String, Class> handlerMap = new HashMap<String, Class>();
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    searchPath;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            //MetadataReader 的工厂类
            MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                //用于读取类信息
                MetadataReader reader = readerfactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                // 记载class类
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定注解
                if (annotationClass != null) {
                    Annotation annotation = clazz.getAnnotation(annotationClass);
                    if (annotation != null) {
                        //将注解中的类型值作为key，对应的类作为 value
                        handlerMap.put(classname, clazz);
                    }
                } else {
                    handlerMap.put(classname, clazz);
                }
            }
            return handlerMap;
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("找不到指定Class");
        }
    }


    /**
     * 查找包下面的类
     * 规则 top.zsmile\**\*.class
     *
     * @param searchPath 路径，支持ANT
     * @return
     */
    public static Map<String, Class> getClassBySuperClass(String searchPath, Class superClass) throws IOException {
        Map<String, Class> handlerMap = new HashMap<String, Class>();
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    searchPath;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            //MetadataReader 的工厂类
            MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                //用于读取类信息
                MetadataReader reader = readerfactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                // 记载class类
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定注解
                if (superClass != null) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length > 0) {
                        if (interfaces[0].equals(superClass)) {
                            handlerMap.put(classname, clazz);
                        }
                    }
                } else {
                    handlerMap.put(classname, clazz);
                }
            }
            return handlerMap;
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("找不到指定Class");
        }
    }

}
