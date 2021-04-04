<label style="display: block; margin: 20px 0 0 7px">Выберите:</label>
<select id="types" style="display: block" class="input_green">
</select>

<input id="query" class="input_green" type="text">

<div id="map" style="width: 100%; height: 400px; display: none;"></div>

<#--<label style="display: block; margin: 20px 0 0 7px">Результат:</label>-->
<#--<a style="padding: 10px; border: 1px solid lightblue; display: none; margin-top: 5px;" class="text" id="result"-->
<#--target="_blank">-->
<#--</a>-->

<button onclick="findEntity()" class="button">Найти</button>

<label id="labelSelectEntity" style="display: block; margin: 20px 0 0 7px"></label>
<div id="results"></div>

<script type="text/javascript">

    var selectContaiter = $('#types');
    var queryInput = $('#query');
    var resultsContainer = $("#results");
    var map = $("#map");
    var labelSelectEntity = $("#labelSelectEntity");

    loadTypes();

    function loadTypes() {
        $.getJSON("/admin/api/types", function (data) {
            for (var key in data) {
                var newOption = $('<option></option>');
                newOption.html(data[key]);
                newOption.attr('value', key);
                selectContaiter.append(newOption);
            }

            selectContaiter.change(function () {
                if (selectContaiter.find('option:selected').val() === 'dbo:Place') {
                    map.fadeIn(400);
                    queryInput.fadeOut(400);
                } else {
                    map.fadeOut(400);
                    queryInput.fadeIn(400);
                }
            });
        })
    }

    function findEntity() {

        loadingIndicator.show();
        contentContainer.hide();

        console.log('bounds of map: ' + myMap.getBounds());
        console.log('selected type:' + selectContaiter.find('option:selected').val());

        $.ajax({
            url: "/admin/api/find-entity",
            type: "GET",
            data: {
                type: selectContaiter.find('option:selected').val(),
                region: myMap.getBounds().toString(),
                query: queryInput.val()
            }
        }).done(function (data) {

            loadingIndicator.hide();

            contentContainer.fadeTo(200, 1);

            resultsContainer.html('');

            if (data.entities !== undefined) {
                if (data.entities.length === 0) {
                    labelSelectEntity.html("Нет результатов. Попробуйте ввести другой запрос.");
                } else {
                    labelSelectEntity.html("Выберите:");
                    for (var key in data.entities) {
                        var currentResultContainer = $("<div class='large-link'></div>");
                        currentResultContainer.attr('onclick', 'createQuestionWith("' + key + '")');
                        currentResultContainer.html(data.entities[key]);
                        resultsContainer.append(currentResultContainer);
                    }
                }
            }
            else {
                alert(data);
            }
        });
    }

    function createQuestionWith(entity) {
        var url = encodeURI('/admin/quiz/${quiz.id}/add-question-with-entity?entity=' + entity);
        console.log(url);
        loadContent(url, 'По классу', true);
    }

    var myMap;

    // Функция ymaps.ready() будет вызвана, когда
    // загрузятся все компоненты API, а также когда будет готово DOM-дерево.
    $(document).ready(function () {

        ymaps.ready(init);

        function init() {
            // Создание карты.
            myMap = new ymaps.Map("map", {
                // Координаты центра карты.
                // Порядок по умолчанию: «широта, долгота».
                // Чтобы не определять координаты центра карты вручную,
                // воспользуйтесь инструментом Определение координат.
                center: [55.78, 49.12],
                // Уровень масштабирования. Допустимые значения:
                // от 0 (весь мир) до 19.
                zoom: 7
            });
        }
    })
</script>