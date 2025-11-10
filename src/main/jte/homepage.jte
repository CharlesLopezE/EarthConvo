@param String username
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EarthConvo</title>
</head>
<body>
<h1>Welcome, ${username}</h1>

<form action="/joke" method="post">
    <label for="topic">Please tell a joke about</label>
    <select id="topic" name="topic">
        <option value="politics">politics</option>
        <option value="gaming">gaming</option>
        <option value="monster hunter">monster hunter</option>
        <option value="college">college</option>
    </select>
    <input type="submit" value="Go">
</form>

<p>
    <a href="/chat-history">View Chat History</a> |
    <a href="/saved-lessons">View Saved Jokes</a> |
    <a href="/logout">Logout</a>
</p>
</body>
</html>
