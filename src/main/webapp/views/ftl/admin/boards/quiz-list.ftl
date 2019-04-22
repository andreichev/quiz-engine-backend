<#if quizzes?has_content>
    <div>Викторины:</div>

    <div id="table-container">

        <table id="quizzes">
            <thead>
            <tr>
                <th onclick="sortTable($('#spectacles').find('tbody'), 0, 'tabledit-span', 'number')">#</th>
                <th onclick="sortTable($('#spectacles').find('tbody'), 1, 'tabledit-span', 'string')">Название</th>
                <th onclick="sortTable($('#spectacles').find('tbody'), 2, 'tabledit-span', 'string')">
                    <span class="glyphicon glyphicon-calendar"></span>
                </th>
                <th onclick="sortTable($('#spectacles').find('tbody'), 3, 'tabledit-span', 'number')">Кол-во ???</th>
                <th onclick="sortTable($('#spectacles').find('tbody'), 3, 'tabledit-span', 'number')">
                    <span class="glyphicon glyphicon-user"></span>
                </th>

                <th onclick="sortTable($('#spectacles').find('tbody'), 3, 'tabledit-span', 'number')">
                    <span class="glyphicon glyphicon-check"></span>
                </th>

                <th>
                    <div class="glyphicon glyphicon-book"></div>
                </th>
            </tr>
            </thead>

            <tbody>

            <#foreach item in quizzes >
            <tr>
                <td>${item.id}</td>
                <td>${item.title}</td>
                <td class="tabledit-input-date">${item.startDate?date}</td>
                <td>${item.questions?size}</td>
                <td>${item.participants?size}</td>
                <td>${item.active?then('Да', 'Нет')}</td>
                <td>
                    <button class="button button-edit-table"
                            onclick="window.open('/admin/quiz/${item.id}')">
                        Подробно
                    </button>
                </td>
            </tr>
            </#foreach>

            </tbody>
            <input class="tabledit-input" type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />

        </table>
    </div>

    <div style="margin: 30px"></div>

    <script type="text/javascript">
        $('#quizzes').Tabledit({
            url: '/admin/boards/quiz-list/edit',
            inputClass: 'input_green',
            editButton: true,
            buttons: {
                edit: {
                    class: 'button button-edit-table',
                    html: '<span class="glyphicon glyphicon-pencil"></span>',
                    action: 'edit',
                    method: 'POST'
                },
                delete: {
                    class: 'button button-edit-table',
                    html: '<span class="glyphicon glyphicon-trash"></span>',
                    action: 'delete',
                    method: 'DELETE'
                },
                save: {
                    class: 'button button-save',
                    html: 'Сохранить'
                },
                restore: {
                    class: 'button btn-sm btn-warning',
                    html: 'Восстановить',
                    action: 'restore'
                },
                confirm: {
                    class: 'button button-delete',
                    html: 'Подтвердить'
                }
            },
            columns: {
                identifier: [0, 'id'],
                editable: [
                    [1, 'title'],
                    [2, 'startDate'],
                    [5, 'active', {
                        1: "Да",
                        0: "Нет"
                        }]
                ]
            }
        });

        $('.tabledit-input-date').each(function () {
            $row = $(this);

            $row.find('input').addClass('js-datepicker');
        });

        $('.js-datepicker').datepicker();

    </script>

<#else>

    <div class="text center">Ни одной викторины еще не создано</div>

</#if>