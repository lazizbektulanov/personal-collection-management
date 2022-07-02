package uz.itransition.personalcollectionmanagement.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.Search;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static uz.itransition.personalcollectionmanagement.utils.Constants.DEFAULT_COLLECTION_IMG_URL;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    public String getUrlIfNotExists(MultipartFile file, String imgUrl) throws IOException {
        if (imgUrl != null && imgUrl.length() > 0) return imgUrl;
        if (file != null) return uploadFile(file);
        else return DEFAULT_COLLECTION_IMG_URL;
    }
}
