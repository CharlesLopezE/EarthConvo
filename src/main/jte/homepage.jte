@param String username
@param String question
@param String answer
@param String userEmail
@param java.util.List<edu.csula.cs3220stu12.earthconvo.ChatMessage> history
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EarthConvo | Home</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
        }

        body {
            background-color: #0c1621;
            min-height: 100vh;
            display: flex;
            align-items: stretch;
            justify-content: center;
        }

        .app {
            background-color: #1e4f94;
            margin: 16px;
            flex: 1;
            display: flex;
            border-radius: 4px;
            overflow: hidden;
        }

        /* ---------- SIDEBAR ---------- */

        .sidebar {
            width: 210px;
            background-color: #2a8dff;
            padding: 16px 12px;
            display: flex;
            flex-direction: column;
            gap: 16px;
        }

        .sidebar-top-buttons {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .sidebar button {
            border-radius: 24px;
            border: 2px solid #0d2740;
            background-color: #429dff;
            padding: 6px 14px;
            font-size: 14px;
            font-weight: 600;
            color: #0d1b2a;
            display: inline-flex;
            align-items: center;
            justify-content: space-between;
            width: 100%;
            cursor: pointer;
        }

        .sidebar button .icon {
            font-size: 16px;
            font-weight: 700;
        }

        .sidebar button.secondary {
            background-color: #2f7ae0;
        }

        .sidebar button.small {
            font-size: 13px;
        }

        .sidebar-select-row {
            display: flex;
            gap: 8px;
        }

        .sidebar-section-title {
            font-size: 16px;
            font-weight: 600;
            margin-top: 12px;
            margin-bottom: 8px;
        }

        .sidebar-history {
            flex: 1;
            border-radius: 4px;
            border: 2px solid #0d2740;
            background-color: #2d67a7;
            padding: 8px;
            font-size: 13px;
            overflow-y: auto;
        }

        .history-item {
            background-color: white;
            border-radius: 6px;
            padding: 6px 8px;
            margin-bottom: 6px;
            max-height: 60px;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .history-empty {
            opacity: 0.7;
            font-size: 13px;
        }

        .sidebar-delete {
            margin-top: 12px;
        }

        .sidebar-delete button {
            background-color: #8b1e5d;
            color: #f8f9ff;
            border-color: #410425;
        }

        /* ---------- MAIN AREA ---------- */

        .main {
            flex: 1;
            display: flex;
            flex-direction: column;
            background-color: #3f6fa9;
        }

        /* top bar */

        .top-bar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 18px;
            background-color: #3f6fa9;
        }

        .profile {
            display: flex;
            align-items: center;
            gap: 10px;
            cursor: pointer;
        }

        .profile-bubble {
            width: 32px;
            height: 32px;
            border-radius: 999px;
            background-color: #222f3b;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #f5f5f5;
            font-weight: 700;
        }

        .profile-text-main {
            font-size: 16px;
            font-weight: 600;
        }

        .profile-text-sub {
            font-size: 13px;
        }

        .top-bar-title-right {
            font-size: 16px;
            font-weight: 600;
        }

        /* chat area */

        .content {
            flex: 1;
            padding: 20px;
            background-color: #6e92ba;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .content-box {
            width: 75%;
            height: 75%;
            background-color: #87a4c4;
            border-radius: 2px;
            position: relative;
            padding: 16px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .conversation {
            max-height: 100%;
            overflow-y: auto;
        }

        .message {
            padding: 8px 10px;
            margin-bottom: 8px;
            border-radius: 8px;
            font-size: 14px;
            line-height: 1.4;
        }

        .message.user {
            background-color: #3f6fa9;
            align-self: flex-end;
        }

        .message.ai {
            background-color: #d9e6f4;
            align-self: flex-start;
        }

        .placeholder {
            font-size: 14px;
            opacity: 0.85;
        }

        /* input bar */

        .input-bar {
            padding: 14px 20px;
            background-color: #3f6fa9;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .clear-btn {
            background-color: #151921;
            color: #ffffff;
            border-radius: 8px;
            border: none;
            padding: 10px 16px;
            font-size: 14px;
            cursor: pointer;
        }

        .input-form {
            flex: 1;
            display: flex;
            align-items: center;
            gap: 8px;
            background-color: #71cfe0;
            border-radius: 999px;
            padding: 4px 10px;
            border: 2px solid #a6e9f3;
        }

        .input-form input[type="text"] {
            flex: 1;
            border: none;
            outline: none;
            background: transparent;
            padding: 6px 4px;
            font-size: 14px;
        }

        .input-form button {
            border: none;
            background: transparent;
            cursor: pointer;
            font-size: 16px;
        }

        .input-form .x-btn {
            font-weight: 700;
        }
    </style>
</head>
<body>
<div class="app">

    <!-- SIDEBAR -->
    <aside class="sidebar">
        <div class="sidebar-top-buttons">
            <button type="button" onclick="window.location='/'">
                <span>New Chat</span>
                <span class="icon">✚</span>
            </button>

            <div class="sidebar-select-row">
                <!-- Language button links to /language -->
                <form action="/language" method="get" style="flex: 1; display: inline-flex;">
                    <button type="submit" class="secondary small">
                        <span>Language</span>
                        <span class="icon">▾</span>
                    </button>
                </form>

                <button class="secondary small" type="button">
                    <span>Saves</span>
                    <span class="icon">▾</span>
                </button>
            </div>
        </div>

        <div>
            <div class="sidebar-section-title">History</div>
            <div class="sidebar-history">
                @if(history != null && !history.isEmpty())
                    @for(edu.csula.cs3220stu12.earthconvo.ChatMessage msg : history)
                        <div class="message ai" style="font-size:12px; margin-bottom:6px;">
                            <strong>EarthConvo:</strong> ${msg.getAiSummary()}
                        </div>
                    @endfor
                @else
                    <p class="placeholder">No history yet. Ask something to start.</p>
                @endif
            </div>
        </div>

        <div class="sidebar-delete">
            <button type="button">
                <span>Delete</span>
            </button>
        </div>
    </aside>

    <!-- MAIN AREA -->
    <div class="main">

        <!-- TOP BAR -->
        <header class="top-bar">
            <div class="profile" onclick="window.location='/profile'">
                <div class="profile-bubble">
                    ${userEmail != null && !userEmail.isEmpty() ? userEmail.substring(0,1).toUpperCase() : "C"}
                </div>
                <div>
                    <div class="profile-text-main">Welcome</div>
                    <div class="profile-text-sub">${userEmail}</div>
                </div>
            </div>

            <div class="top-bar-title-right">EarthConvo</div>
        </header>

        <!-- CONTENT AREA -->
        <section class="content">
            <div class="content-box">
                <div class="conversation">
                    @if(answer != null)
                        <div class="message user">
                            <strong>You:</strong> ${question}
                        </div>
                        <div class="message ai">
                            <strong>EarthConvo:</strong> ${answer}
                        </div>
                    @else
                        <p class="placeholder">
                            Ask EarthConvo anything to start practicing English.
                        </p>
                    @endif
                </div>
            </div>
        </section>

        <!-- INPUT BAR -->
        <footer class="input-bar">
            <button class="clear-btn" type="button"
                    onclick="window.location='/'">Clear</button>

            <!-- AI form -->
            <form class="input-form" action="/ask" method="post">
                <input type="text" name="prompt" placeholder="Ask anything" autocomplete="off" required>
                <button type="button" class="x-btn"
                        onclick="this.form.prompt.value=''">×</button>
                <button type="submit">➜</button>
            </form>
        </footer>

    </div>
</div>
</body>
</html>

