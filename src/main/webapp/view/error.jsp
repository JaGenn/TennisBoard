<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ошибка</title>
    <link rel="stylesheet" type="text/css" href="css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>Упс! Что-то пошло не так</h1>
        <p><strong>Код ошибки:</strong> ${statusCode}</p>
        <p><strong>Описание:</strong> ${errorMessage}</p>
        <a href="/tennis_board">Вернуться на главную</a>
    </div>
</body>
</html>
