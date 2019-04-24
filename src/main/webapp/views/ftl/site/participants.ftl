<form id="new_participant" action="/quiz/${quiz.id}/participant" method="post">
    <label>Введите ваше имя и группу
        <input id="participant-name" class="input_green" name="name">
    </label>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />

    <input type="hidden" name="quiz"
           value="${quiz.id}" />

    <input class="button" type="submit" value="Далее">
</form>

<#if participants?has_content>
    <div style="margin: 30px 0"></div>
    <div class="text">Или выберите из списка</div>
<#foreach participant in participants>
<div style="margin: 10px 0">
    <button onclick="loadContent('/quiz/${quiz.id}/participant/${participant.id}', 'Викторина', true)" class="participant">${participant.name}</button>
</div>
</#foreach>
</#if>

<script type="text/javascript">
    var form = $('#new_participant');

    form.submit(function () {

        if($('#participant-name').val() == '') {
            showDialog('Ошибка', 'Введите имя пользователя и группу через пробел');
            return false
        }

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
                loadContent('/quiz/${quiz.id}/participant/' + data.participantId, 'Викторина', true)
            }
        });

        request.fail(function (data) {
            showDialog('Ошибка с сервера', data);
        });

        return false;
    });
</script>

<style type="text/css">
    .participant{
        font-size: 27px;
        color: #0e78e2;
        background-color: white;
        border: none;
        cursor: pointer;
        font-weight: 300;
        margin: 0
    }
</style>