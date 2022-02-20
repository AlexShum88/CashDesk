<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <script >

        function validatePass(){

            var pass = document.getElementById("pass");
            var confPass = document.getElementById("confPass");
            var confMessage = document.getElementById("confMessage");
            var regButt = document.getElementById("reg");


            if (pass.value!=confPass.value){
                confMessage.innerText = "passwords not matched";
                regButt.disabled = true;
            } else {
            confMessage.innerText = "passwords matched!";
            regButt.disabled = false;
            };

        }
    </script>
</head>
<body>
<br>
<hr>
<br>
<form action="/CashDesk/registrationS" method="post">
    <label>Login</label><br>
    <input type="text" required minlength="6" maxlength="16" name="login" pattern="[A-Z,a-z, А-Я,а-я][a-z,а-я]+"><br>
    <label>Password</label><br>
    <input type="password" required min="6" maxlength="30" id="pass" name="password" pattern="[A-Z,a-z, А-Я,а-я,0-9][a-z,а-я,0-9]+"><br>
    <label>Conform password</label><br>
    <input type="password" required min="6" maxlength="30" id="confPass" oninput="return validatePass()" ><br>
    <br>
    <label id="confMessage" ></label>
    <br>
    <br>
    <input type="submit" value="Registration" id="reg" disabled><br>
</form>
<br>
<hr>
<br>
</body>


</html>

