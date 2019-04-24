<form id="questionCreate" action="/admin/quiz/${quiz.id}/add-question/" method="post">

    <label style="font-weight: 400">Название
        <textarea type="text" name="text" class="input_green"></textarea>
    </label>

    <input type="hidden" name="quiz" value="${quiz.id}">

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

    <input type="submit" class="button" value="Сохранить"/>

</form>

<script type="text/javascript">
    var form = $('#questionCreate');

    form.submit(function () {

        loadingIndicator.show();
        contentContainer.hide();

        var request = $.ajax({
            url: form.attr('action'),
            type: form.attr('method'),
            data: form.serialize()
        });

        request.always(function () {
            loadingIndicator.hide();
            contentContainer.show();
        });

        request.done(function (data) {
            if (data.status === 'ok') {
                showDialog('Готово', '<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>Информация обновлена');
                location.href = '/admin/quiz/${quiz.id}/question/' + data.questionId
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);
        });

        return false;
    });
</script>