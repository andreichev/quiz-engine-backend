<#include '../base.ftl'>

<#macro title>Quiz Engine</#macro>

<#macro stylesheets>
<link href="/resources/css/style.css" rel="stylesheet" media="screen">
<link href="/resources/css/glyphicon.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.structure.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.theme.min.css" rel="stylesheet" media="screen">
</#macro>

<#macro javascripts>
<script type="text/javascript">

    function showDialog(title, message) {
        let dialogMessage = $("<div/>");
        let messageContainer = $('<p/>', {
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

<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/jquery-ui.min.js"></script>

<div class="center-content">
    <h2>Quiz Engine</h2>
</div>
<!-- /container -->

</#macro>

<@display_page/>