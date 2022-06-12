package io.rently.mailerservice.mailer.templates;

import io.rently.mailerservice.mailer.templates.interfaces.ITemplate;

public record NewListing(
        String title,
        String image,
        String description,
        String link) implements ITemplate {

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
                      <h1 style="text-align: center; margin: 50px 0 60px 0; font-size: 40px">
                        It's official!
                      </h1>
                      <h2>Your listing is now online</h2>
                      <p>
                        The advert you listed on Rently is now online, available to anyone! You
                        can update any information at anytime by navigating at the bottom of
                        your
                        <a style="text-decoration: none; color: inherit">listing</a> and
                        clicking the Update button.
                      </p>
                                
                      <a
                        href="[link]"
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
                        <img
                          src="[image]"
                          style="width: 100%; aspect-ratio: 1; height: auto; object-fit: cover"
                        />
                                
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
                      </a>
                                
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
                .replace("[image]", image)
                .replace("[description]", description)
                .replace("[link]", link);
    }
}
