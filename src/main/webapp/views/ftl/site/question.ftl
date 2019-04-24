<div style="font-weight: 400; background-color: lightgray; padding: 7px 0 7px 7px;">Вопрос:</div>
<div id="questionText" class="variant">${question.text}</div>
<div style="padding: 0 0 20px 30px;">
    <div style="font-weight: 400; background-color: lightgray; padding: 7px 0 7px 7px;">Варианты:</div>

    <div id="variants">
    <#foreach option in question.questionOptions>
        <div class="variant" onclick="answer(${option.id})" style="color: #0038aa; cursor: pointer">${option.text}</div>
    </#foreach>
    </div>

</div>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

<script type="text/javascript">
    function answer(optionId) {
        loadingIndicator.show();
        contentContainer.hide();

        var request = $.ajax({
            url: location.href,
            type: 'post',
            data: {
                'question': '${question.id}',
                'questionOption': optionId,
                'participant': '${participant.id}',
                '${_csrf.parameterName}': '${_csrf.token}'
            }
        });

        request.always(function () {
            loadingIndicator.hide();
            contentContainer.show();
        });

        request.done(function (data) {
            if (data.status === 'ok') {
                if(data.nextQuestionId !== -1) {
                    loadContent('/quiz/${quiz.id}/participant/${participant.id}/question/' + data.nextQuestionId, 'Викторина', true)
                } else {
                    loadContent('/quiz/${quiz.id}/participant/${participant.id}/results', 'Результаты', true)
                }
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);
        });
    }
</script>

<style type="text/css">
    .variant {
        font-size: 20px;
        margin: 10px 0 7px 7px;
    }
</style>