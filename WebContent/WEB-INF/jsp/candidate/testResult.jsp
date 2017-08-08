<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/jsp/includes/header.jsp"/>
<h1>Résultats au test ${test.title}</h1>

<div>
    Vous avez obtenu ${score} % au test ${test.title} .
    Status du test : ${bilan} .
</div>

<jsp:include page="/WEB-INF/jsp/includes/footer.jsp"/>