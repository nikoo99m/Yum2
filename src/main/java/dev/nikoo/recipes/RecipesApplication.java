package dev.nikoo.recipes;

import dev.nikoo.recipes.config.CloudinaryConfig;
import dev.nikoo.recipes.controllers.ImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication

public class RecipesApplication {
	public static void main(String[] args) {
//		SpringApplication.run(RecipesApplication.class, args);
		ApplicationContext ctx = SpringApplication.run(RecipesApplication.class, args);
		ImageService imageService = ctx.getBean(ImageService.class);
		imageService.testCloudinaryConnection();
	}}




