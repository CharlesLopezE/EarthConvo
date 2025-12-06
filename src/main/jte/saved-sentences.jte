@param java.util.List<String> sentences
@param String userEmail

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Saved Sentences</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        .avatar {
            display: inline-grid;
            place-items: center;
            width: 28px;
            height: 28px;
            border-radius: 50%;
            font-size: 12px;
            color: white;
            background: #072a7e;
        }

        .app-shell {
            min-height: 100vh;
            background: radial-gradient(circle at top left, #a0c4ff, #1e3a8a);
        }

        .card {
            border-radius: 16px;
            overflow: hidden;
        }

        .table-dark {
            --bs-table-bg: #1f2937;
            --bs-table-striped-bg: #111827;
        }

        .target-lang-header {
            color: #93c5fd;
        }

        .delete-row {
            display: inline-flex;
            align-items: center;
            gap: 4px;
        }

        .delete-row i {
            font-size: 14px;
        }

        .navbar-brand span {
            font-weight: 600;
        }

        .theme-pill {
            border-radius: 999px;
            padding: 2px 8px;
            background: rgba(15,23,42,0.1);
            font-size: 11px;
        }

        @media (max-width: 576px) {
            #sentencesTable td, #sentencesTable th {
                white-space: nowrap;
            }
            #sentencesTable .text-end {
                white-space: normal;
            }
        }
    </style>
</head>

<body class="app-shell" style="background-color: lightblue;">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
    <div class="container-fluid">
        <button class="btn btn-light btn-sm me-2" onclick="history.back()">
            <i class="bi bi-arrow-left"></i>
        </button>

        <a class="navbar-brand d-flex align-items-center" href="#">
            <span class="avatar me-2">
                ${userEmail != null && !userEmail.isEmpty() ? userEmail.substring(0,1).toUpperCase() : "E"}
            </span>
            <span>EarthConvo</span>
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

            <div class="ms-auto d-flex align-items-center">
                <span class="theme-pill text-light me-2">Saved Sentences</span>
            </div>
        </div>
    </div>
</nav>

<main class="container my-4">
    <h1 class="h5 mb-1">Saved Sentences</h1>
    <p class="text-muted small mb-3">Your saved sentences are listed below.</p>

    @if(sentences != null && !sentences.isEmpty())
        @for(String lesson : sentences)
            <div class="card shadow-sm mb-3">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table id="sentencesTable" class="table table-dark table-striped align-middle mb-0">
                            <thead>
                            <tr>
                                <th class="w-50">English</th>
                                <th class="w-50 target-lang-header">Spanish</th>
                                <th class="text-end pe-3">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <!-- ENGLISH column -->
                                <td>
                                    ${
                                            // Case 1: "Buenos días. (Good morning.)"

                                              // Strip leading "- " or "-".
                                            (
                                                (lesson.contains("(") && lesson.contains(")"))
                                                    ? lesson.substring(0, lesson.indexOf("("))
                                                    : (lesson.contains(" - ")
                                                        ? lesson.substring(0, lesson.indexOf(" - "))
                                                        : lesson
                                                      )
                                            ).replaceFirst("^-\\s*", "").trim()
                                        }
                                </td>

                                <!-- SPANISH column -->
                                <td class="target-lang">
                                    ${
                                            (lesson.contains("(") && lesson.contains(")"))
                                                ? lesson.substring(
                                                    lesson.indexOf("(") + 1,
                                                    lesson.lastIndexOf(")")
                                                  ).trim()
                                            // Case 2: "Buenos días. - Good morning."
                                            : (lesson.contains(" - ")
                                                ? lesson.substring(
                                                    lesson.indexOf(" - ") + 3
                                                  ).trim()
                                                // Fallback: no clear English → empty
                                                : ""
                                              )
                                        }
                                </td>

                                <td class="text-end">
                                    <form method="post" action="/delete-sentence" style="margin:0;">
                                        <input type="hidden" name="userEmail" value="${userEmail}">
                                        <input type="hidden" name="sentence" value="${lesson}">
                                        <button class="btn btn-danger btn-sm delete-row">
                                            <i class="bi bi-trash"></i> Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        @endfor
    @else
        <p class="text-mute">No sentences saved yet.</p>
    @endif

    <div class="card-footer d-flex justify-content-between align-items-center mt-3">
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
