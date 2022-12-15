# timeuse-survey-api
Tjeneste som skal fungere som en slags reverse-proxy mellom tidsbruk-appen og timeuse-survey-service.

For samtlige kall som inneholder '/v1/respondent' kreves at sessionToken cookie er satt. 

## Funksjonalitet

### Token exchange
* POST@ /v1/token-exchange -> Sender forespørslene videre til timeuse-survey-service og returnerer status og eventuelt sessionToken som cookie.

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
* GET@ /v1/respondent/{respondent-id}/household-member
* POST@ /v1/respondent/{respondent-id}/household-member
* PUT@ /v1/respondent/{respondent-id}/household-member/{household-member-id}
* DELETE@ /v1/respondent/{respondent-id}/household-member/{household-member-id}


### Questionnaire
* GET@ /v1/respondent/{respondent-id}/questionnaire/{questionnaire-type}
* POST@ /v1/respondent/{respondent-id}/questionnaire/{questionnaire-type}


### Timeuse respondent
* GET@ /v1/respondent/{respondent-id}
* PUT@ /v1/respondent/{respondent-id}
* PUT@ /v1/respondent/{respondent-id}/{status}/{value}


### Diary start history
* GET@ /v1/respondent/{respondent-id}/diary-start-history
* POST@ /v1/respondent/{respondent-id}/diary-start-history
* DELETE@ /v1/respondent/{respondent-id}/diary-start-history


### Companion
* GET@ /v1/respondent/{respondent-id}/companion
* GET@ /v1/respondent/{respondent-id}/companion/{id}
* DELETE@ /v1/respondent/{respondent-id}/companion


### Sub activity
* POST@ /v1/respondent/{respondent-id}/sub-activity


### Survey
* GET@ /v1/survey?abbr=?