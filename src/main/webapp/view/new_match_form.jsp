<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page isELIgnored="false" %>
        <!DOCTYPE html>
        <html lang="ru">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" type="text/css" href="css/match_form.css">
            <title>Создать матч</title>
        </head>

        <body>
            <form class="match-form" action="new-match" method="POST">
                <h1 class="form-title">Новый матч</h1>

                <div class="form-group">
                    <label for="player1">Имя игрока 1:</label>
                    <input type="text" id="player1" value="${playerOneName}" name="player1" required placeholder="Введите имя">
                </div>
                <div class="formRow">
                    <c:if test="${errors.playerOneNameNotValid}">
                        <div class="error"><span>${errors.playerOneError}</span></div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="player2">Имя игрока 2:</label>
                    <input type="text" id="player2" value="${playerTwoName}" name="player2" required placeholder="Введите имя">
                </div>
                <div class="formRow">
                    <c:if test="${errors.playerTwoNameNotValid}">
                        <div class="error"><span>${errors.playerTwoError}</span></div>
                    </c:if>
                </div>
                <c:if test="${errors.playerNamesAreSame}">
                    <div class="formRow">
                        <div class="error">${errors.commonError}</div>
                    </div>
                </c:if>

                <button type="submit">Начать матч</button>
            </form>
        </body>

        </html>