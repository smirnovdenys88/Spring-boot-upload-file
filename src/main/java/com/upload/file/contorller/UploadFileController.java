package com.upload.file.contorller;

import com.upload.file.component.FileComponent;
import com.upload.file.model.content.ContentInfo;
import com.upload.file.model.content.ContentSegment;
import com.upload.file.model.content.Quality;
import com.upload.file.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/files")
public class UploadFileController {

    private static final Logger LOG = LogManager.getLogger(UploadFileController.class);

    @Autowired
    private FileComponent fileComponent;

    @GetMapping("/ping")
    public ResponseEntity ping() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss Z");
        return new ResponseEntity(format.format(new Date()), HttpStatus.OK);
    }

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        File img = new File(FileUtils.TMP_FOLDER + File.separator + filename);
        return ResponseEntity.ok().
                contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                .body(java.nio.file.Files.readAllBytes(img.toPath()));
    }

    @PostMapping(value = "/upload/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFileVideoToLocalStorage(@RequestParam("file") MultipartFile file) {
        LOG.info("uploadFileVideoToLocalStorage file: ", file.getName());
        return new ResponseEntity(fileComponent.getInfoUploadVideoTmp(file).getThumblais(), HttpStatus.OK);
    }

    @PostMapping(value = "/upload/video/tmp/thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFileTmpThumbnail(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("channelId") long channelId) {
        return fileComponent.uploadFileTmpThumbnail(file, channelId);
    }

    @GetMapping("/video/{filename:.+}")
    public void getVideo(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) throws Exception {
        List<Path> resources = new ArrayList<>();
        resources.add(Paths.get("/home/user/.soapbox/video/VideoPlayerTest/0.mp4"));
        resources.add(Paths.get("/home/user/.soapbox/video/VideoPlayerTest/1.mp4"));

        MultipartFileSender.fromPath(Paths.get("/home/user/.soapbox/video/VideoPlayerTest/" + filename))
                .with(request)
                .with(response)
                .serveResource();
    }

    @GetMapping("/test/{filename:.+}")
    public ResponseEntity<byte[]> test(@PathVariable String filename) throws IOException {
        File img = new File("/Users/alex/.soapbox/video/" + filename);
        return ResponseEntity.ok().
                contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img)))
                .body(java.nio.file.Files.readAllBytes(img.toPath()));
    }
}
