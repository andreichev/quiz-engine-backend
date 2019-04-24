<div class="title">Автоматическая генерация вопроса:</div>

<button onclick="loadQuestion()" class="button-primary" style="margin-bottom: 35px">Сгенерировать</button>

<div id="questionContainer" style="display: none">
    <div style="font-weight: 400; background-color: lightgray; padding: 7px 0 7px 7px;">Вопрос:</div>
    <div id="questionText" class="variant"></div>
    <div style="padding: 0 0 20px 30px;">
        <div style="font-weight: 400; background-color: lightgray; padding: 7px 0 7px 7px;">Варианты:</div>

        <div id="variants">

        </div>

    </div>

    <input type="hidden" name="quiz" value="${quiz.id}">

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

    <input onclick="addQuestion()" type="submit" class="button" value="Добавить"/>
</div>

<script type="text/javascript">

    function translate(sourceText, containerToPut) {
        $.ajax({
            url: 'https://translate.yandex.net/api/v1.5/tr.json/translate',
            dataType: 'jsonp',
            data: {
                text: sourceText,
                lang: 'en-ru',
                key: 'trnsl.1.1.20190423T095846Z.ac9dafffd3b40317.438937c8d039412e9d2d6f07adf006c85b38f8ed'
            },
            success: function (result) {
                containerToPut.html(result.text);
            },
            error: function (XMLHttpRequest, errorMsg) {
                containerToPut.html(errorMsg);
            }
        });
    }

    function loadQuestion() {
        var url = 'http://localhost:8898/api/generate?type=dbo:Event';
        //var url = 'http://localhost:8898/api/sample';

        loadingIndicator.show();
        contentContainer.hide();

        $.getJSON(url, function (data) {

            question = data;

            loadingIndicator.hide();
            contentContainer.fadeTo(200, 1);

            translate(data.q, $('#questionText'));
            //$('#questionText').html(data.q);

            var variantsContainer = $('#variants');
            variantsContainer.html('');

            var currentVariant = $('<div class="variant questionOption"></div>');
            //currentVariant.html(data.correctAnswer + ' (верный ответ)');
            translate(data.correctAnswer, currentVariant);
            variantsContainer.append(currentVariant);
            currentVariant.attr('correct', 'true');

            data.alternativeAnswers.forEach(function (element) {
                var currentVariant = $('<div class="variant questionOption"></div>');
                variantsContainer.append(currentVariant);
                translate(element, currentVariant);
            });

            $('#questionContainer').show(200);
        });
    }

    function addQuestion() {

        loadingIndicator.show();
        contentContainer.hide();

        var options = [];
        var optionsElements = $('.questionOption');
        $.each(optionsElements, function () {
            options.push($(this).html());
        });

        var request = $.ajax({
            url: '/admin/quiz/${quiz.id}/add-question/',
            type: 'post',
            data: {
                'text': $('#questionText').html(),
                'quiz': ${quiz.id},
                '${_csrf.parameterName}': '${_csrf.token}'
            }
        });

        request.done(function (data) {
            if (data.status === 'ok') {
                sendOptions(options, data.questionId);
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);

            loadingIndicator.hide();
            contentContainer.show();
        });
    }

    function sendOptions(options, questionId) {
        var request = $.ajax({
            url: '/admin/quiz/${quiz.id}/question/' + questionId + '/add-option',
            type: 'post',
            data: {
                'text': options.pop(),
                'question': questionId,
                'correct': options.length == 0,
                '${_csrf.parameterName}': '${_csrf.token}'
            }
        });

        request.done(function (data) {
            if (data.status === 'ok') {
                if (options.length > 0) {
                    sendOptions(options, questionId);
                } else {
                    location.href = '/admin/quiz/${quiz.id}/question/' + questionId
                }
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);

            loadingIndicator.hide();
            contentContainer.show();
        });
    }

</script>

<style type="text/css">
    .variant {
        font-size: 20px;
        margin: 10px 0 7px 0;
    }
</style>