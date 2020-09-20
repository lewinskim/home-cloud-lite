package home.projects.homecloudlite.files;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
class FileService {

    private static final String USER_ROOT_DIR = System.getProperty("user.dir");
    private static final String APP_ROOT_DIRECTORY_NAME = "homecloud";
    private final Path rootAppDirectory = Path.of(USER_ROOT_DIR, APP_ROOT_DIRECTORY_NAME);

    public FileService() {
        init();
    }

    public List<String> loadHomeDirectoryFiles() {
        try {
            return Files.walk(rootAppDirectory, 1)
                    .map(path -> path.getFileName().toString())
                    .filter(filename -> !filename.equals(APP_ROOT_DIRECTORY_NAME))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Could not load files ", e);
        }
    }

    private void init() {
        try {
            if (!Files.exists(rootAppDirectory)) {
                Files.createDirectory(rootAppDirectory);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not create app home directory ", e);
        }
    }
}
