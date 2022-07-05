package uz.itransition.personalcollectionmanagement.projection.customfield;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.CustomField;

import java.util.List;
import java.util.UUID;

@Projection(types = CustomField.class)
public interface CustomFieldProjection {

    UUID getId();

    String getFieldName();

}
