package com.discbotback.upload;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploaderService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileUploaderService(StorageProperties prop) {
		this.rootLocation = Paths.get(prop.getLocation());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public String store(MultipartFile file) {
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = StringUtils.getFilenameExtension(filename);
		String fileoriginal = filename.replace("." + extension, "");
		String storedFilename = fileoriginal + "_" + System.currentTimeMillis() + "." + extension;

		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				throw new StorageException("Cannot store file with relative path" + filename);
			}

			InputStream is = file.getInputStream();
			Files.copy(is, this.rootLocation.resolve(storedFilename), StandardCopyOption.REPLACE_EXISTING);
			is.close();

			return storedFilename;
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}

	}

	@Override
	public Stream<Path> loadAll() {

		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {

			throw new StorageException("Failed to read stored files" + e);
		}
	}

	@Override
	public Path load(String fileName) {
		return rootLocation.resolve(fileName);
	}

	@Override
	public Resource loadAsResource(String filename) {

		Resource resource;
		try {
			Path file = load(filename);
			resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable())
				return resource;
			throw new StorageFileNotFoundException("could not read file " + filename);

		} catch (MalformedURLException e) {

			throw new StorageFileNotFoundException("could not read file " + filename);
		}

	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

}
