<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="administrator.banner.list.label.startPeriod" path="startPeriod" width="24%"/>
	<acme:list-column code="administrator.banner.list.label.endPeriod" path="endPeriod" width="33%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="33%"/>	
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>
