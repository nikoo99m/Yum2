package dev.nikoo.recipes.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.nikoo.recipes.config.CloudinaryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    @Autowired(required = false)
    public ImageService(CloudinaryConfig config) {
        System.out.println("✅ CLOUDINARY_CLOUD_NAME: " + config.getCloudName());
        System.out.println("✅ CLOUDINARY_API_KEY: " + config.getApiKey());
        System.out.println("✅ CLOUDINARY_API_SECRET: " + (config.getApiSecret() != null ? "****" : "NULL"));

        if (config.getCloudName() == null || config.getApiKey() == null || config.getApiSecret() == null) {
            throw new RuntimeException("❌ Cloudinary API credentials are missing! Check application.properties.");
        }

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", config.getCloudName(),
                "api_key", config.getApiKey(),
                "api_secret", config.getApiSecret()
        ));
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }

    public void testCloudinaryConnection() {
        try {
            Map result = cloudinary.api().ping(ObjectUtils.emptyMap());
            System.out.println("✅ Cloudinary Connection Successful: " + result);
        } catch (Exception e) {
            System.out.println("❌ Cloudinary Connection Failed: " + e.getMessage());
        }
    }
}
