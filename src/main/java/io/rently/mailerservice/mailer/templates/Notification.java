package io.rently.mailerservice.mailer.templates;

public class Notification {
    private Notification() {
    }

    public static String getTemplate(String subject, String body) {
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
                        padding: 50px;
                        border-top: solid 15px #f14b4a;
                        padding-bottom: calc(15px +40px);
                      "
                    >
                      <h1 style="margin: 50px 0 60px 0; font-size: 40px; line-height: 60px">
                        [subject]
                      </h1>
                                
                      <p style="font-size: 20px">[body]</p>
                                
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

        return template
                .replace("[subject]", subject)
                .replace("[body]", body);
    }
}