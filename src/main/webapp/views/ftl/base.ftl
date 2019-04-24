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
    <link rel="apple-touch-icon" sizes="57x57" href="/resources/icon/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/resources/icon/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/resources/icon/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/resources/icon/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/resources/icon/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/resources/icon/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/resources/icon/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/resources/icon/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/resources/icon/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192"  href="/resources/icon/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/icon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="/resources/icon/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/icon/favicon-16x16.png">
    <link rel="manifest" href="/resources/icon/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
</head>

<body>
    <@body/>
    <@javascripts/>
</body>
</html>
</#macro>