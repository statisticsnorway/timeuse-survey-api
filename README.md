# timeuse-survey-api
Tjeneste som skal rute forespørsler mellom tidsbruk-appen og timeuse-survey-service.


## Funksjonalitet
### Main activity
* GET@ /v1/respondent/{respondent-id}/main-activity
* GET@ /v1/respondent/{respondent-id}/main-activity/{activity-id}
* GET@ /v1/respondent/{respondent-id}/main-activity/group-by-day

Usikker hvordan man skal fordele post for main-activity og main-activities. Implementert forslag under
* POST@ /v1/respondent/{respondent-id}/main-activity
* POST@ /v1/respondent/{respondent-id}/main-activity/activities


* DELETE@ /v1/respondent/{respondent-id}/main-activity
* DELETE@ /v1/respondent/{respondent-id}/main-activity/{activity-id}
* DELETE@ /v1/respondent/{respondent-id}/main-activity/start-time/{start-time} //Usikker på denne


* PATCH@ /v1/respondent/{respondent-id}/main-activity/{activity-id}

### Household members
* GET@ /v1/respondent/{respondent-id}/household-members
* POST@ /v1/respondent/{respondent-id}/household-members
* PUT@ /v1/respondent/{respondent-id}/household-members/{household-member-id}
* DELETE@ /v1/respondent/{respondent-id}/household-members/{household-member-id}


### Questionnaire
* GET@ /v1/respondent/{respondent-id}/questionnaire/{questionnaire-type}
* POST@ /v1/respondent/{respondent-id}/questionnaire/{questionnaire-type}