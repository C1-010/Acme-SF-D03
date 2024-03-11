
package acme.entities.trainingModules;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long			serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.trainingmodule.code}")
	private String						code;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date						creationMoment;

	@NotBlank
	@Length(max = 100)
	private String						details;

	@NotNull
	private TrainingModuleDifficulty	difficultyLevel;

	@PastOrPresent
	//antes del creationMoment
	private Date						updateMoment;

	@URL
	private String						optionalLink;

	@NotNull
	@PositiveOrZero
	private Integer						totalTime; //Para ponerlo en horas

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project						project;

}
