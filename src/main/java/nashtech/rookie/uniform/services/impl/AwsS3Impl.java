package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.exceptions.InternalServerErrorException;
import nashtech.rookie.uniform.services.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AwsS3Impl implements StorageService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public void uploadFiles(Map<String, MultipartFile> files, String folder) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            String key = entry.getKey();
            MultipartFile file = entry.getValue();

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    uploadFile(key, folder, file);
                } catch (IOException e) {
                    throw new InternalServerErrorException(String.format("Failed to upload file with key %s: %s", key, e.getMessage()));
                }
            });
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<String> errors = futures.stream()
                .filter(CompletableFuture::isCompletedExceptionally)
                .map(f -> {
                    try {
                        f.join();
                        return null;
                    } catch (Exception e) {
                        return e.getCause().getMessage();
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (!errors.isEmpty()) {
            throw new InternalServerErrorException("Some files failed to upload: " + String.join("; ", errors));
        }
    }

    @Override
    public void uploadFile(String key, MultipartFile file) throws IOException {
        uploadFile(key, null, file);
    }

    @Override
    public void uploadFile(String key, String folder, MultipartFile file) throws IOException {
        String fullKey = folder != null ? folder + "/" + key : key;
        PutObjectRequest putObjectRequest = buildPutObjectRequest(fullKey);
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
    }

    @Override
    public void deleteFile(String key) {
        deleteFile(key, null);
    }

    @Override
    public void deleteFile(String key, String folder) {
        String fullKey = folder != null ? folder + "/" + key : key;
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fullKey));
    }

    @Override
    public byte[] getByte(String key) throws IOException {
        return getByte(key, null);
    }

    @Override
    public byte[] getByte(String key, String folder) throws IOException {
        String fullKey = folder != null ? folder + "/" + key : key;
        GetObjectRequest getObjectRequest = buildGetObjectRequest(fullKey);
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        return response.readAllBytes();
    }

    private PutObjectRequest buildPutObjectRequest(String key) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }

    private GetObjectRequest buildGetObjectRequest(String key) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }
}

