/*
 * ManagerDashboard.java
 *
 * Copyright (C) 2012-2024 Andres Garcia.
 *
 */

package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfMustUserStories;
	Integer						totalNumberOfShouldUserStories;
	Integer						totalNumberOfCouldUserStories;
	Integer						totalNumberOfWontUserStories;

	Double						averageEstimatedCostOfUserStories;
	Double						deviationEstimatedCostOfUserStories;
	Integer						minimumEstimatedCostOfUserStories;
	Integer						maximumEstimatedCostOfUserStories;

	Double						averageCostOfProjects;
	Double						deviationCostOfProjects;
	Integer						minimumCostOfProjects;
	Integer						maximumCostOfProjects;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------	
}
