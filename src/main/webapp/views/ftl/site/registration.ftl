<link href="/resources/css/signin.css" rel="stylesheet" media="screen">

<#if error??>
<div class="error_text">
    ${error}
</div>
</#if>

<form action="/registration" method="post">
    <label for="inputName" class="sr-only">Имя</label>
    <input name="username" value="" type="text" id="inputName" class="form-control" placeholder="Имя" required=""
           autofocus="">
    <label for="inputPassword" class="sr-only">Пароль</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Пароль" required="">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
    <button name="submit" class="login-button" type="submit">Зарегистрироваться</button>
</form>

<script type="text/javascript">
    var normalContentWidth = $('#content').width();

    function setNormalContentWidth() {
        $('.center-content').animate({
            width: normalContentWidth
        }, 200);
    }

    function setBroaderContentWidth() {
        $('.center-content').animate({
            width: $(window).width() * 0.4
        }, 200);
    }
</script>