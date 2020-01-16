package com.upload.file.contorller;

import com.upload.file.component.FileComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tmp/storage")
public class TmpStorageFileController {

    @Autowired
    private FileComponent fileComponent;

    @GetMapping("/thumbnails")
    public ResponseEntity getTmpThumbnails(@RequestParam long channelId){
        return new ResponseEntity(fileComponent.getTmpThumbnails(channelId), HttpStatus.OK);
    }
}
