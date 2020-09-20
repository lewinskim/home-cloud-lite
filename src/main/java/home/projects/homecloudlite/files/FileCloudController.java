package home.projects.homecloudlite.files;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FileCloudController {

    private final FileService fileService;

    public FileCloudController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public List<String> allFiles() {
        return fileService.loadHomeDirectoryFiles();
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
