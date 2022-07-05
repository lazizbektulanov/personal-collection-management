package uz.itransition.personalcollectionmanagement.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.CustomFieldValue;

import java.util.UUID;

@Projection(types = CustomFieldValue.class)
public interface CustomFieldValueProjection {

    UUID getId();
    String getFieldName();
    String getFieldType();
    String getFieldValue();
}
