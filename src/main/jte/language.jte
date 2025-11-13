@param String currentLanguage
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Select Language</title>
    <style>
        body {
            background-color: #0c1621;
            font-family: system-ui, sans-serif;
            color: white;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 40px;
        }

        .card {
            background-color: #1f3c63;
            padding: 24px;
            width: 360px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.4);
        }

        .title {
            font-size: 22px;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .subtitle {
            font-size: 14px;
            opacity: 0.85;
            margin-bottom: 18px;
        }

        .option {
            margin-bottom: 10px;
        }

        .buttons {
            margin-top: 16px;
            display: flex;
            gap: 10px;
        }

        button, a.button-link {
            border: none;
            border-radius: 8px;
            padding: 10px 14px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
        }

        button {
            background-color: #3a8dff;
            color: white;
        }

        button:hover {
            background-color: #1f6fe0;
        }

        a.button-link {
            background-color: #444b5a;
            color: white;
        }

        a.button-link:hover {
            background-color: #2c3240;
        }

        .current {
            margin-top: 16px;
            font-size: 14px;
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="card">
    <div class="title">Choose Language</div>
    <div class="subtitle">
        Select the language EarthConvo should mainly use.
    </div>

    <form action="/language" method="post">
        <div class="option">
            <label>
                @if("English".equals(currentLanguage))
                    <input type="radio" name="language" value="English" checked>
                @else
                    <input type="radio" name="language" value="English">
                @endif
                English
            </label>
        </div>

        <div class="option">
            <label>
                @if("Spanish".equals(currentLanguage))
                    <input type="radio" name="language" value="Spanish" checked>
                @else
                    <input type="radio" name="language" value="Spanish">
                @endif
                Spanish
            </label>
        </div>

        <div class="option">
            <label>
                @if("Japanese".equals(currentLanguage))
                    <input type="radio" name="language" value="Japanese" checked>
                @else
                    <input type="radio" name="language" value="Japanese">
                @endif
                Japanese
            </label>
        </div>

        <div class="buttons">
            <button type="submit">Save</button>
            <a href="/" class="button-link">Cancel</a>
        </div>
    </form>

    <div class="current">
        Current language: <strong>${currentLanguage}</strong>
    </div>
</div>
</body>
</html>
