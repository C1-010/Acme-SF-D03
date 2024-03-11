
package acme.entities.codeAudits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CodeAudit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.codeAudits.code}")
	@NotBlank
	private String				code;

	@Temporal(TemporalType.DATE)
	@Past(message = "{validation.codeAudits.execution-date")
	@NotNull
	private Date				executionDate;

	@Pattern(regexp = "^(Static|Dynamic)$", message = "{validation.codeAudits.type}")
	@NotNull
	private CodeAuditType		type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	//computed as the mode of the marks in the corresponding auditing records;
	//ties must be broken arbitrarily if necessary.

	private Double				mark;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor				auditor;
}
