<p>
  <img src="https://github.com/rently-io/mailer-service/actions/workflows/ci.yml/badge.svg" />
  <img src="https://github.com/rently-io/mailer-service/actions/workflows/cd.yml/badge.svg" />
</p>

# Mailer Service

This Spring Boot project is one among other RESTful APIs used in the larger Rently project. The mailer uses Google Gmail's SMTP and sends mails under `info.rently.io@gmail.com`. This is configurable externally. The mailer service also sends emails to a list of 'first responders' whenever an unhandled error occurs in other Rently services. This is also configurable externally.

Using this service is straight forward. There are two `POST` endpoints. `api/v1/report/dispatch` dispatches error report exclusively while the other, `api/v1/emails/dispatch` can dispatch a variety of mails and expects a `type` field in it's request body. 

Possible values of this field include `GREETINGS`, `NEW_LISTING`, `UPDATED_LISTING`, `LISTING_DELETION`, `ACCOUNT_DELETION`, `GENERIC_NOTIFICATION` (case insensitive). A specific html/css template is used depending on its value. A request's body must also include a valid email address to whom the mail should be dispatched to.

A middleware was added that verifies the json web tokens' validity upon every requests. JWTs must have the [following shape](#jwt-object]) and must be encrypted using the right server secret and its corresponding hashing algorithm.

After each subsequent additions and changes to the codebase of the service, tests are ran and, if passed, the service is automatically deployed on to a Heroku instance [here](https://mailer-service-rently.herokuapp.com/) and dockerized [here](https://hub.docker.com/repository/docker/dockeroo80/rently-mailer-service).

> ⚠️ Please note that the service is currently deployed on a free Heroku instance and needs a few seconds to warm up on first request!

### C2 model
![C2 model](https://i.imgur.com/CqQbDQA.png)

## Objects

### JWT Object

| **Field**         | **Description**              |
| ----------------- | ---------------------------- |
| `iat` timestamp   | Issue time of the token      |
| `exp` timestamp   | Expiration time of the token |
| `jti` uuid string | The token's id               |

<br />

## Request Mappings

### `POST /api/v1//emails/dispatch` for greetings

Dispatches a greeting email to a recepient. Used when a user first opens an account on Rently.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `GREETINGS` |
| `email` email string | The recipiant's email address |
| `name` string        | The recipiant's name          |

#### Dispatched email example:

[example]

### `POST /api/v1//emails/dispatch` for account deletions

Dispatches a goodbye email to a recepient. Used when a user terminates an account on Rently.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `ACCOUNT_DELETION` |
| `email` email string | The recipiant's email address |
| `name` string        | The recipiant's name          |

#### Dispatched email example:

[example]

### `POST /api/v1//emails/dispatch` for generic notifications

Dispatches a generic notification email to a recepient. Used for various actions should there be a need.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `GENERIC_NOTIFICATION` |
| `email` email string | The recipiant's email address |
| `subject` string     | The email's subject matter    |
| `body` string        | The email's body content      |

#### Dispatched email example:

[example]

### `POST /api/v1//emails/dispatch` for new listings

Dispatches a new listing notification to a recepient when a listing is created.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `NEW_LISTING` |
| `email` email string | The recipiant's email address |
| `link` url string    | The listing's link            |
| `image` url string   | The listing's image link      |
| `title` string       | The listing's title           |
| `description` string | The listing's description     |

#### Dispatched email example:

[example]

### `POST /api/v1//emails/dispatch` for updated listings

Dispatches a updated listing notification to a recepient when a listing is updated.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `UPDATE_LISTING` |
| `email` email string | The recipiant's email address |
| `link` url string    | The listing's link            |
| `image` url string   | The listing's image link      |
| `title` string       | The listing's title           |
| `description` string | The listing's description     |

#### Dispatched email example:

[example]

================================================================================================================================================

### `POST /api/v1//emails/dispatch` for deleted listings

Dispatches a deleted listing notification to a recepient when a listing is deleted.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `LISTING_DELETION` |
| `email` email string | The recipiant's email address |
| `link` url string    | The listing's link            |
| `title` string       | The listing's title           |
| `description` string | The listing's description     |

#### Dispatched email example:

[example]

### `POST /api/v1/report/dispatch`

Dispatches an error report to a list of first responders.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `service` string     | The error's origin service, optional    |
| `message` string     | The exception's message, optional       |
| `cause` string       | The exception's cause if any, optional  |
| `trace` string       | The exception's stack trace, optional   |
| `expectionType` string | The exception's class, optional   |
