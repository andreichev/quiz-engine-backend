<label style="display: block; margin: 20px 0 0 7px">Тип сущности</label>
<select id="types" style="display: block" class="input_green">
</select>

<div id="map" style="width: 100%; height: 400px; display: none;"></div>

<#--<label style="display: block; margin: 20px 0 0 7px">Результат:</label>-->
<#--<a style="padding: 10px; border: 1px solid lightblue; display: none; margin-top: 5px;" class="text" id="result"-->
<#--target="_blank">-->
<#--</a>-->

<button onclick="loadEntity()" class="button">Далее</button>

      <script src="https://api-maps.yandex.ru/2.1/?apikey=f929bb23-4471-42b8-a34e-13f2b18da04d&lang=ru_RU"
              type="text/javascript">
      </script>

<script type="text/javascript">

    var selectContaiter = $('#types');

    loadTypes();

    function loadTypes() {
        $.getJSON("/admin/api/types", function (data) {
            for (var item in data) {
                var newOption = $('<option></option>');
                newOption.html(item);
                selectContaiter.append(newOption);
            }

            selectContaiter.change(function () {
                if (selectContaiter.find('option:selected').html() === 'dbo:Place') {
                    $('#map').fadeIn(200)
                } else {
                    $('#map').fadeOut(200);
                }
            });
        })
    }

    function loadEntity() {

        loadingIndicator.show();
        contentContainer.hide();

        console.log(myMap.getBounds());

        $.ajax({
            url: "/admin/api/random-entity",
            type: "GET",
            data: {
                type: selectContaiter.find('option:selected').html(),
                region: myMap.getBounds().toString()
            }
        }).done(function (data) {

            loadingIndicator.hide();

            contentContainer.fadeTo(200, 1);

            if (data.entity != "") {
                var resultContainer = $("#result");
                resultContainer.attr('href', data.entity);
                resultContainer.html(data.entity);
                resultContainer.css('display', 'block');
            }
            else {
                alert(data);
            }
        });
    }

    var myMap;

    // Функция ymaps.ready() будет вызвана, когда
    // загрузятся все компоненты API, а также когда будет готово DOM-дерево.
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
</script>