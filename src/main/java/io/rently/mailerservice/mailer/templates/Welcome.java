package io.rently.mailerservice.mailer.templates;

public class Welcome {
    public final String person;

    public Welcome(String person) {
        this.person = person;
    }

    @Override
    public String toString() {
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
                      class="content"
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
                        class="text-center title"
                        style="
                          text-align: center;
                          margin: 50px 0 60px 0;
                          font-size: 40px;
                          line-height: 60px;
                        "
                      >
                        The Rently community welcomes you, [person]!
                      </h1>

                      <img
                        class="image"
                        src="https://i.imgur.com/DtX8rWd.png"
                        style="display: block; width: 60%; margin: auto"
                      />

                      <p
                        class="logo text-center"
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
