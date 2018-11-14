<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Actions</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    <script>
        function post(path, params) {
            var form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', path);

            for (var key in params) {
                if (params.hasOwnProperty(key)) {
                    var hiddenField = document.createElement('input');
                    hiddenField.setAttribute('type', 'hidden');
                    hiddenField.setAttribute('name', key);
                    hiddenField.setAttribute('value', params[key]);

                    form.appendChild(hiddenField);
                }
            }

            document.body.appendChild(form);
            form.submit();
        }
    </script>
</head>
<body>
<div class="container">
<c:choose>
    <c:when test="${actionData.isPresent()}">
        <c:set value="${actionData.get()}" var="action"/>
        <spring:url value="/action/${action.id}" var="searchUrl"/>

        <h2>Акция "${action.name}"!</h2>

        <c:if test="${not empty message}">
            <div class="alert alert-${alert} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <strong>${message}</strong>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty action.products}">
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong>
                        <c:choose>
                            <c:when test="${not empty param.searchText}">
                                Не удалось найти подарки для запроса "${param.searchText}"
                                <button class="btn btn-info mb-2 ml-2" onclick="location.href='${searchUrl}'">
                                    Показать все
                                </button>
                            </c:when>
                            <c:otherwise>
                                ${action.quantity > 0 ? 'Извините, на складе закончились товары' : 'Извините, для акции исчерпан лимит подарков'}
                            </c:otherwise>
                        </c:choose>
                    </strong>
                </div>
            </c:when>
            <c:otherwise>
                <p>Осталось подарков: ${action.quantity}</p>

                <form:form class="form-inline" action="${searchUrl}" method="get">
                    <div class="form-group mx-sm-3 mb-2">
                        <input type="text" class="form-control" id="searchText" name="searchText" value="${param.searchText}">
                    </div>
                    <button type="submit" class="btn btn-primary mb-2">Фильтровать</button>
                    <button class="btn btn-info mb-2 ml-2" onclick="$('#searchText').val('');location.href='${searchUrl}'">
                        Показать все
                    </button>
                </form:form>

                <table class="table">
                    <c:forEach items="${action.products}" var="product">
                        <tr>
                            <td>
                                ${product.name} <b>${product.quantity}</b>
                            </td>
                            <td>
                                <spring:url value="/action/${action.id}/takeGift/${product.id}" var="takeGiftUrl" />
                                <button class="btn btn-danger" onclick="post('${takeGiftUrl}', {searchText: '${param.searchText}'})">
                                    Забрать
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Акция не найдена</strong>
        </div>
    </c:otherwise>
</c:choose>
</div>
</body>
</html>