<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Матч завершен</title>
    <link rel="stylesheet" type="text/css" href="css/finished_match.css">

</head>
<body>
    <div class="match-result">
        Матч завершен! <strong>${result.winner}</strong> победил!
    </div>

    <table class="result-table">
        <thead>
            <tr>
                <th>Игрок</th>
                <th>Сеты</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${result.player1}</td>
                <td>${result.player1Sets}</td>
            </tr>
            <tr>
                <td>${result.player2}</td>
                <td>${result.player2Sets}</td>
            </tr>
        </tbody>
    </table>

    <div class="buttons-container">
        <a href="new-match" class="nav-button" id="new-match">Новый матч</a>
        <a href="matches" class="nav-button" id="finished-matches">Завершенные матчи</a>
    </div>
</body>
</html>