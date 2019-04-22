<#macro title>Welcome!</#macro>
<#macro stylesheets></#macro>
<#macro body></#macro>
<#macro javascripts></#macro>

<#macro display_page>
<!DOCTYPE html>
<html>
<head>
    <title><@title/></title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <@stylesheets/>
    <link rel="icon" type="image/x-icon" href="/resources/favicon.ico"/>
</head>

<body>
    <@body/>
    <@javascripts/>
</body>
</html>
</#macro>