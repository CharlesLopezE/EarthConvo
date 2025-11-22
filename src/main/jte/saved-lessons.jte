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
</head>
<body class="app-shell" style="background-color: lightblue;">

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container-fluid">
        <button class="btn btn-light btn-sm me-2" onclick="history.back()">
            <i class="bi bi-arrow-left"></i> Return Home
        </button>

        <a class="navbar-brand fw-semibold" href="#">
            <span class="avatar me-2">C</span>
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
            <div class="panel-dark p-3 mb-3">
                <div class="d-flex justify-content-between align-items-start gap-3">
                    <div>
                        <div class="fw-semibold small">Lesson</div>
                        <div class="small text-secondary">
                            ${lesson}
                        </div>
                    </div>
                    <div class="d-flex gap-2">
                        <button class="btn btn-secondary btn-sm">Expand</button>
                        <button class="btn btn-danger btn-sm">Delete</button>
                    </div>
                </div>
            </div>
        @endfor
    @else
        <p class="text-muted">No lessons saved yet.</p>
    @endif
</main>

<footer class="py-3 text-center text-muted small">© 2025 AI Language App</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
