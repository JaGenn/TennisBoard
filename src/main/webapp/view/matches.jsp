<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <title>Завершенные матчи</title>
    <link rel="stylesheet" type="text/css" href="css/matches.css">
</head>
<body>
    <h1 style="text-align: center;">Завершенные матчи</h1>

    <!-- Форма фильтрации -->
    <div class="filter-section">
        <form action="matches" method="GET" style="display: flex; gap: 1rem;">
            <input type="text"
                   name="filter_by_player_name"
                   placeholder="Имя игрока"
                   value="${fn:escapeXml(filterPlayerName)}">
            <button type="submit">Поиск</button>
            <a href="matches" class="clear-btn">Сброс</a>
        </form>
    </div>

    <!-- Таблица матчей -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Игрок 1</th>
                <th>Игрок 2</th>
                <th>Победитель</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${matches}" var="match">
                <tr>
                    <td>${match.id}</td>
                    <td>${fn:escapeXml(match.player1.name)}</td>
                    <td>${fn:escapeXml(match.player2.name)}</td>
                    <td>${fn:escapeXml(match.winner.name)}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="matches?page=${currentPage - 1}&filter_by_player_name=${fn:escapeXml(filterPlayerName)}">← Назад</a>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span style="padding: 0.5rem 1rem; background-color: #3498db; color: white; border-radius: 4px;">${i}</span>
                </c:when>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="matches?page=${currentPage + 1}&filter_by_player_name=${fn:escapeXml(filterPlayerName)}">Вперед →</a>
        </c:if>
    </div>
        <a href="new-match" class="nav-button" id="new-match">Новый матч</a>
</body>
</html>