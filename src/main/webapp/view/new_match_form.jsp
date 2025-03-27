<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <input
                type="text"
                id="player1"
                name="player1"
                required
                placeholder="Введите имя"
            >
        </div>

        <div class="form-group">
            <label for="player2">Имя игрока 2:</label>
            <input
                type="text"
                id="player2"
                name="player2"
                required
                placeholder="Введите имя"
            >
        </div>

        <button type="submit">Начать матч</button>
    </form>
</body>
</html>