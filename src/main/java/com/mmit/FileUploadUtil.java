package com.mmit;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil 
{
	public static void saveUploadFile(String uploadDir, String fileName, MultipartFile multiPartFile) throws IOException
	{
		try
		{
			Path path = Path.of(uploadDir); // uploadDir = "uploads/1"
			
			if(! Files.exists(path))
				Files.createDirectories(path);
			
			InputStream inputStream = multiPartFile.getInputStream();
			
			Path uploadFile = path.resolve(fileName); // uploads/1/photo.png
			
			Files.copy(inputStream, uploadFile, StandardCopyOption.REPLACE_EXISTING);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
