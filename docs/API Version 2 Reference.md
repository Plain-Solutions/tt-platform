
General
-------

Now all the requests can be called directly from `/`.
So, the address is [api.ssutt.org:8080]() or [api-amst.ssutt.org:8080]().

__Version 1 is [here](https://github.com/Plain-Solutions/tt-platform/blob/dev/docs/API%20Version%201%20Reference.md).__

----
### [GET] /2/department/< department_tag >/group/< group_name >

Get timetable of group from department. Results are sorted by days, then by sequence of lessons, subgroup names (lexigraphically).

It has this structure:
 
 * 1st: array of lessons
 * 2nd: array of subjects on a day, time, sorted by parity.
 * 3rd: array of subgroups for subject

Days are numbered as for now from 0 - Monday to 5 - Saturday.
Parities are:

* 0 - nominator
* 1 - denominator
* 2 - full (both)


Status code:

* 200 - Success
* 404 - Error


Response in case of success:

```json
 [
 {
    "day": 0,
    "sequence": 4,
    "subject": [
      {
        "name": "англ.яз.станд.",
        "activity": "practice",
        "parity": 2,
        "subgroups": [
          {
            "subgroup": "анг.ст.7",
            "teacher": "Обликова Мария Ивановна",
            "location": "12корпус ауд.225В"
          },
          {
            "subgroup": "анг.ст.8",
            "teacher": "Кожевникова Елена Владимировна",
            "location": "12корпус ауд.229"
          },
          {
            "subgroup": "анг.ст.9",
            "teacher": "Карпец Елена Витальевна",
            "location": "12корпус ауд.313"
          },
          {
            "subgroup": "англ.ст.10",
            "teacher": "Павлова Ольга Вячеславовна",
            "location": "12корпус ауд.302"
          }
        ]
      },
      {
        "name": "переводчики",
        "activity": "practice",
        "parity": 2,
        "subgroups": [
          {
            "subgroup": "перев,3под",
            "teacher": "Шилова Светлана Алексеевна",
            "location": "12корпус ауд.125"
          }
        ]
      }
    ]
  },
  ...
  ]
```   

With two lessons on the same day and sequence:

```json
[...,
{
    "day": 2,
    "sequence": 2,
    "subject": [
      {
        "name": "Информатика и программирование",
        "activity": "lecture",
        "parity": 0,
        "subgroups": [
          {
            "subgroup": "",
            "teacher": "Иванова Анна Сергеевна",
            "location": "12корпус ауд.303"
          }
        ]
      },
       {
        "name": "Информатика и программирование",
        "activity": "practice",
        "parity": 1,
        "subgroups": [
          {
            "subgroup": "",
            "teacher": "Иванова Анна Сергеевна",
            "location": "12корпус ауд.303"
          }
        ]
      }
    ]
  },
  ...
  ]
```
