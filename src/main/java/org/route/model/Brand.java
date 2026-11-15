package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @NotBlank(message = "name is required")
    @Size(min=2,max=50,message = "brand name must be between 2 and 50")
    private String name;
    @NotBlank(message = "slug is required")
    private String slug;
    private boolean active=true;
}
