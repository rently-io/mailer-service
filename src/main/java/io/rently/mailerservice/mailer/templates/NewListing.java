package io.rently.mailerservice.mailer.templates;


public class NewListing {
    private final String link;
    private final String image;
    private final String title;
    private final String description;

    public NewListing(String link, String image, String title, String description) {
        this.link = link;
        this.image = image;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "<html>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      margin: 0;\n" +
                "      font-family: 'Ubuntu', 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell,\n" +
                "        'Open Sans', 'Helvetica Neue', sans-serif;\n" +
                "      background-color: #f5f7fc;\n" +
                "      width: 100%;\n" +
                "      line-height: 26px;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      class=\"content\"\n" +
                "      style=\"\n" +
                "        width: 600px;\n" +
                "        height: auto;\n" +
                "        border-radius: 7px;\n" +
                "        border-top: solid 15px #f14b4a;\n" +
                "        margin: 100px auto;\n" +
                "        background-color: white;\n" +
                "        padding: 40px;\n" +
                "        padding-bottom: calc(15px + 40px);\n" +
                "      \"\n" +
                "    >\n" +
                "      <h1\n" +
                "        class=\"text-center title\"\n" +
                "        style=\"text-align: center; margin: 50px 0 60px 0; font-size: 50px\"\n" +
                "      >\n" +
                "        It's official!\n" +
                "      </h1>\n" +
                "      <h2>Your listing is online</h2>\n" +
                "      <p>\n" +
                "        The ad you listed on Rently is now online, available to anyone! You can\n" +
                "        update any information at anytime by navigating at the bottom of your\n" +
                "        <a>listing</a> and clicking the Update button.\n" +
                "      </p>\n" +
                "\n" +
                "      <a\n" +
                "        href=\"" + link + "\"" +
                "        class=\"listing\"\n" +
                "        style=\"\n" +
                "          display: grid;\n" +
                "          grid-template-columns: 2fr 4fr;\n" +
                "          height: auto;\n" +
                "          border: 2px solid #eee;\n" +
                "          margin: 30px auto;\n" +
                "          border-radius: 9px;\n" +
                "          overflow: hidden;\n" +
                "          transition: 0.2s;\n" +
                "        \"\n" +
                "      >\n" +
                "        <img\n" +
                "          class=\"image\"\n" +
                "          src=\""+ image +"\"\n" +
                "          style=\"width: 100%; aspect-ratio: 1; object-fit: cover\"\n" +
                "        />\n" +
                "\n" +
                "        <div class=\"details\" style=\"padding: 5px 25px\">\n" +
                "          <h2\n" +
                "            class=\"name\"\n" +
                "            style=\"\n" +
                "              text-overflow: ellipsis;\n" +
                "              white-space: nowrap;\n" +
                "              overflow: hidden;\n" +
                "              width: 300px;\n" +
                "            \"\n" +
                "          >\n" +
                "            " + title + "\n" +
                "          </h2>\n" +
                "          <div\n" +
                "            class=\"desc\"\n" +
                "            style=\"\n" +
                "              overflow: hidden;\n" +
                "              width: 350px;\n" +
                "              display: -webkit-box;\n" +
                "              -webkit-line-clamp: 4;\n" +
                "              -webkit-box-orient: vertical;\n" +
                "            \"\n" +
                "          >\n" +
                "            " + description + "\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </a>\n" +
                "\n" +
                "      <p\n" +
                "        class=\"logo text-center\"\n" +
                "        style=\"\n" +
                "          text-align: center;\n" +
                "          font-size: 20px;\n" +
                "          font-weight: bolder;\n" +
                "          color: #f14b4a;\n" +
                "          margin-top: 70px;\n" +
                "        \"\n" +
                "      >\n" +
                "        Rently.io\n" +
                "      </p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
