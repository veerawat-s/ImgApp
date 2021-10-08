package com.sample.imgapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.sample.imgapp.model.ImgData;
import com.sample.imgapp.repository.ImgDataRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ImgAppController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ImgAppController.class);
	public static final String IMG_PATH = "G:/Work/ImgAppData/images";
	public static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	ImgDataRepository repo;
	
	
	@GetMapping("/view/{id}")
	public void viewImgById(@PathVariable String  id, HttpServletResponse response) throws IOException {

		ImgData imgData = repo.findById(Long.valueOf(id)).orElse(null);
		if (imgData != null) {
			InputStream in = new FileInputStream(getImagePath(imgData.getImgPath()));
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			IOUtils.copy(in, response.getOutputStream());
			return;
		}
		createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				MediaType.APPLICATION_JSON_VALUE, "{\"message\": \"Image not found!\"}");
	}

	@GetMapping("/search/{id}")
	public void searchImgById(@PathVariable String  id, HttpServletResponse response) throws IOException {
		ImgData imgData = repo.findById(Long.valueOf(id)).orElse(null);
		if (imgData != null) {
			imgData.setImgPath(null);
			String outputData = mapper.writeValueAsString(imgData);
			createResponse(response, HttpStatus.OK.value(),
					MediaType.APPLICATION_JSON_VALUE, outputData);
			return;
		}
		createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				MediaType.APPLICATION_JSON_VALUE, "{\"message\": \"Image not found!\"}");
	}

	@GetMapping("/memory")
	public void getTodayMemoryImages(HttpServletResponse response) throws IOException {
		SimpleDateFormat memoryDateFormat = new SimpleDateFormat("dd/MM");
		List<ImgData> listImgData = repo.findAllByMemoryDate(memoryDateFormat.format(new Date()),
				Year.now().minusYears(10).toString());
		if (listImgData != null && !listImgData.isEmpty()) {
			listImgData.forEach(i -> i.setImgPath(null));
			String outputData = mapper.writeValueAsString(listImgData);
			createResponse(response, HttpStatus.OK.value(),
					MediaType.APPLICATION_JSON_VALUE, outputData);
			return;
		}
		createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				MediaType.APPLICATION_JSON_VALUE, "{}");
	}

	@PostMapping("/upload")
	public void uploadImg(@RequestParam(required=false) String title, @RequestParam(required=false) String description,
						  @RequestPart MultipartFile image,
						  HttpServletResponse response) throws IOException {

		if (image.getOriginalFilename() != null) {
			String fileName = StringUtils.cleanPath(image.getOriginalFilename());

			ImgData imgData = new ImgData();
			Date date = new Date();
			UUID uuid = UUID.randomUUID();
			String uploadDir = String.format("%s/%s/", IMG_PATH, uuid);
			imgData.setImgPath(String.format("%s/%s", uuid, fileName));
			imgData.setTitle(title);
			imgData.setDescription(description);
			imgData.setDate(date);

			LOG.info("imgData String = " + imgData);
			repo.save(imgData);

			saveFile(uploadDir, fileName, image);

			imgData.setImgPath(null);
			String outputData = mapper.writeValueAsString(imgData);
			createResponse(response, HttpStatus.OK.value(),
					MediaType.APPLICATION_JSON_VALUE, outputData);
			return;
		}
		createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				MediaType.APPLICATION_JSON_VALUE, "{\"message\": \"Save Image failed!\"}");
	}

	@DeleteMapping("/delete/{id}")
	public void deleteImg(@PathVariable String  id, HttpServletResponse response) throws IOException {
		ImgData imgData = repo.findById(Long.valueOf(id)).orElse(null);
		if (imgData != null) {
			String imgPath = imgData.getImgPath();
			repo.delete(imgData);
			if (deleteFile(imgPath)) {
				String outputData = mapper.writeValueAsString(imgData);
				createResponse(response, HttpStatus.OK.value(),
						MediaType.APPLICATION_JSON_VALUE, "{\"message\": \"Delete success.\"}");
			} else {
				String outputData = mapper.writeValueAsString(imgData);
				createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(),
						MediaType.APPLICATION_JSON_VALUE, "{\"message\": \"Delete failed.\"}");
			}

			return;
		}
		createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				MediaType.APPLICATION_JSON_VALUE, "{\"message\": \"Image not found!\"}");
	}

	private void createResponse(HttpServletResponse response, int status, String contentType, String content) throws IOException {
		response.setContentType(contentType);
		response.setStatus(status);
		response.getWriter().write(content);
		response.getWriter().flush();
	}

	private void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
		Path path = Paths.get(uploadDir);
		
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {        
            throw new IOException("Save image failed" + fileName, ex);
        }  
	}

	private boolean deleteFile(String fileName) throws IOException {
		String targetFilePath = String.format("%s/%s", IMG_PATH, fileName);
		File file = new File(targetFilePath);
		return Files.deleteIfExists(file.toPath());
	}

	private String getImagePath(String imgUrl) {
		if (imgUrl == null) return null;

		return  String.format("%s/%s", IMG_PATH, imgUrl);
	}
}
