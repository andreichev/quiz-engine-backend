<div>Выберите викторину:</div>
<div id="table-container">
    <table>
        <thead>
        <tr>
            <td>Название</td>
            <td>Кол-во вопросов</td>
        </tr>
        </thead>
        <tbody>
    <#foreach quiz in quizzes>
    <tr class="pathItem" onclick="loadRateForQuiz(${quiz.id})">
        <td>${quiz.title}</td>
        <td>${quiz.questions?size}</td>
    </tr>
    </#foreach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    function loadRateForQuiz(quizId) {
        loadContent('/admin/boards/rate/' + quizId);
    }
</script>