package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @NotBlank(message = "the name is required")
    @Size(min=2,max=50,message = "category name must be between 2 and 50")
    private String name;
    @NotBlank(message = "the slug is required")
    private String slug;

    private String parentSlug;
    private int displayOrder;
    private boolean active=true;

}
