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
![C2 model](https://i.imgur.com/34Nvkd4.jpg)

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

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `type` mail type     | Mail type of value `GREETINGS` |     true     |
| `email` email string | The recipiant's email address |     true     |
| `name` string        | The recipiant's name          |     true     |

#### Dispatched email example:

```json
{
  "type": "greetings",
  "email": "myemailaddress@gmail.com",
  "name": "greffgreff",
}
```

yields:

![greetings](https://i.imgur.com/SYgXt6q.png)

### `POST /api/v1//emails/dispatch` for account deletions

Dispatches a goodbye email to a recepient. Used when a user terminates an account on Rently.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `type` mail type     | Mail type of value `ACCOUNT_DELETION` |     true     |
| `email` email string | The recipiant's email address |     true     |
| `name` string        | The recipiant's name          |     true     |

#### Dispatched email example:

```json
{
  "type": "account_deletion",
  "email": "myemailaddress@gmail.com",
  "name": "greffgreff",
}
```

yields:

![goodbye](https://i.imgur.com/kBXMhTV.png)

### `POST /api/v1//emails/dispatch` for generic notifications

Dispatches a generic notification email to a recepient. Used for various actions should there be a need.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `type` mail type     | Mail type of value `GENERIC_NOTIFICATION` |     true     |
| `email` email string | The recipiant's email address |    true     |
| `subject` string     | The email's subject matter    |    true     |
| `body` string        | The email's body content      |    true     |

#### Dispatched email example:

```json
{
  "type": "generic_notification",
  "email": "myemailaddress@gmail.com",
  "subject": "Unwanrranted dad joke incoming",
  "body": "Why don't eggs tell jokes? They'd crash each other up."
}
```

yields:

![generic notification](https://i.imgur.com/dH9AN0i.png)

### `POST /api/v1//emails/dispatch` for new listings

Dispatches a new listing notification to a recepient when a listing is created.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `type` mail type     | Mail type of value `NEW_LISTING` |     true     |
| `email` email string | The recipiant's email address |     true     |
| `link` url string    | The listing's link            |     true     |
| `image` url string   | The listing's image link      |     true     |
| `title` string       | The listing's title           |     true     |
| `description` string | The listing's description     |     false    |

#### Dispatched email example:

```json
{
  "type": "new_listing",
  "email": "myemailaddress@gmail.com",
  "link": "https://rently-io.herokuapp.com/listings/6d1e0af3-5304-48a0-9c6f-eab15416d182",
  "image": "https://images-service-rently.herokuapp.com/api/v1/images/6d1e0af3-5304-48a0-9c6f-eab15416d182",
  "title": "Truck for rent",
  "description": "I am leasing this truck for a fw days. Max payload cannot exceed 2500 kg. Range capable of 3500 km."
}
```

yields:

![new listing](https://i.imgur.com/wDKZMIM.png)

### `POST /api/v1//emails/dispatch` for updated listings

Dispatches a updated listing notification to a recepient when a listing is updated.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `type` mail type     | Mail type of value `UPDATE_LISTING` |     true     |
| `email` email string | The recipiant's email address |     true     |
| `link` url string    | The listing's link            |     true     |
| `image` url string   | The listing's image link      |     true     |
| `title` string       | The listing's title           |     true     |
| `description` string | The listing's description     |     false    |

#### Dispatched email example:

```json
{
  "type": "update_listing",
  "email": "myemailaddress@gmail.com",
  "link": "https://rently-io.herokuapp.com/listings/37cc39d7-ba35-4ed2-b267-2b6f041dbbb7",
  "image": "https://images-service-rently.herokuapp.com/api/v1/images/37cc39d7-ba35-4ed2-b267-2b6f041dbbb7",
  "title": "BBQ for lease",
  "description": "Never used BBQ family size for big events. I received this BBQ two weeks ago and I never used it."
}
```

yields:

![updated listing](https://i.imgur.com/OK1S4bC.png)

### `POST /api/v1//emails/dispatch` for deleted listings

Dispatches a deleted listing notification to a recepient when a listing is deleted.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `type` mail type     | Mail type of value `LISTING_DELETION` |     true     |
| `email` email string | The recipiant's email address |     true     |
| `title` string       | The listing's title           |     true     |
| `description` string | The listing's description     |     false    |

#### Dispatched email example:

```json
{
  "type": "listing_deletion",
  "email": "myemailaddress@gmail.com",
  "image": "https://images-service-rently.herokuapp.com/api/v1/images/25cc39d7-ba35-4ed2-b267-2b6f041dbbb8",
  "title": "Bike for 10 days",
  "description": "I won't be using this bike for the next 10 days. Can pick up anytime."
}
```

yields:

![deleted listing](https://i.imgur.com/clkAx0M.png)

### `POST /api/v1/report/dispatch`

Dispatches an error report to a list of first responders.

#### URL parameters:

> _none_

#### Request body parameters:

| **Field**            | **Description**               | **Required** |
| -------------------- | ----------------------------- | :----------: |
| `service` string     | The error's origin service, optional    |    false     |
| `message` string     | The exception's message, optional       |    false     |
| `cause` string       | The exception's cause if any, optional  |    false     |
| `trace` string       | The exception's stack trace, optional   |    false     |
| `exceptionType` string | The exception's class, optional   |    false     |

#### Dispatched report exemple:

```json
{
  "service": "Listing service",
  "message": "Cannot invoke \"io.rently.listingservice.utils.Jwt.validateBearerToken(String)\" because \"this.jwt\" is null",
  "cause": null,
  "trace": "a long trace...",
  "exceptionType": "java.lang.NullPointerException"
}
```

yields:

![error report](https://i.imgur.com/nbWnFBw.png)
> This error has since been mitigated.
