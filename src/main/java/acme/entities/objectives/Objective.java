
package acme.entities.objectives;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objective extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@NotNull
	private Priority			priority;

	//If status=1 the objective is critical, if not the objective is not critical
	private boolean				status;

	//TODO the objective must start at any moment after the instantiation moment
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startDuration;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				endDuration;

	@URL
	@Length(max = 255)
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public boolean isDurationValid() {
		// Validate that start duration is before end duration
		boolean isStartBeforeEnd = this.startDuration.before(this.endDuration);

		// Validate that start duration is after instantiation moment
		boolean isStartAfterInstantiation = this.startDuration.after(this.instantiationMoment);

		// Validate that duration is at least one hour
		long durationInMs = this.endDuration.getTime() - this.startDuration.getTime();
		boolean isDurationAtLeastOneHour = durationInMs >= 3600000; // 3600000 milliseconds = 1 hour

		return isStartBeforeEnd && isStartAfterInstantiation && isDurationAtLeastOneHour;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project project;

}
