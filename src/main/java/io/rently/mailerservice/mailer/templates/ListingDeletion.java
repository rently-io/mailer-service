package io.rently.mailerservice.mailer.templates;

public class ListingDeletion {
    private ListingDeletion() { }

    public static String getTemplate(String title, String description) {
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
                      <h1 style="text-align: center; margin: 50px 0 60px 0; font-size: 40px">
                        Done and dusted!
                      </h1>
                      <h2>Your listing has been removed.</h2>
                      <p>
                        The below advert you listed on Rently has been removed. Feel free to
                        list a new one anytime.
                      </p>
                                
                      <div
                        style="
                          text-decoration: none;
                          color: inherit;
                          height: auto;
                          border: 2px solid #eee;
                          margin: 30px auto;
                          width: 50%;
                          border-radius: 9px;
                          overflow: hidden;
                          display: block;
                          padding-bottom: 50px;
                        "
                      >
                                
                        <div class="details" style="padding: 5px 25px">
                          <h2
                            style="
                              text-overflow: ellipsis;
                              white-space: nowrap;
                              overflow: hidden;
                              width: 90%;
                            "
                          >
                            [title]
                          </h2>
                          <div
                            style="
                              overflow: hidden;
                              width: 90%;
                              display: -webkit-box;
                              -webkit-line-clamp: 4;
                              -webkit-box-orient: vertical;
                            "
                          >
                            [description]
                          </div>
                        </div>
                      </div>
                                
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
                .replace("[title]", title)
                .replace("[description]", description);
    }
}
