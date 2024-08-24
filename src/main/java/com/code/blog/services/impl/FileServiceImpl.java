package com.code.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.code.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
		// File Name
		String name=multipartFile.getOriginalFilename();
		//Generate random file name
		String randomId=UUID.randomUUID().toString();
		String filePath=path+File.separator+randomId.concat(name.substring(name.lastIndexOf(".")));
		File f=new File(path);
		if(!f.exists()) f.mkdir();
		Files.copy(multipartFile.getInputStream(),Paths.get(filePath));
		return randomId;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String filePath=path+File.separator+fileName;
		return new FileInputStream(filePath);
	}

}
