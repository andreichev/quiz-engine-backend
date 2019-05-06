<div class="title">${quiz.title}</div>
<div id="table-container">
    <table>
        <thead>
        <tr>
            <td>Номер (не Id)</td>
            <td>Кол-во верных</td>
        </tr>
        </thead>
        <tbody>
            <#foreach participant in participantResults>
            <tr>
                <td>${participant?counter}</td>
                <td>${participant}</td>
            </tr>
            </#foreach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    finder.depth = 2;
    finder.folderIndexes = [0, 1, 0];
    finder.folderNames = ["Панель администратора", "Результаты", "Викторина 1"];
</script>