@param String username
@param java.util.List<String> jokes

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${username}'s Saved Jokes</title>
</head>
<body>
<h1>${username}'s Saved Jokes</h1>

@if(jokes != null && !jokes.isEmpty())
    <ul>
        @for(String j : jokes)
            <li>${j}</li>
        @endfor
    </ul>
@else
    <p>No jokes saved yet.</p>
@endif

<p>
    <a href="/">Back to Home</a> |
    <a href="/logout">Logout</a>
</p>
</body>
</html>