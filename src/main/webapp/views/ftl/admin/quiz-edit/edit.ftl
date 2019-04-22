<form id="edit_quiz_form" action="/admin/quiz/${quiz.id}" method="post">

    <label>Название
        <input value="${quiz.title}" type="text" name="title" class="input_green"/>
    </label>

    <label>Описание
        <textarea type="text" name="description" class="input_green">${quiz.description}</textarea>
    </label>

    <label>Дата начала
        <input value="${quiz.startDate?date}" type="text" name="startDate" class="input_green js-datepicker"/>
    </label>

    <div class="checkbox">
        <label>Активна
            <input name="active" class="js-checkbox" type="checkbox" value="true" ${quiz.active?then('checked', '')}>
        </label>
    </div>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

    <input type="submit" class="button-primary"/>

</form>

<script type="text/javascript">
    $(document).ready(function () {
        $.datepicker.setDefaults($.datepicker.regional["ru"]);

        $('.js-datepicker').datepicker({
            inline: true
        });

        var form = $('#edit_quiz_form');

        form.submit(function () {

            console.log(form.serialize());

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
                if (data.status == 'ok') {
                    showDialog('Готово', '<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>Информация обновлена');
                }
            });

            request.fail(function (data) {
                showDialog('Ошибка с сервера', data);
            });

            return false;
        });

        $('.js-checkbox').checkboxradio();
    });
</script>