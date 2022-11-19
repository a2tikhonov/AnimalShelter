package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.jd6.team3.animalshelter.entity.Location;
import ru.skypro.jd6.team3.animalshelter.service.LocationService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(value = "/{id}/location", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadLocation(@PathVariable Long id, @RequestParam MultipartFile location) throws IOException {
        if (location.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        locationService.uploadLocation(id, location);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/location/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Location location = locationService.findLocation(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(location.getMediaType()));
        headers.setContentLength(location.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(location.getData());
    }

    @GetMapping(value = "/{id}/cover")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Location location = locationService.findLocation(id);;

        Path path = Path.of(location.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setContentType(location.getMediaType());
            response.setContentLength(location.getFileSize().intValue());
            is.transferTo(os);
        }
    }
}
