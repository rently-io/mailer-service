package io.rently.mailerservice.mailer.templates;

import io.rently.mailerservice.mailer.templates.interfaces.ITemplate;

public record Goodbye(String person) implements ITemplate {

    public String getTemplate() {
        String template = """
                <html>
                  <body
                    style="
                      margin: 0;
                      font-family: 'Ubuntu', 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell,
                        'Open Sans', 'Helvetica Neue', sans-serif;
                      background-color: #f5f7fc;
                      width: 100%;
                      line-height: 26px;
                      color: black;
                      padding: 50px 0;
                    "
                  >
                    <div
                      style="
                        position: relative;
                        display: block;
                        width: 70%;
                        max-width: 600px;
                        height: auto;
                        border-radius: 7px;
                        margin: 0 auto;
                        background-color: white;
                        padding: 40px;
                        border-top: solid 15px #f14b4a;
                        padding-bottom: calc(15px +40px);
                      "
                    >
                      <h1
                        style="
                          text-align: center;
                          margin: 50px 0 60px 0;
                          font-size: 40px;
                          line-height: 60px;
                        "
                      >
                        Goodbye, [person]!
                      </h1>

                      <p
                        style="
                          text-align: center;
                          margin: 50px 0 60px 0;
                          font-size: 30px;
                          line-height: 60px;
                        "
                      >
                        Your account was removed from our services.
                      </p>

                      <img
                        src="https://i.imgur.com/lqhwxBK.png"
                        style="display: block; width: 40%; margin: auto"
                      />

                      <p
                        style="
                          text-align: center;
                          font-size: 20px;
                          font-weight: bolder;
                          color: #f14b4a;
                          margin-top: 70px;
                        "
                      >
                        Rently.io
                      </p>
                    </div>
                  </body>
                </html>
                """;

        return template.replace("[person]", person);
    }
}
