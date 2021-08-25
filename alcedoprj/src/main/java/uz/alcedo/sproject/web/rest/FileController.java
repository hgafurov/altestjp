package uz.alcedo.sproject.web.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uz.alcedo.sproject.service.FileStorageService;
import uz.alcedo.sproject.web.rest.vm.UploadFileVM;

@RestController
@CrossOrigin(origins = "*")  // "*" Havfli! Aniq hostni ko'rsatish kerak
@RequestMapping("/api/v1/files")
public class FileController {

	private final FileStorageService fileStorageService;

	@Autowired
	public FileController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}
	
	@PostMapping(path = "/upload-ed", produces = "application/json")
	@ResponseBody
    public ResponseEntity<?> uploadFileEd(@RequestParam("file") MultipartFile file, Model model) {
        System.out.println(file.getName());
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/download/")
                .path(fileName)
                .toUriString();
        
        Map<Object, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("originalName", fileName);
        response.put("generatedName", fileName);
        response.put("msg", "Image upload successful");
		response.put("imageUrl", fileDownloadUri);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping("/upload")
    public UploadFileVM uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getName());
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/download/")
                .path(fileName)
                .toUriString();

        return new UploadFileVM(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
	@PostMapping("/uploads")
    public List<UploadFileVM> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
	
	@GetMapping("/download/{file_name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file_name") String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
