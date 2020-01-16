package com.upload.file.component;

import com.google.common.io.Files;
import com.upload.file.enums.StatusVideoFile;
import com.upload.file.model.InfoUploadVideoTmp;
import com.upload.file.model.TmpStorageFileEntity;
import com.upload.file.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FileComponent {

    private static final Logger LOG = LogManager.getLogger(FileComponent.class);
    private static final String CONTENT_TYPE = "image/jpeg";
    private static final int QUANTITY_IMAGE = 4;

    private static List<String> images = new ArrayList<>();

    public String uploadFile(String videoName, MultipartFile file, String path) {
        if (!file.isEmpty()) {
            try {
                java.nio.file.Files.createDirectories(Paths.get(path));
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(
                                new FileOutputStream(
                                        new File(path + videoName)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                LOG.error("FileComponent in uploadFile: " + e.getMessage());
            }
        }
        return videoName;
    }

    public InfoUploadVideoTmp getInfoUploadVideoTmp(MultipartFile file) {
//        List<String> images = new ArrayList<>();
        String taskId = UUID.randomUUID().toString();
        String videoName = taskId + "." + Files.getFileExtension(file.getOriginalFilename());
        String path = FileUtils.TMP_FOLDER + File.separator  + File.separator;
        String videoFile = uploadFile(videoName, file, path);
        Path pathVideoFile = Paths.get(path + videoFile);

        try {
            images = cutVideoIntoPictures(new File(pathVideoFile.toUri()), path, "JPEG", 4);
        } catch (Exception e) {
            LOG.error("FileComponent in getThumbnails: " + e.getMessage());
        }

        InfoUploadVideoTmp videoTmp = new InfoUploadVideoTmp();
        videoTmp.setTaskId(taskId);
        videoTmp.setPath(pathVideoFile);
        videoTmp.setUploadDate(new Date());
        videoTmp.setStatusVideoFile(StatusVideoFile.UPLOADING);
        videoTmp.setThumblais(images);

        return videoTmp;
    }

    private List<String> cutVideoIntoPictures(File mp4Path, String imagePath, String imgType,
                                              int quantityImage) throws Exception {
        List<String> generateImages = new ArrayList<>();

        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(mp4Path);
        frameGrabber.start();
        Frame frame;

        //TODO delete after realization logic gRPC upload Video
        int step = frameGrabber.getLengthInFrames() / quantityImage;
        int max = frameGrabber.getLengthInFrames();
        int min = step;

        try {
            for (int ii = min; ii <= max; ii += step) {
                frameGrabber.setFrameNumber(ii);
                frame = frameGrabber.grab();
                BufferedImage bi = converter.convert(frame);
                String imageName = UUID.randomUUID() + "." + imgType;
                String path = imagePath + File.separator + imageName;
                generateImages.add(imageName);
                ImageIO.write(bi, imgType, new File(path));
            }
            frameGrabber.stop();
        } catch (Exception e) {
            LOG.error("FileComponent in cutVideoIntoPictures: " + e.getMessage());
        }
        return generateImages;
    }

    public List<TmpStorageFileEntity> getTmpThumbnails(long channelId) {
        return images.stream().map(im -> new TmpStorageFileEntity(channelId, im, (short) 2, CONTENT_TYPE)).collect(Collectors.toList());
    }

    public ResponseEntity uploadFileTmpThumbnail(MultipartFile file, long channelId) {
        try {

            if (images.size() > QUANTITY_IMAGE) {
                images.remove(images.size() - 1);
            }

            String imageName = UUID.randomUUID() + "." + Files.getFileExtension(file.getOriginalFilename());
            TmpStorageFileEntity tmp = new TmpStorageFileEntity(channelId, imageName, (short) 2, CONTENT_TYPE);

            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(
                                    new FileOutputStream(
                                            new File(FileUtils.TMP_FOLDER + File.separator + tmp.getFileName())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            images.add(imageName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
