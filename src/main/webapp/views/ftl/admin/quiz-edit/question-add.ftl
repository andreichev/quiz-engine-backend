<div class="title">Выберите способ создания вопроса:</div>

<button onclick="createQuestionRandomBySubject()" class="button-primary">Автоматически, по предмету и случайному субъекту</button>
<button onclick="createQuestionBySearch()" class="button-primary">Автоматически, по поиску субъекта</button>
<button onclick="createQuestionRandom()" class="button-primary">Автоматически, по случайному субъекту</button>
<button onclick="createQuestionManually()" class="button-primary">Вручную</button>

<script type="text/javascript">

    function createQuestionRandomBySubject() {
        loadContent('/admin/quiz/${quiz.id}/add-question-by-subject', 'Автоматически', true);
    }
    
    function createQuestionBySearch() {
        loadContent('/admin/quiz/${quiz.id}/add-question-by-search', 'Автоматически', true);
    }

    function createQuestionRandom() {
        loadContent('/admin/quiz/${quiz.id}/add-question-random', 'Автоматически', true);
    }
    
    function createQuestionManually() {
        loadContent('/admin/quiz/${quiz.id}/add-question-manually', 'Вручную', true);
    }

</script>