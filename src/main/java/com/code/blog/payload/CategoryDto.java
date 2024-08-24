package com.code.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private String categoryID;
	@NotBlank
	@Size(min=4 ,message = "Minimum size of title is 4")
	private String categoryTitle;
	@NotBlank
	@Size(min=10,message = "Minimum size of description is 4")
	private String categoryDescription;
}
