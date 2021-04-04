<div class="title-small-bold" style="margin-top: 0">Главный субъект:</div>
<div class="large-link" onclick="window.open('${entity}')">${label}</div>

<#if triples?has_content>

<div class="title-small-bold">Получившиеся вопросы (${triples?size}):</div>
<div class="text">Выберите подходящий вопрос</div>

    <#foreach triple in triples>

    <div onclick="addClick($(this).html(), ['${triple.objectLabel!}'])"
         class="question">Кто, или что, или какой ${triple.predicateLabel!} ${triple.subjectLabel!}?</div>

    <div class="variant">(${triple.objectLabel!})</div>

    </#foreach>
<#else>

    <div class="text center">Нет вопросов.</div>

</#if>

<script type="text/javascript">
    function addClick(text, options) {
        var params = {
            'text': text,
            'quiz': ${quiz.id}
        };

        var csrfParams = {
            '${_csrf.parameterName}': '${_csrf.token}'
        };

        addQuestion(params, csrfParams, options);
    }
</script>

<style type="text/css">
    .variant {
        font-size: 18px;
        margin: -5px 0 7px 0;
    }

    .question {
        font-size: 20px;
        padding-top: 7px;
        margin: 0 0 7px 7px;
        cursor: pointer;
    }
</style>