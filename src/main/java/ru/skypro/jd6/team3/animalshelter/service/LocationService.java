package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.jd6.team3.animalshelter.entity.Location;
import ru.skypro.jd6.team3.animalshelter.entity.Shelter;
import ru.skypro.jd6.team3.animalshelter.repository.LocationRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class LocationService {
    private final Logger logger = LoggerFactory.getLogger(LocationService.class);

    @Value("${upload.path}$")
    private String uploadPath;

    private final LocationRepository locationRepository;
    private final ShelterService shelterService;

    public LocationService(LocationRepository locationRepository, ShelterService shelterService) {
        this.locationRepository = locationRepository;
        this.shelterService = shelterService;
    }

    public void uploadLocation(Long id, MultipartFile file) throws IOException {
        Shelter shelter = shelterService.getShelter(id);

        Path filePath = Path.of(uploadPath, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Location location = findLocation(id);
        location.setShelter(shelter);
        location.setFilePath(filePath.toString());
        location.setFileSize(file.getSize());
        location.setMediaType(file.getContentType());
        location.setData(location.getData());

        locationRepository.save(location);
    }

    public Location findLocation(Long id) {
        logger.info("Simple find location by id");
        return locationRepository.findByShelter_ShelterId(id).orElse(new Location());
    }

    public byte[] generateImagePreview(Path filePath) throws IOException {
        logger.info("This generates preview of location.");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int heights = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, heights, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, heights, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
