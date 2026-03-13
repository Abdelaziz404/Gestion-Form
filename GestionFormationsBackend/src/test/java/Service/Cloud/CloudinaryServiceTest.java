package Service.Cloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = com.example.GestionFormationsBackend.GestionFormationsBackendApplication.class)
public class CloudinaryServiceTest {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Test
    public void testUploadFile() {
        try {
            // Create a small dummy file
            String content = "Hello, this is a test file for Cloudinary upload.";
            MockMultipartFile dummyFile = new MockMultipartFile(
                    "file",
                    "test-upload.txt",
                    "text/plain",
                    content.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("Attempting to upload file to Cloudinary...");
            String url = cloudinaryService.uploadFile(dummyFile);
            
            System.out.println("Upload successful!");
            System.out.println("Secure URL: " + url);
            
            assertNotNull(url, "URL should not be null");
            assertTrue(url.startsWith("https://"), "URL should be an HTTPS link");

        } catch (IOException e) {
            System.err.println("Upload failed due to IOException:");
            e.printStackTrace();
            throw new RuntimeException("Test failed", e);
        } catch (Exception e) {
            System.err.println("Upload failed due to an exception (likely Invalid Cloudinary Configuration):");
            e.printStackTrace();
            throw new RuntimeException("Test failed", e);
        }
    }
}
