## Агрегатор новостей

- приложение получает данные с помощью протокола RSS 
- для однажды загруженного материала обеспечена его доступность при отсутствии подключения к интернету 
- реализованы функции управления списком каналов: добавление, удаление, просмотр новостей данного канала 
- любые сетевые запросы не блокируют главную нить приложения, их обработка организована в рамках сервиса 
- содержимое новостных лент сохраняется в базу данных SQLite 
- программа регистрируется в ОС как обработчик соответствующих схем данных, ссылки на новостные каналы открываются в ней 
- приложение локализовано на второй язык 
- уведомление пользователя о свежих новостях через механизм нотификаций 
- индикация процесса выгрузки новых данных на панели уведомлений 
- программа с заданной пользователем периодичностью проверяет обновления каналов 
