@param String email
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>

    <style>
        body {
            background-color: #0c1621;
            font-family: system-ui, sans-serif;
            color: white;
            margin: 0;
            display: flex;
            justify-content: center;
            padding-top: 40px;
        }

        .card {
            background-color: #1f3c63;
            padding: 24px;
            width: 340px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.4);
        }

        .title {
            font-size: 22px;
            font-weight: 700;
            margin-bottom: 14px;
        }

        .detail {
            background-color: #2a5288;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        a.button {
            display: block;
            margin-top: 12px;
            background-color: #ff5c5c;
            color: white;
            text-align: center;
            padding: 10px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
        }

        a.button:hover {
            background-color: #ff2f2f;
        }
    </style>
</head>
<body>

<div class="card">
    <div class="title">Your Profile</div>

    <div class="detail">
        <strong>Email:</strong><br>
        ${email}
    </div>

    <a href="/" class="button" style="background-color:#3a8dff;">Back to Home</a>
    <a href="/logout" class="button">Log Out</a>
</div>

</body>
</html>
