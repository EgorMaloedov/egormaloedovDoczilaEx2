# egormaloedovDoczilaEx2


Для начала необходимо установить Docker образ базы данных, который создаст базу данный PostgreSQL с нужной таблицей и пользователем, а затем запустит её.<br>
<a href="https://disk.yandex.ru/d/fg4udtTLeFPOnA"> ссылка на docker контейнер </a> <br><br>
Чтобы запустить его, воспользуйтесь командой, перед этим скачав<br>
`docker image load -i my-postgres.tar` <span> - это установит контейнер</span><br>
`docker run -p 9999:5432 -d my-postgres` <span> - это запустит базу данных</span><br>
<br><br>
Для запуска Java сервера убедитесь в установленном JRE <br>
Если не установлен: <br><br>
<a href="https://www.java.com/ru/download/"> Страница загрузки для Вашей OS </a><br><br>
На Ubuntu пропишите следующие команды: <br>
`sudo apt update`<br>
`sudo apt install default-jre`<br>
`sudo apt install default-jdk`<br><br>
Теперь можно запускать сервер, он будет находиться на localhost:9923<br>
`java -jar students-api-server.jar`<br><br>
Сервер запущен, теперь можно открыть index.html файл и проверить работоспособность API 
