<#include '../base.ftl'>

<#macro title>Quiz Engine</#macro>

<#macro stylesheets>
<link href="/resources/css/style.css" rel="stylesheet" media="screen">
<link href="/resources/css/glyphicon.css" rel="stylesheet" media="screen">
<link href="/resources/css/loading.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.structure.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.theme.min.css" rel="stylesheet" media="screen">
</#macro>

<#macro javascripts>
<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/jquery-ui.min.js"></script>
<script src="/resources/js/datepicker-ru.js"></script>

<script type="text/javascript">

    var contentContainer;
    var loadingIndicator;

    $(document).ready(function () {
        contentContainer = $('#content');
        loadingIndicator = $('#circleG');

        loadingIndicator.hide();
    });

    function loadContent(url, title, addToHistory) {
        if (url != undefined && contentContainer != undefined) {

            loadingIndicator.show();
            contentContainer.hide();

            $.ajax({
                url: url,
                type: "GET"
            }).done(function (data) {
                document.title = title;

                loadingIndicator.hide();

                $(document).scrollTop(0);

                contentContainer.fadeTo(200, 1);
                contentContainer.html(data);

                if (addToHistory) {
                    window.history.pushState({"html": data, "pageTitle": title}, title, url);
                }
            });
        }
    }

    function showDialog(title, message) {
        var dialogMessage = $("<div/>");
        var messageContainer = $('<p/>', {
            html: message
        });
        dialogMessage.append(messageContainer);

        dialogMessage.dialog({
            title: title,
            modal: true,
            buttons: {
                "Закрыть": function () {
                    $(this).dialog("close");
                }
            }
        });
    }

</script>
</#macro>

<#macro body>

<div class="center-content">
    <h2 style="margin: 20px 0 20px 7px">Quiz Engine</h2>

    <div id="circleG" style="margin-top: 12%; margin-bottom: 12%">
        <div class="title center" style="color: #575757">Загрузка</div>
        <div id="circleG_1" class="circleG"></div>
        <div id="circleG_2" class="circleG"></div>
        <div id="circleG_3" class="circleG"></div>
    </div>

    <div id="content">

    <#if content=='main'>
        <#include 'step_one.ftl'>
    <#elseif content=='registration'>
        <#include 'registration.ftl'>
    <script type="text/javascript">
        setBroaderContentWidth();
    </script>
    </#if>

    </div>

</div>
<!-- /container -->

</#macro>

<@display_page/>