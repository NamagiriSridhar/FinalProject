package todos.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Todo 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Size(min=5, message="Title should be atleast 5 characters long")
	private String title;
	
	private String description;

	@Size(min=1, message="Creator is required")//Notnull not working
	private String creator;
	
	@NotNull(message = "Status is required")
	private Status status;

	public static enum Status
	{
		NOT_STARTED, IN_PROGRESS, COMPLETED
	}
}
