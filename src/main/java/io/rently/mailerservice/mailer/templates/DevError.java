package io.rently.mailerservice.mailer.templates;


import java.util.List;

public class DevError {
    private DevError() { }

    public static String getTemplate(String service, String message, String cause, String trace, String exceptionType, List<Object> emails, String time) {
        String template = """
                <html>
                  <div
                    style="
                      margin: 0;
                      font-family: 'Ubuntu', 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell,
                        'Open Sans', 'Helvetica Neue', sans-serif;
                      width: 100%;
                      line-height: 26px;
                      color: black;
                      padding: 30px;
                      box-sizing: border-box;
                    "
                  >
                    <h1>Error report for [service]</h1>
                    <hr style="border: solid 1px #eee" />
                    <p>Source &nbsp; <b>[service]</b></p>
                    <p>Occurred at &nbsp; <b>[time]</b></p>
                    <p>Exception type &nbsp; <b>[type]</b></p>
                    <hr style="border: solid 1px #eee" />
                    <h2>Message</h2>
                    <p style="padding-left: 50px">[message]</p>
                    <hr style="border: solid 1px #eee" />
                    <h2>Cause</h2>
                    <p style="padding-left: 50px">[cause]</p>
                    <hr style="border: solid 1px #eee" />
                    <h2>Trace</h2>
                    <div style="margin: 20px 0;">[trace]</div>
                    <hr style="border: solid 1px #eee" />
                    <h2>Notified</h2>
                    <p>[emails]</p>
                  </div>
                </html>
                """;

        StringBuilder parsedEmail = new StringBuilder();
        for (Object email : emails) {
            parsedEmail.append(email).append("  ");
        }

        StringBuilder parsedTrace = new StringBuilder();
        for (Object line : trace.split(",")) {
            parsedTrace.append("<p style=\"padding-left: 50px; margin: 0; \">").append(line).append("</p>");
        }

        return template
                .replace("[message]", message)
                .replace("[cause]", cause)
                .replace("[type]", exceptionType)
                .replace("[emails]", parsedEmail.toString())
                .replace("[trace]", parsedTrace.toString())
                .replace("[time]", time)
                .replace("[service]", service);
    }
}
