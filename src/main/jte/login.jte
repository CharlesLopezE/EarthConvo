@param String error
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<style>
    body {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 100vh;
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
    }
</style>
<body>
<h1>EarthConvo</h1>
<p>Sign/Login</p>
<form action="/login" method="post">
    <label>Username:</label>
    <label>
        <input type="text" name="username" required>
    </label><br><br>
    <label>Password:</label>
    <label>
        <input type="password" name="password" required>
    </label><br><br>
    <input type="submit" value="Login">
</form>

<p style="color:red;">
    ${error}
</p>
</body>
</html>
