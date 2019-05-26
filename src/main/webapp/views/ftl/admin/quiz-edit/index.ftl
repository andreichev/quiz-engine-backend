<#include '../../base.ftl'>

<#macro title> Панель администратора </#macro>

<#macro stylesheets>
    <link href="/resources/css/style.css" rel="stylesheet" media="screen">
    <link href="/resources/css/loading.css" rel="stylesheet" media="screen">
    <link href="/resources/css/quiz-edit.css" rel="stylesheet" media="screen">
    <link href="/resources/css/menu.css" rel="stylesheet" media="screen">
    <link href="/resources/css/glyphicon.css" rel="stylesheet" media="screen">
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

            <#if content=='question-edit'>
            setSelected('question${question.id}');
            <#else>
            setSelected('${content}');
            </#if>
        });

        function loadContent(url, title, addToHistory) {
            if (url !== undefined && contentContainer !== undefined) {

                loadingIndicator.show();
                contentContainer.hide();

                $.ajax({
                    url: url,
                    type: "GET"
                }).done(function (data) {
                    document.title = title;

                    loadingIndicator.hide();

                    contentContainer.fadeTo(200, 1);
                    contentContainer.html(data);

                    if (addToHistory) {
                        window.history.pushState({"html": data, "pageTitle": title}, title, url);
                    }

                    $("html, body").animate({ scrollTop: 0 }, 200);
                });
            }
        }

        function showDialog(title, message) {
            var dialogMessage = $( "<div/>" );
            var messageContainer = $('<p/>', {
                html: message
            });
            dialogMessage.append(messageContainer);

            dialogMessage.dialog({
                title: title,
                modal: true,
                buttons: {
                    "Закрыть": function() {
                        $( this ).dialog( "close" );
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

        function setSelected(selected) {
            $('.selected').removeClass('selected');
            $('#' + selected).addClass('selected', 100);
        }

        function edit() {
            loadContent('/admin/quiz/${quiz.id!'0'}', "Редактирование", true);
            setSelected('edit');
        }

        function question(id) {
            loadContent('/admin/quiz/${quiz.id!'0'}/question/' + id, "Вопрос", true);
            setSelected('question' + id);
        }

        function add() {
            loadContent('/admin/quiz/${quiz.id!'0'}/add-question/', "Добавить вопрос", true);
            $('.selected').removeClass('selected');
        }

        window.onpopstate = function (e) {
            loadContent(window.location.href, e.state.pageTitle, false);
        };

    </script>

</#macro>

<#macro body>
    <script src="/resources/js/jquery.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/datepicker-ru.js"></script>
    <script src="/resources/js/engine-admin-scripts.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?apikey=f929bb23-4471-42b8-a34e-13f2b18da04d&lang=ru_RU"
        type="text/javascript"></script>

    <div id="dialog-message" style="display: none">
        <p id="message-container">
        </p>
    </div>

    <div class="title-customer center">Викторина: ${quiz.title}</div>
    <div class="container">

        <div class="menu-container">
            <a id="edit" class="button-menu" onclick="edit()">Редактирование</a>
            <div style="color: white; padding: 8px;">Вопросы:</div>
            <#foreach q in quiz.questions>
                <a id="question${q.id}" class="button-menu" onclick="question(${q.id})">${q.text}</a>
            </#foreach>
            <div onclick="add()" class="buttonAdd" style="background-color: white; border-radius: 60px;">+</div>
        </div>


        <div class="center-content">
            <div id="circleG">
                <div class="title center">Загрузка</div>
                <div id="circleG_1" class="circleG"></div>
                <div id="circleG_2" class="circleG"></div>
                <div id="circleG_3" class="circleG"></div>
            </div>

            <div id="content">
                <#if content == 'edit' >

                    <#include 'edit.ftl'>

                <#elseif content=='question-edit'>

                    <#include 'question-edit.ftl'>

                <#elseif content=='question-add'>

                    <#include 'question-add.ftl'>

                <#elseif content=='question-add-manually'>

                    <#include 'question-add-manually.ftl'>

                <#elseif content=='question-add-by-subject'>

                    <#include 'question-add-by-subject.ftl'>

                <#elseif content=='question-add-by-search'>

                    <#include 'question-add-by-search.ftl'>

                <#elseif content=='question-add-random'>

                    <#include 'question-add-random.ftl'>

                <#elseif content=='question-add-with-entity'>

                    <#include 'question-add-with-entity.ftl'>

                <#else>

                <div class="text">
                    <div class="glyphicon glyphicon-arrow-left"></div>
                    Выберите пункт меню
                </div>

                </#if>
            </div>
        </div>

        <img class="center" style="width: 200px; clear: both;" src="/resources/images/Logo/LOGO.png"/>
        <!-- /container -->
    </div>

</#macro>

<@display_page/>
