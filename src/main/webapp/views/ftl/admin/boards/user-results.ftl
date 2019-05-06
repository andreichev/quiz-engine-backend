<div class="title">${participant.name}</div>
<div id="table-container">
    <table>
        <thead>
            <tr>
                <td>Номер</td>
                <td>Вопрос</td>
                <td>Ответ</td>
                <td>Верно ли</td>
                <td>Верный ответ</td>
            </tr>
            </thead>
        <tbody>
            <#foreach answer in participant.questionAnswers>
            <tr>
                <td>${answer?counter}</td>
                <td>${answer.question.text}</td>
                <td>${answer.questionOption.text}</td>
                <td>${answer.questionOption.correct?then("Верно", "Не верно")}</td>
                <td>
                    <#foreach option in answer.question.questionOptions>
                        <#if option.correct>
                            ${option.text}
                        </#if>
                    </#foreach>
            </td>
            </tr>
            </#foreach>
        </tbody>
    </table>
</div>

<script type="text/javascript">
    var normalContentWidth = $('.center-content').width();
    function setNormalContentWidth() {
        $('.center-content').animate({
            width: normalContentWidth
        }, 200 );
    }

    function setBroaderContentWidth(){
        $('.center-content').animate({
            width: $(window).width() * 0.8
        }, 200 );
    }

    //called from finder
    function onBoardDestroy() {
        setNormalContentWidth();
    }

    //called when board opened
    setBroaderContentWidth();
</script>