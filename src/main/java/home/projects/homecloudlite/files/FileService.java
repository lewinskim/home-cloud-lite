package home.projects.homecloudlite.files;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
class FileService {

    private static final String USER_ROOT_DIR = System.getProperty("user.dir");
    private static final String APP_ROOT_DIRECTORY_NAME = "homecloud";
    private final Path rootAppDirectory = Path.of(USER_ROOT_DIR, APP_ROOT_DIRECTORY_NAME);

    public FileService() {
        init();
    }

    public Stream<Path> loadHomeDirectoryFiles() {
        try {
            return Files.walk(rootAppDirectory, 1)
                    .filter(filename -> !filename.equals(rootAppDirectory))
                    .map(rootAppDirectory::relativize);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load files ", e);
        }
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = rootAppDirectory.resolve(filename);
            UrlResource urlResource = new UrlResource(file.toUri());
            if (urlResource.exists() || urlResource.isReadable()) {
                return urlResource;
            } else {
                throw new IllegalStateException("Could not read file " + filename);
            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Could not read file " + filename, e);
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
