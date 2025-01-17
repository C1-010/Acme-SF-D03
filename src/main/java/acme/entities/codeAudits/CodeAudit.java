
package acme.entities.codeAudits;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.helpers.MomentHelper;
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
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$", message = "{validation.codeaudits.code}")
	@NotBlank
	private String				code;

	@Temporal(TemporalType.DATE)
	@Past
	@NotNull
	private Date				executionDate;

	@NotNull
	private CodeAuditType		type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	//computed as the mode of the marks in the corresponding auditing records;
	//ties must be broken arbitrarily if necessary.
	@NotNull
	private Mark				mark;

	@URL
	@Length(max = 255)
	private String				optionalLink;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	// Check if the code audit is not in draft mode and if the instantiation moment is in the past or present
	@Transient
	public boolean isAvailable() {
		boolean result;

		result = !this.draftMode && MomentHelper.isPresentOrPast(this.executionDate);

		return result;
	}

	@Transient
	public Mark getMark(final Collection<AuditRecord> records) {
		Map<Mark, Integer> frequencyMap = new HashMap<>();
		for (AuditRecord arecords : records) {
			Mark mark = arecords.getMark();
			frequencyMap.put(mark, frequencyMap.getOrDefault(mark, 0) + 1);
		}
		Mark mode = null;
		int maxFrequency = 0;
		for (Map.Entry<Mark, Integer> entry : frequencyMap.entrySet())
			if (entry.getValue() > maxFrequency) {
				maxFrequency = entry.getValue();
				mode = entry.getKey();
			}

		return mode;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project	project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor	auditor;

}
