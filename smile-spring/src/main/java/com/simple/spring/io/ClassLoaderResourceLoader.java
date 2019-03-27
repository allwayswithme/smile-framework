package com.simple.spring.io;

import java.net.URL;

/**
 * 用来加载classPath的资源
 */
public class ClassLoaderResourceLoader implements ResourceLoader {

	@Override
	public Resource getResource(String location) {
		URL resource = this.getClass().getClassLoader().getResource(location);
		return new UrlResource(resource);
	}
}
