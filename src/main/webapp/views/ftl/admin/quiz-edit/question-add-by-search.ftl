<label style="display: block; margin: 20px 0 0 7px">Введите запрос:</label>

<input id="query" class="input_green" type="text">

<button onclick="findEntity()" class="button">Найти</button>

<label id="labelSelectEntity" style="display: block; margin: 20px 0 0 7px"></label>
<div id="results"></div>

<script type="text/javascript">

    var queryInput = $('#query');
    var resultsContainer = $("#results");
    var labelResults = $("#labelResults");

    function findEntity() {

        loadingIndicator.show();
        contentContainer.hide();

        resultsContainer.html('');

        $.ajax({
            url: 'https://www.googleapis.com/customsearch/v1',
            dataType: 'json',
            data: {
                q: queryInput.val(),
                cx: '004840712803425132701:vwdd-8bl5iw',
                key: 'AIzaSyBrfIoFHXAedKtC82_dpg42zYihvsWjado'
            },
            success: function (result) {
                loadingIndicator.hide();
                contentContainer.fadeTo(200, 1);

                if (result.items !== undefined) {
                    if (result.items.length === 0) {
                        labelResults.html("Нет результатов. Попробуйте ввести другой запрос.");
                    } else {
                        labelResults.html("Выберите:");
                        for (var key in result.items) {
                            var currentResultContainer = $("<div class='large-link'></div>");
                            currentResultContainer.attr('onclick', 'createQuestionWith("' + result.items[key].link + '", "' + result.items[key].title + '")');
                            currentResultContainer.html(result.items[key].htmlTitle);
                            resultsContainer.append(currentResultContainer);
                        }
                    }
                } else {
                    labelResults.html("Нет результатов. Попробуйте ввести другой запрос.");
                }
            },
            error: function (XMLHttpRequest, errorMsg) {
                labelResults.html("Ошибка запроса. " + errorMsg);
            }
        });
    }

    function createQuestionWith(entity, label) {
        entity = entity.replace('/page', '/resource');

        var url = '/admin/quiz/${quiz.id}/add-question-with-entity?entity=' + entity;
        if(label !== undefined) {
            url += "&label=" + label;
        }
        loadContent(url, 'По классу', true);
    }
</script>