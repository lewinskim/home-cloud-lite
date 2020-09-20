package home.projects.homecloudlite.files;

import org.springframework.web.bind.annotation.GetMapping;
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
}
