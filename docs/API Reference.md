SSU TimeTable API Reference
==========================

General
-------

All methods use common prefix /api/```api_version```, where ```api_version``` is the major API version number.


Basic Requests
--------------

Common API for interacting with a service.

---
### [GET] /api/1/departments

Get list of department tags and names, sorted by names.

Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
{
  "bf": {
    "name": "Биологический факультет"
  },
  "gf": {
    "name": "Географический факультет"
  },
  ...
```

---
### [GET] /api/1/< department_tag >/groups

Get list of group numbers for selected department.

**String group names now are supported**

Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
[
	"111",
	"112",
	...
]
```

---
### [GET] /api/1/department/< department_tag >/group/< group_name >

Get timetable of group from department. Results are sorted by days, then by sequence of lessons, and finally (should be, not checked) by parity: **even > odd**, see second example. 

Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
{
  "mon": [
    {
      "parity": "odd",
      "sequence": "1",
      "info": "лек.  Математический анализ Сахно Л. В. 12корпус ауд.312"
    },
    {
      "parity": "all",
      "sequence": "2",
      "info": "пр.  Математический анализ Сахно Л. В. 12корпус ауд.312"
    },
    {
      "parity": "all",
      "sequence": "3",
      "info": "пр.  Физическая культура Вантеева В. Л. 12корпус Спортзал"
    },
	...
```
If classes are at the same time, but alternate each week:
```json
	...
	{
      "parity": "even",
      "sequence": "2",
      "info": "лек.  Информатика и программирование Иванова А. С. 12корпус ауд.303 "
    },
    {
      "parity": "odd",
      "sequence": "2",
      "info": "пр.  Информатика и программирование Иванова А. С. 12корпус ауд.30"
    },
    ...
```
	
---
### Response in case of failure
TT Platform has the unified response in case of some error occurs: 

Response in case of failure:
```json
{
	"module": < ModuleName >,
	"message": <Some Error Information >
}
```

----
### More methods coming soon..