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

    function showCorrectAnswerAndLoadNextQuestion(title,
                                                  quizId,
                                                  participantId,
                                                  nextQuestionId,
                                                  optionId,
                                                  correctOptionId,
                                                  correctOptionText) {

        var htmlMessage = "";
        if (optionId === correctOptionId) {
            htmlMessage += "Верно.";
        } else {
            htmlMessage += "Верный ответ: " + correctOptionText + ".";
        }

        var dialogMessage = $("<div/>");
        var messageContainer = $('<p/>', {
            html: htmlMessage
        });

        dialogMessage.append(messageContainer);

        var close = false;
        dialogMessage.dialog({
            title: title,
            modal: true,
            beforeClose: function(){
                return close;
            },
            open: function(event, ui) {
                $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
            },
            buttons: {
                "Далее": function () {
                    close = true;
                    $(this).dialog("close");

                    if (nextQuestionId !== -1) {
                        loadContent('/quiz/' + quizId + '/participant/' + participantId + '/question/' + nextQuestionId, 'Викторина', true);
                    } else {
                        loadContent('/quiz/' + quizId + '/participant/' + participantId + '/results', 'Результаты', true);
                    }

                }
            }
        });
    }

</script>
</#macro>

<#macro body>

<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/jquery-ui.min.js"></script>
<script src="/resources/js/datepicker-ru.js"></script>
<script src="/resources/js/shuffle.js"></script>

<div class="center-content">
    <h2>Quiz Engine</h2>

    <div id="circleG" style="margin-top: 12%; margin-bottom: 12%">
        <div class="title center" style="color: #575757">Загрузка</div>
        <div id="circleG_1" class="circleG"></div>
        <div id="circleG_2" class="circleG"></div>
        <div id="circleG_3" class="circleG"></div>
    </div>

    <div id="content">

    <#if content=='main'>

        <#include 'main.ftl'>

    <#elseif content=='quiz-list'>

        <#include 'quiz-list.ftl'>

    <#elseif content=='participants'>

        <#include 'participants.ftl'>

    <#elseif content=='participant-status'>

        <#include 'participant-status.ftl'>

    <#elseif content=='question'>

        <#include 'question.ftl'>

    <#elseif content=='results'>

        <#include 'results.ftl'>

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