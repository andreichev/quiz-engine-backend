<div class="title">Выберите викторину:</div>

<#foreach quiz in quizList>
<div>
    <button onclick="passage(${quiz.id})" class="button-primary">${quiz.title}</button>
</div>
</#foreach>

<script type="text/javascript">

    function passage(id) {
        loadContent('/quiz/' + id, 'Викторина', true);
    }

</script>