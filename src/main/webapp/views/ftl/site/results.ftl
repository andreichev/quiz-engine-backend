<div class="title center">Количество верных ответов: ${countOfCorrect}. Спасибо за участие!</div>

<div onclick="startTest()" class="title blue center" style="border-radius: 20px; cursor: pointer; padding: 10px">Пройти заново</div>

<script type="text/javascript">
    function startTest() {
        var startQuestionId = '${questions?first.id}';
        loadContent('/quiz/${quiz.id}/participant/${participant.id}/question/' + startQuestionId, 'Вопрос', true);
    }
</script>