# egormaloedovDoczilaEx2


Для начала необходимо установить Docker образ базы данных, который создаст базу данный PostgreSQL с нужной таблицей и пользователем, а затем запустит её.<br>
<a href="https://disk.yandex.ru/d/fg4udtTLeFPOnA"> ссылка на docker контейнер </a> <br><br>
Чтобы запустить его, воспользуйтесь командой, перед этим скачав<br>
`docker image load -i my-postgres.tar` <span> - это установит контейнер</span><br>
`docker run -p 9999:5432 -d my-postgres` <span> - это запустит базу данных</span><br>
