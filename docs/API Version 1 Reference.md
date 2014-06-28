SSU TimeTable API Version 1 Reference
==========================

General
-------

Now all the requests can be called directly from `/`.
So, the address is [api.ssutt.org:8080]() or [api-amst.ssutt.org:8080]().

__Version 2 is available [here](https://github.com/Plain-Solutions/tt-platform/blob/dev/docs/API%20Version%202%20Reference.md).__


---
### [GET] /1/departments

Get list of department tags and names, sorted by names.

Status code:

* 200 - Success
* 404 - Error

Response in case of success:
```json
[
	{ 
		"tag": "bf",
		"name": "Биологический факультет",
	},
  ...
  	{
		"tag": "ff"
		"name": "Физический факультет",
    },
  ...
]
```

---
### [GET] /1/department/< department_tag >/msg
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
### [GET] /1/department/< department_tag >/groups/?filled=<1|0>


####filled = 1

Get list of **all groups which have timetables** for selected department.

####filled = 0

Get list of **all available group names** for selected department.

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
### [GET] /1/department/< department_tag >/group/< group_name >

Get timetable of group from department. Results are sorted by days, then by sequence of lessons, subgroup names (lexigraphically) and finally by parity.

It has this structure:
 
 * 1st: array of lessons
 * 2nd: array of subjects on a day and time.
 * 3rd: array of subgroups for subject


Status code:

* 200 - Success
* 404 - Error


Response in case of success:

```json
[
	{
		"day": "mon",
		"parity": "full",
		"sequence": 4,
		"subject": [
			{
				"name": "англ.яз.станд.",
				"activity": "practice",
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
				 ]
      		},
      		{
		        "name": "переводчики",
        		"activity": "practice",
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
	
	
---
### Response in case of failure
TT Platform has the unified response in case of some error occurs: 

Response in case of failure:
```json
{ "errmsg": <Some Error Information > }
```
