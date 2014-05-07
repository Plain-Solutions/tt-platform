SSU TimeTable API Reference
==========================

General
-------

Now all the requests can be called directly from `/`.
So, the address is [api.ssutt.org:8080]() or [api-amst.ssutt.org:8080]().

Basic Requests
--------------

Common API for interacting with a service.

---
### [GET] /departments

Get list of department tags and names, sorted by names.

Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
{
  "bf": {
    "name": "Биологический факультет",
  },
  ...
  {
    "ff": {
      "name": "Физический факультет",
    }
  }
  ...
```

---
### [GET] /department/< department_tag >/msg
Get the department message by its tag.
Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
{
	"msg" : "Crucial information!"	
}
  ...
```

---
### [GET] /department/< department_tag >/groups/ < mode >

####mode = all

Get list of **all available group names** for selected department.

####mode = nonemp

Get list of **all groups which have timetables** for selected department.

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
{ "errmsg": <Some Error Information > }
```

----
### More methods coming soon...