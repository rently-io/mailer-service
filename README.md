<p>
  <img src="https://github.com/rently-io/mailer-service/actions/workflows/ci.yml/badge.svg" />
  <img src="https://github.com/rently-io/mailer-service/actions/workflows/cd.yml/badge.svg" />
</p>

# Mailer Service

This Spring Boot project is one among other RESTful APIs used in the larger Rently project. The mailer uses Google Gmail's SMTP and sends mails under `info.rently.io@gmail.com`. This is configurable externally. The mailer service also sends emails to a list of 'first responders' whenever an unhandled error occurs in other Rently services. This is also configurable externally.

Using this service is straight foward. There are two `POST` endpoints. `api/v1/report/dispatch` dispatches error report exclusively while the other, `api/v1/emails/dispatch` can dispatch a variety of mails and expects a `type` field in it's request body. Possible values of this field include `GREETINGS`, `NEW_LISTING`, `UPDATED_LISTING`, `LISTING_DELETION`, `ACCOUNT_DELETION`, `GENERIC_NOTIFICATION`, and `DEV_ERROR` (case insensitive). A specific html/css template is used depending on its value. Aside from mails of type `DEV_ERROR`, a request's body must also include a valid email address to whom the mail should be dispatched to.

A middleware was added that verifies the json web tokens' validity upon every requests. JWTs must have the [following shape](#jwt-object]) and must be encrypted using the right server secret and its corresponding hashing algorithm.

After each subsequent additions and changes to the codebase of the service, tests are ran and, if passed, the service is automatically deployed on to a Heroku instance [here](https://mailer-service-rently.herokuapp.com/) and dockerized [here](https://hub.docker.com/repository/docker/dockeroo80/rently-mailer-service).

> ⚠️ Please note that the service is currently deployed on a free Heroku instance and needs a few seconds to warm up on first request!

### C2 model
![C2 model](https://i.imgur.com/CqQbDQA.png)

### HTML templates

[templates]

## Objects

### Generic Notification Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `GENERIC_NOTIFICATION` |
| `email` email string | The recipiant's email address |
| `subject` string     | The email's subject matter    |
| `body` string        | The email's body content      |

### Greeting Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `GREETINGS` |
| `email` email string | The recipiant's email address |
| `name` string        | The recipiant's name          |

### Account Deletion Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `ACCOUNT_DELETION` |
| `email` email string | The recipiant's email address |
| `name` string        | The recipiant's name          |

### New Listing Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `NEW_LISTING` |
| `email` email string | The recipiant's email address |
| `link` url string    | The listing's link            |
| `image` url string   | The listing's image link      |
| `title` string       | The listing's title           |
| `description` string | The listing's description     |

### Updated Listing Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `UPDATE_LISTING` |
| `email` email string | The recipiant's email address |
| `link` url string    | The listing's link            |
| `image` url string   | The listing's image link      |
| `title` string       | The listing's title           |
| `description` string | The listing's description     |

### Listing Deletion Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `LISTING_DELETION` |
| `email` email string | The recipiant's email address |
| `link` url string    | The listing's link            |
| `title` string       | The listing's title           |
| `description` string | The listing's description     |

### Dev Error Object

| **Field**            | **Description**               |
| -------------------- | ----------------------------- |
| `type` mail type     | Mail type of value `DEV_ERROR`, optional when passed through  |
| `type` mail type     | The mail type for template    |
| `email` email string | The recipiant's email address |

### JWT Object

| **Field**         | **Description**              |
| ----------------- | ---------------------------- |
| `iat` timestamp   | Issue time of the token      |
| `exp` timestamp   | Expiration time of the token |
| `jti` uuid string | The token's id               |

<br />

## Request Mappings

### `POST /api/v1//emails/dispatch`

Dispatches an emails 

#### URL parameters:

|       **Parameter** | **Description**           | **Required** |
| ------------------: | ------------------------- | :----------: |
|   `id` uuid string       | Valid image id            |     true     |

#### Request body parameters:

> _none_
