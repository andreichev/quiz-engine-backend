<div>Выберите викторину:</div>
<div id="table-container">
    <table>
        <thead>
        <tr>
            <td>Название</td>
        </tr>
        </thead>
        <tbody>
    <#foreach quiz in quizzes>
    <tr class="pathItem" onclick="loadRateForQuiz(${quiz.id}, '${quiz.title}')">
        <td>${quiz.title}</td>
    </tr>
    </#foreach>
        </tbody>
    </table>
</div>

<script type="text/javascript">

    function loadRateForQuiz(quizId, quizName) {
        finder.addCurrentMenu({
            "title": quizName,
            "rightButton": "logout",
            "leftButton": "back",
            "url": '/admin/boards/participants-results/quiz/' + quizId,
            "hasContent": true
        });

        reload(true);
    }
</script>