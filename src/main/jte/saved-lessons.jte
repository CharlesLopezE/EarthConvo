@param java.util.List<String> lessons
@param String userEmail
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Saved Lessons</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="lessons.css" rel="stylesheet">

    <style>
        .lesson-card {
            background: #111;
            border-radius: 14px;
            padding: 16px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.4);
            margin-bottom: 16px;
        }

        .lesson-body {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 16px;
        }

        .lesson-title {
            color: white;
            font-weight: 700;
            margin-bottom: 4px;
        }

        .lesson-text {
            color: #a9b6c3;
            font-size: 14px;
            line-height: 1.4;
            max-height: 60px;              /* collapsed height */
            overflow: hidden;
            position: relative;
            transition: max-height 0.4s ease;
        }

        /* fade effect */
        .lesson-text::after {
            content: "";
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 35px;
            background: linear-gradient(transparent, #111);
        }

        /* expanded state */
        .lesson-text.expanded {
            max-height: 600px;
        }

        /* hide fade when expanded */
        .lesson-text.expanded::after {
            display: none;
        }


        /* Buttons */
        .lesson-actions {
            display: flex;
            gap: 8px;
        }

        .btn-expand {
            background: #6c757d;
            color: white;
            border: none;
            border-radius: 6px;
            padding: 6px 12px;
            font-size: 13px;
        }

        .btn-delete {
            background: #dc3545;
            color: white;
            border: none;
            border-radius: 6px;
            padding: 6px 12px;
            font-size: 13px;
        }

        .btn-expand:hover { background: #5a6268; }
        .btn-delete:hover { background: #bb2d3b; }

        body{
            background: radial-gradient(circle at top left, #a0c4ff, #1e3a8a);
        }
        .avatar{
            display: inline-grid;
            place-items: center;
            width: 28px;
            height: 28px;
            border-radius: 50%;
            font-size: 12px;
            color: white;
            background: #072a7e;
        }
    </style>
</head>
<body class="app-shell">

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container-fluid">
        <button class="btn btn-light btn-sm me-2" onclick="history.back()">
            <i class="bi bi-arrow-left"></i>
        </button>

        <a class="navbar-brand fw-semibold" href="#">
            <span class="avatar me-2">${userEmail != null && !userEmail.isEmpty() ? userEmail.substring(0,1).toUpperCase() : "C"}</span>
            EarthConvo
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#topbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div id="topbar" class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" href="#">Saves</a></li>
                <li class="nav-item"><a class="nav-link" href="/saved-lessons">Lessons</a></li>
                <li class="nav-item"><a class="nav-link" href="/saved-sentences">Sentences</a></li>
                <li class="nav-item"><a class="nav-link" href="/saved-vocab">Vocabulary</a></li>
            </ul>

            <div class="d-flex align-items-center gap-2">
                <span class="badge text-bg-dark text-uppercase">Saved Lessons</span>

                <div class="btn-group">
                    <button class="btn btn-outline-light btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-translate me-1"></i> Select Language
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item lang-choice" data-lang="es" href="#">Spanish</a></li>
                        <li><a class="dropdown-item lang-choice" data-lang="fr" href="#">French</a></li>
                        <li><a class="dropdown-item lang-choice" data-lang="de" href="#">German</a></li>
                        <li><a class="dropdown-item lang-choice" data-lang="zh" href="#">Chinese (Simplified)</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<main class="container my-3">
    <h1 class="h5 mb-1">Saved Lessons</h1>
    <p class="text-muted small mb-3">Your saved lessons are listed below.</p>

    @if(lessons != null && !lessons.isEmpty())
        @for(String lesson : lessons)
            <div class="lesson-card">
                <div class="lesson-body">

                    <div>
                        <div class="lesson-title">Lesson</div>
                        <div class="lesson-text">${lesson}</div>
                    </div>
                    <div class="lesson-actions">
                        <button type="button" class="btn-expand" onclick="toggleExpand(this)">Expand</button>
                        <form method="post" action="/delete-lesson" style="margin:0;">
                            <input type="hidden" name="lesson" value="${lesson}">
                            <button type="submit" class="btn-delete">Delete</button>
                        </form>
                    </div>

                </div>
            </div>
        @endfor
    @else
        <p class="text-muted">No lessons saved yet.</p>
    @endif
    <div class="card-footer d-flex justify-content-between align-items-center">
        <a class="btn btn-primary btn-sm" href="/" role="button">
            <i class="bi bi-arrow-right-circle"></i> Go to AI Interaction
        </a>
    </div>
</main>

<footer class="py-3 text-center text-muted small">© 2025 AI Language App</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function toggleExpand(btn) {
        const text = btn.closest('.lesson-card').querySelector('.lesson-text');

        if (text.classList.contains("expanded")) {
            text.classList.remove("expanded");
            btn.textContent = "Expand";
        } else {
            text.classList.add("expanded");
            btn.textContent = "Collapse";
        }
    }
</script>
</body>
</html>
