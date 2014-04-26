SSU TimeTable API Reference
==========================

General
-------

Now all the requests can be called directly from `/`.
So, the address is [api.ssutt.org].

Basic Requests
--------------

Common API for interacting with a service.

---
### [GET] /departments

Get list of department tags, names and department messages, sorted by names.

Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
{
  "bf": {
    "name": "Биологический факультет",
    "msg": ""
  },
  ...
  {
    "ff": {
      "name": "Физический факультет",
      "msg": "Числитель\nФевраль - 10 - 16, 24 - 28\nМарт - 1, 2, 10 - 16, 24 - 30\nАпрель - 7 - 13, 21 - 27\nМай - 5 - 11, 19 - 25\n_________________________________________________________\nЗнаменатель\nФевраль - 6 - 9, 17 - 23\nМарт - 3 - 9, 17 - 23, 31\nАпрель - 1 - 6, 14 - 20, 28 - 30\nМай - 1 - 4, 12 - 18, 26 - 31"
    }
  }
  ...
```

---
### [GET] /department/< department_tag >/groups

Get list of group names for selected department.

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
### [GET] /department/< department_tag >/group/< group_name >

Get timetable of group from department. Results are sorted by days, then by sequence of lessons, subgroup names (lexigraphically) and finally by parity.

It has this structure:
 
 * 1st: array of days {"dayname": data}
 * 2nd: array of lessons (data) {parity, sequence, info} 
 * 3rd: array of lessons records (info) {activity, subject, subinfo}
 * 4th: array of subgroup-teacher-location (subinfo) {subgroup, teacher, building, room}


Status code:

* 200 - Success
* 404 - Error

 

Response in case of success:

**simpliest:**

```json
{
        "parity": "full",
        "sequence": 2,
        "info": [
          {
            "activity": "practice",
            "subject": "Алгебра и геометрия",
            "subgroups": [
              {
                "subgroup": "",
                "teacher": "Букушева Алия Владимировна",
                "building": "12корпус ауд.312",
                "room": ""
              }
            ]
          }
        ]
      }
```

**several subgroups:**

```json
{
		"parity": "full",
        "sequence": 3,
        "info": [
          {
            "activity": "practice",
            "subject": "Информатика и программирование",
            "subgroups": [
              {
                "subgroup": " 1 под.",
                "teacher": "Иванова Анна Сергеевна",
                "building": "12корпус ауд.418а",
                "room": ""
              },
              {
                "subgroup": " 2 под.",
                "teacher": "Кондратова Юлия Николаевна",
                "building": "12корпус ауд.418б",
                "room": ""
              }
            ]
          }
        ]
      }
```   

***several subject at the same day-parity-time:***

```json
      {
        "parity": "full",
        "sequence": 4,
        "info": [
          {
            "activity": "practice",
            "subject": "англ.яз.станд.",
            "subgroups": [
              {
                "subgroup": "анг.ст.7",
                "teacher": "Обликова Мария Ивановна",
                "building": "12корпус ауд.225В",
                "room": ""
              },
              {
                "subgroup": "анг.ст.8",
                "teacher": "Кожевникова Елена Владимировна",
                "building": "12корпус ауд.229",
                "room": ""
              },
              {
                "subgroup": "анг.ст.9",
                "teacher": "Карпец Елена Витальевна",
                "building": "12корпус ауд.313",
                "room": ""
              },
              {
                "subgroup": "англ.ст.10",
                "teacher": "Павлова Ольга Вячеславовна",
                "building": "12корпус ауд.217",
                "room": ""
              }
            ]
          },
          {
            "activity": "practice",
            "subject": "переводчики",
            "subgroups": [
              {
                "subgroup": "перев,3под",
                "teacher": "Шилова Светлана Алексеевна",
                "building": "12корпус ауд.125",
                "room": ""
              }
            ]
          }
        ]
      }
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