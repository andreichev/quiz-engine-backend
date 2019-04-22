<form id="new_quiz_form" action="/admin/boards/quiz-list/add" method="post">

    <label>Название
        <input type="text" name="title" class="input_green"/>
    </label>

    <label>Описание
        <textarea type="text" name="description" class="input_green"></textarea>
    </label>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />

    <input type="submit" class="button-primary"/>

</form>

<script type="text/javascript">

    var form = $('#new_quiz_form');

    form.submit(function () {

        var request = $.ajax({
            url: form.attr('action'),
            type: form.attr('method'),
            dataType: 'json',
            data: form.serialize()
        });

        request.done(function (data) {
            if (data.status == 'ok') {
                back();
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data.responseJSON.errors);
        });

        return false;
    });

    $.datepicker.setDefaults($.datepicker.regional["ru"]);

    $('.js-datepicker').datepicker({
        inline: true
    });

</script>