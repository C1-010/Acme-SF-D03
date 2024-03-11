
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.Administrator;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^R-[0-9]{3}$")
	private String				reference;

	@NotNull
	@Past
	private Date				identificationDate;

	@NotNull
	@Min(0)
	private double				impact;

	@NotNull
	@Digits(integer = 3, fraction = 2)
	@Max(100)
	private double				probability;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double value() {
		Double result;

		result = this.impact * this.probability;

		return result;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project			project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Administrator	administrator;

}
