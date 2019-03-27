package com.simple.spring.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源实体类最基础的接口
 */
public interface Resource {
	InputStream getInputStream() throws IOException;
}
