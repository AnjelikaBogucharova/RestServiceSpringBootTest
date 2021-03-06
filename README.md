# RestServiceSpringBootTest
Приложение о питомцах.
Есть семья состоящая из двух пользователей parent (родитель) и child (ребенок). В данной семье есть правила, что решение завести питомца принимает родитель, у ребенка не будет доступа для принятия решения, пользователи отличные от родителя и ребенка блокируются. Получить информацию о всех питомцах, конкретном питомце, изменить либо удалить данные о питомце доступно всем.
Тип параметров:
user, password, name, type - String (строковый)
id, age - int (целочисленный)
Примеры запрос/ответ
Все запросы посылаем по адресу http://localhost:8989 если порт 8989 уже занят на вашем компьютере, то необходимо открыть исходники кода и в свойствах выставить любой другой свободный порт.

Если послать запрос по адресу отличного от http://localhost:8989 либо от порта установленного вами, то получим код статуса 502

Если прописать метод отличный от нижеуказанных, то получим код статуса 404

Если указать тип запроса отличный от установленного типа запроса к нижеуказанным методам, то получим код статуса 405


type request “GET” method “/getAll”
Метод возвращает всегда код статуса 200, если есть данные, то еще вернет список питомцев в виде json объекта. 


type request “GET” method “/getPet”
В headers обязательно для всех запросов указать параметр Content-Type: application/json, иначе получим код статуса 415.
Метод возвращает код статуса 200 и данные о питомце в виде json объекта
Тело запроса
{
  "id":2
}
Метод возвращает код статуса 404 и текст “Запрашиваемый питомец не найден!”, если указать id не из списка доступных
Тело запроса
{
  "id":5
}


type request “POST” method “/createPet”
В headers обязательно для всех запросов указать параметр Content-Type: application/json, иначе получим код статуса 415.
Успешно создавать данные может только пользователь parent, с паролем parentPassword.
Метод возвращает код статуса 201 и текст “Поздравляем с очередным пополнением! Bob, добро пожаловать в семью!”
Тело запроса
{
  "user":"parent",
  "password":"parentPassword",
  "name":"Bob",
  "type":"Dog",
  "age":3
}
Метод возвращает код статуса 403 и текст “Действие не доступно!”, если в качестве user/password передать данные child/childPassword
Тело запроса
{
  "user":"child",
  "password":"childPassword",
  "name":"Kate",
  "type":"fox",
  "age":1
}
Метод возвращает код статуса 401 и текст “Неверный пароль!”, если в качестве user передать данные child или parent, а в качестве пароля отличные данные от childPassword и parentPassword соответственно.
Тело запроса
{
  "user":"child",
  "password":"childPassword1",
  "name":"Kate",
  "type":"fox",
  "age":1
}
Метод возвращает код статуса 423 и текст “Действие заблокировано!”, если в качестве user передать данные отличные от child или parent.
Тело запроса
{
  "user":"parent1",
  "password":"childPassword1",
  "name":"Vasya",
  "type":"cat",
  "age":2
}
Метод возвращает код статуса 500 и текст “Попытка несанкционированного доступа!”, если в теле запроса не будет передано данных user и/или password
Тело запроса
{
   "name":"Vasya",
  "type":"cat",
  "age":2
}


type request “PUT” method “/updatedPet”
В headers обязательно для всех запросов указать параметр Content-Type: application/json, иначе получим код статуса 415.
Метод возвращает код статуса 202 и текст “updated”, если в теле запроса обязательно передан параметр id и один из параметров либо оба name и age
Тело запроса
{
  "id":2,
  "name":"Markus"
}
Метод возвращает код статуса 404 и текст “Питомца с id = 3 не найдено.”, если в теле запроса передать не существующего значения у параметра id
Тело запроса
{
  "id":3,
  "name":"Markus",
  "age":2
}
Метод возвращает код статуса 400 и текст “Для внесения изменений, необходимо передать данные для изменения имени либо возраста.”, если в теле запроса передать существующее значение параметра id и не передать ни один из параметров name или age
Тело запроса
{
  "id":1
}


type request “DELETE” method “/deletePet”
В headers обязательно для всех запросов указать параметр Content-Type: application/json, иначе получим код статуса 415.
Метод возвращает всегда код статуса 204.
Тело запроса
{
  "id":1
}
