/**
 * Created by Михаил on 01.12.2016.
 */

ymaps.ready(function () {
    latLng = [55.90710733249874, 49.30347561836243];

    var myMap = new ymaps.Map('map', {
            center: latLng,
            zoom: 12
        }, {
            searchControlProvider: 'yandex#search'
        }),
        myPlacemark = new ymaps.Placemark(myMap.getCenter(), {
            hintContent: 'Собственный значок метки',
            balloonContent: 'Это красивая метка'
        });

    myMap.geoObjects.add(myPlacemark);
    myMap.behaviors.disable(['scrollZoom', 'multiTouch']);
});






