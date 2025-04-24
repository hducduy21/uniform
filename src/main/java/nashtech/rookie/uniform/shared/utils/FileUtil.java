package nashtech.rookie.uniform.shared.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static String getFileExtension(String filename) {
        String extension = StringUtils.substringAfterLast(filename, ".");
        return extension.isEmpty() ? "" : "." + extension;
    }

    public static boolean isImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        String contentType = file.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("image/");
    }

}
