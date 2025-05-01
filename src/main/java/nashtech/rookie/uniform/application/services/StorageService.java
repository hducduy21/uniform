package nashtech.rookie.uniform.application.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface StorageService {

    void uploadFiles(Map<String, MultipartFile> files, String folder);

    void uploadFile(String key, MultipartFile file) throws IOException;

    void uploadFile(String key, String folder, MultipartFile file) throws IOException;

    void deleteFile(String key);

    void deleteFile(String key, String folder);

    byte[] getByte(String key) throws IOException;

    byte[] getByte(String key, String folder) throws IOException;
}
