package Controller;

import Service.Cloud.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> urls = cloudinaryService.uploadMultipleFiles(files);
            return ResponseEntity.ok(Map.of("urls", urls));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload files: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String publicId) {
        try {
            cloudinaryService.deleteFile(publicId);
            return ResponseEntity.ok(Map.of("message", "File deleted successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete file: " + e.getMessage()));
        }
    }
}
