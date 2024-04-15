
package acme.entities.group;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				instantiationMoment;

	//Must start at any moment after the instantiation/update moment and must last for at least one week
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				endPeriod;

	//Must be stored somewhere else
	@URL
	@Length(max = 255)
	private String				picture;

	@NotBlank
	@Length(max = 75)
	private String				slogan;

	//Link to a target web document
	@URL
	@Length(max = 255)
	private String				link;

}
