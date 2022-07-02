package uz.itransition.personalcollectionmanagement.payload;


import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
public class CollectionDto {

    private String id;

    private String title;

    private String description;

    private String topic;

    private String previousImgUrl;

    private List<CustomFieldDto> customFields;
}
