<div>Выберите пользователя:</div>
<div id="table-container">
    <table>
        <thead>
        <tr>
            <td>Номер (не Id)</td>
            <td>Имя</td>
        <#--<td>Кол-во ответов</td>-->
        </tr>
        </thead>
        <tbody>
            <#foreach participant in quiz.participants>
            <tr class="pathItem" onclick="loadParticipant(${participant.id})">
                <td>${participant?counter}</td>
                <td>${participant.name}</td>
            <#--<td>${participant.questionAnswers?size}</td>-->
            </tr>
            </#foreach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    function loadParticipant(id) {
        var url = '/admin/boards/quiz/${quiz.id}/participant/' + id;
        loadContent(url);
        window.history.pushState(null, null, url);

    }
</script>