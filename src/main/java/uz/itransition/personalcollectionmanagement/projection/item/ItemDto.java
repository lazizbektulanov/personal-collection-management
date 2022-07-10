package uz.itransition.personalcollectionmanagement.projection.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private UUID id;

    private String itemName;

    private String authorProfileImgUrl;

    private String authorName;

    private String itemCollectionTitle;
}
