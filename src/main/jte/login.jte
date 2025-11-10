@param String error
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EarthConvo | LogIn</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
    />
    <style>
        body {
            background-color: lightblue;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            padding: 1rem;
            box-sizing: border-box;
        }
        h1{
            text-align: center;
            font-size: 30px;
            margin: 0.25rem 0;
        }
        h2{
            text-align: center;
            font-size: 19px;
            margin: 0 0 0.75rem 0;
        }
        .container{
            border: 2px solid black;
            width: 90%;
            max-width: 320px;
            background-color: rgb(145, 206, 244);
            border-radius: 10px;
            padding: 1rem;
            box-sizing: border-box;
        }
        h4{
            color: grey;
            text-align: center;
            font-size: 12px;
            margin-top: 1rem;
        }
        #continueButton{
            background-color: rgb(28, 29, 30);
            border: 1px solid black;
            border-radius: 5px;
            width: 100%;
            height: 40px;
            margin-top: 1em;
            color: white;
        }
        #exampleInputEmail1, #exampleInputPassword1{
            background-color: rgb(209, 214, 220);
            border: 1.5px solid black;
        }
        h5{
            font-size: 12px;
            color: rgb(206, 205, 205);
            margin-top: 1rem;
            padding-left: 0.5em;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div>
    <h1>EarthConvo</h1>
    <h2>Log in/Sign in</h2>
</div>
<div class="container bg-primary">
    <!-- Updated form to POST to /login -->
    <form action="/login" method="post">
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Email address</label>
            <input type="email" class="form-control" id="exampleInputEmail1" name="email" required>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" name="password" required>
        </div>
        <input type="submit" id="continueButton" value="Continue">
        <p class="error">${error}</p>
    </form>
    <div>
        <h5><u>Forgot Password?</u></h5>
    </div>
</div>
<div>
    <h4><u>Terms of Use</u> | <u>Privacy Policy</u></h4>
</div>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
></script>
</body>
</html>
