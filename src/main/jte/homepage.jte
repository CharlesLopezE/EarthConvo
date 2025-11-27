@param String userEmail
@param String question
@param String answer
@param String theme
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

            /* Themes */

            body.dark {
                background-color: #0c1621;
                color: #eee;
            }

            body.light {
                background-color: #f2f4fb;
                color: #111;
            }

            body.light .app { background:#fff }
            body.light .sidebar { background:#dbe6f7 }
            body.light .top-bar { background:#cfd9eb }
            body.light .main { background:white }
            body.light .content { background:#eef2f8 }
            body.light .content-box { background:white }
            body.light .input-bar { background:#cfd9eb }
            body.light .message.user { background:#cddbf2 }
            body.light .message.ai { background:#f1f4fb }

            body.dark .app { background:#171616}
            body.dark .sidebar { background: #0b0909}
            body.dark .top-bar { background:#171616}
            body.dark .main { background:black}
            body.dark .content { background: #292828}
            body.dark .content-box { background:black}
            body.dark .input-bar { background:#171616}
            body.dark .message.user { background: #070606}
            body.dark .message.ai { background:#171616}

            body.default {
                background-color: #0c1621;
                color: black;
            }

            body.default .app { background:#1e4f94 }
            body.default .sidebar { background:#2a8dff }
            body.default .top-bar { background:blue }
            body.default .main { background:#3f6fa9 }
            body.default .content { background:lightblue }
            body.default .content-box { background:#97bdee }
            body.default .input-bar { background:blue }
            body.default .message.user { background:#3f6fa9 }
            body.default .message.ai { background:#d9e6f4 }



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

            .dropdown {
                position: relative;
                width: 100%;
            }

            .dropdown-menu {
                display: none;
                flex-direction: column;
                position: absolute;
                top: 100%;
                background-color: #1c5ba3;
                border: 2px solid #0d2740;
                border-radius: 6px;
                margin-top: 3px;
                width: 100%;
                padding: 12px 0;
            }

            .dropdown-menu a {
                padding: 8px 12px;
                color: #f0f6ff;
                font-size: 13px;
                text-decoration: none;
                display: block;
            }

            .dropdown-menu a:hover {
                background-color: #2a8dff;
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

            .sidebar-settings {
                margin-top: auto; /* pushes it to the bottom of sidebar */
                display: flex;
                justify-content: flex-start; /* align left instead of center */
                padding-left: 3px; /* optional: add a little padding from the edge */
            }

            .sidebar-settings button {
                width: auto; /* fit content instead of full width */
                border-radius: 24px;
                border: 2px solid #0d2740;
                background-color: #2f7ae0;
                color: #ffffff;
                padding: 8px 12px;
                font-size: 14px;
                font-weight: 600;
                cursor: pointer;
            }
            .sidebar-settings button:hover {
                background-color: #1c5ba3;
            }

            /* Overlay & Modal */
            .settings-modal {
                display: none; /* hidden by default */
                position: fixed;
                top: 0; left: 0;
                width: 100%;
                height: 100%;
                z-index: 1000;
                justify-content: center;
                align-items: center;
            }

            .settings-modal.show {
                display: flex; /* show modal */
            }

            .settings-overlay {
                position: absolute;
                top: 0; left: 0;
                width: 100%; height: 100%;
                background-color: rgba(0,0,0,0.5); /* dim background */
            }

            .settings-content {
                position: relative;
                background-color: #1e4f94;
                color: #fff;
                border-radius: 8px;
                width: 400px;
                max-width: 90%;
                padding: 20px;
                z-index: 1001;
                box-shadow: 0 5px 25px rgba(0,0,0,0.3);
            }

            .settings-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                font-weight: 600;
                font-size: 18px;
                margin-bottom: 16px;
            }

            .settings-body .settings-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 0;
                border-bottom: 1px solid rgba(255,255,255,0.3);
            }

            .settings-body .settings-row:last-child {
                border-bottom: none;
            }

            .settings-body button {
                background: none;
                border: none;
                color: #fff;
                font-size: 16px;
                cursor: pointer;
            }

            .settings-close {
                background: none;
                border: none;
                color: #fff;
                font-size: 20px;
                cursor: pointer;
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
                background-color: blue;
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
                background-color: lightblue;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .content-box {
                width: 75%;
                height: 75%;
                background-color: #97bdee;
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
            .typing::after {
                content: "";
                animation: dots 1.2s infinite;
            }

            @keyframes dots {
                0% { content: ""; }
                33% { content: "."; }
                66% { content: ".."; }
                100% { content: "..."; }
            }

            .input-bar {
                padding: 14px 20px;
                background-color: blue;
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
    <body class="${theme}">
    <div class="app">

        <!-- SIDEBAR -->
        <aside class="sidebar">
            <div class="sidebar-top-buttons">
                <button type="button" onclick="clearMainArea()">
                    <span>New Chat</span>
                    <span class="icon">✚</span>
                </button>


                <div class="sidebar-select-row">
                    <form action="/language" method="get" style="flex: 1; display: inline-flex;">
                        <button type="submit" class="secondary small">
                            <span>Language</span>
                        </button>
                    </form>
                    <div class="dropdown">
                        <button class="secondary small" type="button" onclick="toggleDropdown()">
                            <span>Saves</span>
                            <span class="icon">▾</span>
                        </button>
                        <div id="saves-menu" class="dropdown-menu">
                            <a href="/saved-lessons">Lessons</a>
                            <a href="/saved-sentences">Sentences</a>
                            <a href="/saved-vocab">Vocabulary</a>
                        </div>
                    </div>
                </div>
            </div>

            <div>
                <div class="sidebar-section-title">History</div>
                <div class="sidebar-history">
                    @if(history != null && !history.isEmpty())
                        @for(edu.csula.cs3220stu12.earthconvo.ChatMessage msg : history)
                            <div class="message ai" style="font-size:12px; margin-bottom:6px;">
                                ${msg.getAiSummary()}
                            </div>
                        @endfor
                    @else
                        <p class="placeholder">No history yet.</p>
                    @endif
                </div>
            </div>

            <div class="sidebar-delete">
                <button type="button" onclick="clearHistory()">Delete</button>
            </div>

            <div class="sidebar-settings">
                <button type="button" onclick="openSettings()">
                    <i class="bi bi-gear-fill"></i> ⚙ Settings
                </button>
            </div>

            <!-- SETTINGS MODAL -->
            <div id="settings-modal" class="settings-modal">
                <div class="settings-overlay" onclick="closeSettings()"></div>
                <div class="settings-content">
                    <div class="settings-header">
                        <span>Settings</span>
                        <button class="settings-close" onclick="closeSettings()">✕</button>
                    </div>
                    <div class="settings-body">
                        <div class="settings-row">
                            <span>Theme ➤</span>

                            <form action="/theme" method="post" style="display:flex; gap:8px;">

                                @if("default".equals(theme))
                                    <label>
                                        <input type="radio" name="theme" value="default" checked>
                                        Default
                                    </label>
                                @else
                                    <label>
                                        <input type="radio" name="theme" value="default">
                                        Dark
                                    </label>
                                @endif

                                @if("dark".equals(theme))
                                    <label>
                                        <input type="radio" name="theme" value="dark" checked>
                                        Dark
                                    </label>
                                @else
                                    <label>
                                        <input type="radio" name="theme" value="dark">
                                        Dark
                                    </label>
                                @endif

                                @if("light".equals(theme))
                                    <label>
                                        <input type="radio" name="theme" value="light" checked>
                                        Light
                                    </label>
                                @else
                                    <label>
                                        <input type="radio" name="theme" value="light">
                                        Light
                                    </label>
                                @endif

                                <button type="submit" style="margin-left:6px; border: 2px solid #ffffff; padding: 6px 12px; justify-content: center;">Save</button>

                            </form>
                        </div>

                        <div class="settings-row">
                            <span>Language</span>
                            <button onclick="alert('Language clicked')">➤</button>
                        </div>
                        <div class="settings-row">
                            <span>About us</span>
                            <button onclick="alert('About us clicked')">➤</button>
                        </div>
                    </div>
                </div>
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
                        @if(history != null && !history.isEmpty())
                            @for(edu.csula.cs3220stu12.earthconvo.ChatMessage msg : history)
                                <div class="message user">
                                    <strong>You:</strong> ${msg.getUserMessage()}
                                </div>
                                <div class="message ai">
                                    <strong>EarthConvo:</strong> ${msg.getAiResponse()}
                                </div>
                            @endfor
                        @elseif(question != null && answer != null)
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
                <button class="clear-btn" type="button" onclick="clearMainArea()">Clear</button>

                <form class="input-form" action="/ask" method="post" onsubmit="showLoading()">
                    <input type="text" name="prompt" placeholder="Ask anything" autocomplete="off" required>
                    <button type="button" class="x-btn" onclick="this.form.prompt.value=''">×</button>
                    <button type="submit">➜</button>
                </form>
            </footer>

            <script>
                function showLoading() {
                    const convo = document.querySelector('.conversation');
                    convo.innerHTML = `
                    <div class="message ai">
                        <strong>EarthConvo:</strong> <span class="typing">Typing</span>
                    </div>
                `;
                }
                function toggleDropdown() {
                    const menu = document.getElementById("saves-menu");
                    menu.style.display = menu.style.display === "flex" ? "none" : "flex";
                }

                function clearHistory() {
                    fetch('/clear-history', { method: 'POST' })
                        .then(() => window.location.reload());
                }
                function clearMainArea() {
                    const convo = document.querySelector('.conversation');
                    convo.innerHTML = `
                    <p class="placeholder">
                        Ask EarthConvo anything to start practicing English.
                    </p>
                   `;
                }
                function openSettings() {
                    document.getElementById('settings-modal').classList.add('show');
                }

                function closeSettings() {
                    document.getElementById('settings-modal').classList.remove('show');
                }
            </script>

        </div>
    </div>
    </body>
    </html>
