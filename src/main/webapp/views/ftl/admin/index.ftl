<#include '../base.ftl'>

<#macro title> Панель администратора </#macro>

<#macro stylesheets>
<link href="/resources/css/style.css" rel="stylesheet" media="screen">
<link href="/resources/css/table.css" rel="stylesheet" media="screen">
<link href="/resources/css/glyphicon.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.structure.min.css" rel="stylesheet" media="screen">
<link href="/resources/css/jquery-ui.theme.min.css" rel="stylesheet" media="screen">
</#macro>

<#macro javascripts>
<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/jquery-ui.min.js"></script>
<script src="/resources/js/datepicker-ru.js"></script>
<script src="/resources/js/finder.js"></script>
<script src="/resources/js/buttonsDistributor.js"></script>
<script src="/resources/js/jquery.tabledit.js"></script>
<script src="/resources/js/jquery.tablefilter.js"></script>
<script src="/resources/js/jquery.tablesort.js"></script>

<script type="text/javascript">

    $(document).ready(function () {
        init('/resources/panel/layout.json','/resources/panel/buttons.json')
    });

    function add() {
        goDeeper(0);
    }

    function logout() {
        window.location.href = '/logout';
    }

    function site() {
        window.location.href = '/';
    }

    function back() {
        if (finder.depth == 0) return;

        finder.upFolders(1);
        if (typeof onBoardDestroy != 'undefined') {
            onBoardDestroy();
        }
        reload(true);
    }

    function goDeeper(pos) {
        finder.addFolder(pos);
        if (typeof onBoardDestroy != 'undefined') {
            onBoardDestroy();
        }
        reload(true);
    }

    function setDepth(depth) {
        finder.setDepth(depth);
        if (typeof onBoardDestroy != 'undefined') {
            onBoardDestroy();
        }
        reload(true);
    }

    $(document).on('keyup', function (event) {
        switch (event.keyCode) {
            case 27: // Escape.
                back();
                break;
        }
    });

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

    function askUser(title, message, yes, no) {
        var dialogMessage = $( "<div/>" );
        var messageContainer = $('<p/>', {
            html: message
        });
        dialogMessage.append(messageContainer);

        dialogMessage.dialog({
            title: title,
            modal: true,
            buttons: {
                "Да": yes,
                "Нет": no
            }
        });
    }
</script>
</#macro>

<#macro body>
<div class="container">
    <div class="center-content">

        <div class="navigation-bar">
            <div id="leftButtonContainer">
                <a id="buttonLeft" class="leftButton"></a>
            </div>

            <div id="rightButtonContainer">
                <a id="buttonRight" class="rightButton"></a>
            </div>
            <div id="title" class="title center">Панель администратора</div>
        </div>

        <div id="pathContainer">

        </div>

        <div id="content">

        </div>

        <div id="buttonsContainer">

        </div>

        <div style="height: 220px; position: relative; top: 60px; z-index: -1; opacity: 100">
            <img class="center" style="width: 200px;" src="/resources/images/Logo/LOGO.png"/>
        </div>

    </div>
</div>
</#macro>

<@display_page/>
