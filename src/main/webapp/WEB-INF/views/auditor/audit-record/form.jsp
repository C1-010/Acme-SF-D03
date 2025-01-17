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

<acme:form>
	<acme:input-textbox code="auditor.audit-record.form.label.code" path="code" placeholder="auditor.audit-record.form.placeholder.code"/>
	<acme:input-moment code="auditor.audit-record.form.label.startPeriod" path="startPeriod" />
	<acme:input-moment code="auditor.audit-record.form.label.endPeriod" path="endPeriod" />
	<acme:input-textbox code="auditor.audit-record.form.label.mark" path="mark"/>
	<acme:input-textarea code="auditor.audit-record.form.label.furtherInformationLink" path="furtherInformationLink"/>

	
	
	<jstl:choose>	 
	<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
	<acme:submit  code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update"/>
	<acme:submit  code="auditor.audit-record.form.button.delete" action="/auditor/audit-record/delete"/>

	</jstl:when>
	<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-record.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
