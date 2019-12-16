package com.wsj.spring.c2;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyFilter implements TypeFilter {

    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata(); //获取类注解信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata(); //获取类型信息
        Resource resource = metadataReader.getResource(); //获取资源信息（类路径）
        String className = classMetadata.getClassName();
        System.out.println(className);
        if (className.contains("er")) {
            return true;
        }
        return false;
    }
}
