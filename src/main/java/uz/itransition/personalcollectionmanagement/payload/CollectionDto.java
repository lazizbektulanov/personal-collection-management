package uz.itransition.personalcollectionmanagement.payload;


import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CollectionDto {

    private String title;

    private String description;

    private String topic;

    private MultipartFile collectionImage;

    private JSONObject customFields;
}
