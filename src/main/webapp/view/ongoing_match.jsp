<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Матч</title>
    <link rel="stylesheet" type="text/css" href="css/ongoing_match.css">

</head>
<body>
    <h1 style="text-align: center">Матч</h1>

    <table class="score-table">
        <thead>
            <tr>
                <th>Имена игроков</th>
                <th>Сеты</th>
                <th>Геймы</th>
                <th>Очки</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="player-name">${player1.name}</td>
                <td>${score.player1Sets}</td>
                <td>${score.player1Games}</td>
                <td>${score.player1Points}</td>
            </tr>
            <tr>
                <td class="player-name">${player2.name}</td>
                <td>${score.player2Sets}</td>
                <td>${score.player2Games}</td>
                <td>${score.player2Points}</td>
            </tr>
        </tbody>
    </table>

    <div class="buttons-container">
        <form action="match-score?uuid=${matchId}" method="post">
            <input type="hidden" name="winnerId" value="${player1.id}">
            <button type="submit" class="action-button" id="player1-button">
                ${player1.name} выиграл очко
            </button>
        </form>

        <form action="match-score?uuid=${matchId}" method="post">
            <input type="hidden" name="winnerId" value="${player2.id}">
            <button type="submit" class="action-button" id="player2-button">
                ${player2.name} выиграл очко
            </button>
        </form>
    </div>

    <!-- Секция тай-брейка -->
            <c:if test="${score.extraRoundIsActive}">
                <div class="tiebreak-section">
                    <table class="tiebreak-table">
                        <thead>
                        <!-- <h2 style="text-align:center">Для победы нужно преимущество в 2 очка</h2> -->
                            <tr>
                                <th colspan="2">EXTRA ROUND!</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${player1.name}</td>
                                <td>${player2.name}</td>
                            </tr>
                            <tr>
                                <td>${score.extraPoints1}</td>
                                <td>${score.extraPoints2}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
</body>
</html>