<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        .avatar {
            display: inline-grid;
            place-items: center;
            width: 28px;
            height: 28px;
            border-radius: 50%;
            background: rgba(255,255,255,.2);
            font-weight: 700;
        }

        @media (max-width: 576px) {
            #vocabTable td, #vocabTable th {
                white-space: nowrap;
            }
            #vocabTable .text-end {
                white-space: normal;
            }
        }
    </style>
</head>

<body style="background-color:lightblue">

<!-- 🔹 Navbar identical to saved-sentences -->
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
                <span class="badge text-bg-dark text-uppercase">Saved Vocabulary</span>

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


<!-- 🔹 Page Body -->
<main class="container my-4">
    <p class="text-muted small mb-3 fw-bold">Saved Words</p>

    <div class="card shadow-sm">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table id="vocabTable" class="table table-dark table-striped align-middle mb-0">
                    <thead>
                    <tr>
                        <th class="w-50">English</th>
                        <th class="w-50 target-lang-header">Spanish</th>
                        <th class="text-end pe-3">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Hello</td>
                        <td class="target-lang">Hola</td>
                        <td class="text-end">
                            <button class="btn btn-danger btn-sm delete-row"><i class="bi bi-trash"></i> Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>Beautiful</td>
                        <td class="target-lang">Hermosa</td>
                        <td class="text-end">
                            <button class="btn btn-danger btn-sm delete-row"><i class="bi bi-trash"></i> Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>School</td>
                        <td class="target-lang">Escuela</td>
                        <td class="text-end">
                            <button class="btn btn-danger btn-sm delete-row"><i class="bi bi-trash"></i> Delete</button>
                        </td>
                    </tr>

                    <!-- placeholder rows -->
                    <tr>
                        <td class="text-secondary">—</td><td class="text-secondary">—</td>
                        <td class="text-end"><button class="btn btn-danger btn-sm" disabled>Delete</button></td>
                    </tr>
                    <tr>
                        <td class="text-secondary">—</td><td class="text-secondary">—</td>
                        <td class="text-end"><button class="btn btn-danger btn-sm" disabled>Delete</button></td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>

        <div class="card-footer d-flex justify-content-between align-items-center">
            <div class="btn-group" role="group" aria-label="Tabs">
                <button class="btn btn-outline-secondary btn-sm">Menu</button>
                <button class="btn btn-outline-secondary btn-sm">Tab</button>
            </div>
            <a class="btn btn-primary btn-sm" href="#" role="button">
                <i class="bi bi-arrow-right-circle"></i> Go to AI Interaction
            </a>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.querySelectorAll('.delete-row').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const row = e.currentTarget.closest('tr');
            row.remove();
        });
    });
</script>

</body>
</html>
