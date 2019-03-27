package com.simple.spring.io;

/**
 * 用来加载资源的基础接口
 */
public interface ResourceLoader {

	Resource getResource(String location);
}
