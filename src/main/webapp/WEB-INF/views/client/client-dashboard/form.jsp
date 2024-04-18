<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="client.client-dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">

	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-logs-less-than-25"/>
		</th>
		<td>
			<acme:print value="${totalProgressLogsLessThan25}"/> 
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-logs-between-25-and-50"/>
		</th>
		<td>
			<acme:print value="${totalProgressLogsBetween25And50}"/> 
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-logs-between-50-and-75"/>
		</th>
		<td>
			<acme:print value="${totalProgressLogsBetween50And75}"/> 
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.total-progress-logs-above-75"/>
		</th>
		<td>
			<acme:print value="${totalProgressLogsAbove75}"/> 
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.average-contract-budget"/>
		</th>
		<td>
			<acme:print value="${averageContractBudget}"/> EUR
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.minimum-contract-budget"/>
		</th>
		<td>
			<acme:print value="${minContractBudget}"/> EUR
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.maximum-contract-budget"/>
		</th>
		<td>
			<acme:print value="${maxContractBudget}"/> EUR
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.client-dashboard.form.label.deviation-contract-budget"/>
		</th>
		<td>
			<acme:print value="${deviationContractBudget}"/> EUR
		</td>
	</tr>	
</table>


<acme:return/>

