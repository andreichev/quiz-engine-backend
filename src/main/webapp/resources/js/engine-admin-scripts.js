function addQuestion(params, csrfParams, options) {

    loadingIndicator.show();
    contentContainer.hide();

    var request = $.ajax({
        url: '/admin/quiz/' + params.quiz + '/add-question/',
        type: 'post',
        data: Object.assign(params, csrfParams)
    });

    request.done(function (data) {
        if (data.status === 'ok') {
            sendOptions(options, params.quiz, data.questionId, csrfParams);
        }
    });

    request.fail(function (data) {
        showDialog('Ошибка с сервера', data);

        loadingIndicator.hide();
        contentContainer.show();
    });
}

function sendOptions(options, quizId, questionId, csrfParams) {
    var request = $.ajax({
        url: '/admin/quiz/' + quizId + '/question/' + questionId + '/add-option',
        type: 'post',
        data: Object.assign({
            'text': options.pop(),
            'question': questionId,
            'correct': options.length === 0
        }, csrfParams)
    });

    request.done(function (data) {
        if (data.status === 'ok') {
            if (options.length > 0) {
                sendOptions(options, quizId, questionId, csrfParams);
            } else {
                location.href = '/admin/quiz/' + quizId + '/question/' + questionId
            }
        }
    });

    request.fail(function (data) {
        showDialog('Ошибка с сервера', data);

        loadingIndicator.hide();
        contentContainer.show();
    });
}

function translate(sourceText, containerToPut) {
    $.ajax({
        url: 'https://translate.yandex.net/api/v1.5/tr.json/translate',
        dataType: 'jsonp',
        data: {
            text: sourceText,
            lang: 'en-ru',
            key: 'trnsl.1.1.20190423T095846Z.ac9dafffd3b40317.438937c8d039412e9d2d6f07adf006c85b38f8ed'
        },
        success: function (result) {
            containerToPut.html(result.text);
        },
        error: function (XMLHttpRequest, errorMsg) {
            containerToPut.html(errorMsg);
        }
    });
}