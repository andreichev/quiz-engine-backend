<div class="title">
    ${participant.name}
</div>

<#if answers?size == questions?size>
    <button onclick="loadContent(location.href + '/results', 'Результаты', true)" class="button-primary">Перейти к результатам</button>
<#else>
    <button onclick="startTest()" class="button-primary">Начать тест</button>
</#if>

<script type="text/javascript">
    function startTest() {
        var startQuestionId = '${questions?first.id}';
        loadContent(location.href + '/question/' + startQuestionId, 'Вопрос', true);
    }
</script>