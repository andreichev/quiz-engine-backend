<form action="/admin/quiz/${quiz.id}/question/${question.id}" method="post">

    <label style="font-weight: 400">Название
        <textarea type="text" name="text" class="input_green">${question.text}</textarea>
    </label>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

    <input type="submit" class="button" value="Сохранить"/>
    <button type="button" style="background: #ce5c5c;" class="button"
            onclick="deleteQuestion(${question.id})">Удалить
    </button>

</form>

    <div style="padding: 30px 0 20px 30px;">
        <div style="font-weight: 400">Варианты:</div>

    <#foreach questionOption in question.questionOptions>

        <form id="questionOption${questionOption.id}" action="/admin/quiz/${quiz.id}/question/${question.id}/option/${questionOption.id}" method="post">
            <div style="display: table">
                <input value="${questionOption.text}" type="text" name="text"
                       class="input_green" style="display: table-cell"/>

                <span style="display:table-cell; width: 1%; height: 20px">
                        <label style="margin-left: 20px">Верный
                            <input type="checkbox" name="correct"
                                   class="js-checkbox" ${questionOption.correct?then('checked', '')}>
                        </label>
                    </span>
            </div>

            <div style="clear: both"></div>

            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>

            <button class="button">Сохранить</button>
            <button type="button" style="background: #ce5c5c;" class="button"
                    onclick="deleteQuestionOption(${questionOption.id})">Удалить
            </button>

            <div style="margin-bottom: 30px">
            </div>
        </form>
    </#foreach>

        <form action="/admin/quiz/${quiz.id}/question/${question.id}/add-option" method="post" reloadpage="true">
            <div style="font-weight: 400">Добавить:</div>
            <div style="display: table">
                <input value="" type="text" name="text"
                       class="input_green" style="display: table-cell"/>

                <span style="display:table-cell; width: 1%; height: 20px">
                    <label style="margin-left: 20px">Верный
                        <input name="correct" type="checkbox" class="js-checkbox" value="1">
                    </label>
                </span>
            </div>

            <div style="clear: both"></div>

            <input type="hidden" name="question" value="${question.id}">

            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>

            <button type="submit" class="button">Сохранить</button>
        </form>
    </div>


<script type="text/javascript">

    function deleteQuestionOption(id) {

        loadingIndicator.show();
        contentContainer.hide();

        var request = $.ajax({
            url: '/admin/quiz/${quiz.id}/question/${question.id}/option/' + id + '?${_csrf.parameterName}=${_csrf.token}',
            type: 'delete'
        });

        request.always(function () {
            loadingIndicator.hide();
            contentContainer.show();
        });

        request.done(function (data) {
            if (data.status === 'ok') {
                $('#questionOption' + id).hide(200);
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);
        });
    }

    function deleteQuestion() {

        loadingIndicator.show();
        contentContainer.hide();

        var request = $.ajax({
            url: '/admin/quiz/${quiz.id}/question/${question.id}?${_csrf.parameterName}=${_csrf.token}',
            type: 'delete'
        });

        request.always(function () {
            loadingIndicator.hide();
            contentContainer.show();
        });

        request.done(function (data) {
            location.href='/admin/quiz/${quiz.id}'
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);
        });
    }

    $(document).ready(function () {
        var form = $('form');

        form.each(function () {
            var currentForm = $(this);
            currentForm.submit(function () {

                loadingIndicator.show();
                contentContainer.hide();

                var request = $.ajax({
                    url: currentForm.attr('action'),
                    type: currentForm.attr('method'),
                    data: currentForm.serialize()
                });

                request.always(function () {
                    if(currentForm.attr('reloadpage')) {
                        question(${question.id});
                    } else {
                        loadingIndicator.hide();
                        contentContainer.show();
                    }
                });

                request.done(function (data) {
                    if (data.status === 'ok' && !currentForm.attr('reloadpage')) {
                        showDialog('Готово', '<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>Информация обновлена');
                    }
                });

                request.fail(function (data) {
                    showDialog('Ошибка с сервера', data);
                });

                return false;
            });
        });

        $('.js-checkbox').checkboxradio();
    });
</script>