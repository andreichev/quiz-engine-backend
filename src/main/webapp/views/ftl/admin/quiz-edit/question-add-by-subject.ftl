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

    <input onclick="addClick()" type="submit" class="button" value="Добавить"/>
</div>

<script type="text/javascript">

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

    function addClick() {

        var options = [];
        $.each($('.questionOption'), function () {
            options.push($(this).html());
        });

        var params = {
            'text': $('#questionText').html(),
            'quiz': ${quiz.id}
        };

        var csrfParams = {
            '${_csrf.parameterName}': '${_csrf.token}'
        };

        addQuestion(params, csrfParams, options);
    }

</script>

<style type="text/css">
    .variant {
        font-size: 20px;
        margin: 10px 0 7px 0;
    }
</style>