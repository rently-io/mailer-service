package io.rently.mailerservice.mailer.templates;

public class Welcome {
    public final String person;

    public Welcome(String person) {
        this.person = person;
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
                "      color: black;\n" +
                "      padding: 50px 0;\n" +
                "    \"\n" +
                "  >\n" +
                "    <div\n" +
                "      class=\"content\"\n" +
                "      style=\"\n" +
                "        position: relative;\n" +
                "        display: block;\n" +
                "        width: 600px;\n" +
                "        height: auto;\n" +
                "        border-radius: 7px;\n" +
                "        margin: 0 auto;\n" +
                "        background-color: white;\n" +
                "        padding: 40px;\n" +
                "        border-top: solid 15px #f14b4a;\n" +
                "        padding-bottom: calc(15px +40px);\n" +
                "      \"\n" +
                "    >\n" +
                "      <h1\n" +
                "        class=\"text-center title\"\n" +
                "        style=\"\n" +
                "          text-align: center;\n" +
                "          margin: 50px 0 60px 0;\n" +
                "          font-size: 50px;\n" +
                "          line-height: 70px;\n" +
                "        \"\n" +
                "      >\n" +
                "        The Rently community welcomes you, " + person + "!\n" +
                "      </h1>\n" +
                "\n" +
                "      <img\n" +
                "        class=\"image\"\n" +
                "        src=\"https://i.imgur.com/DtX8rWd.png\"\n" +
                "        style=\"display: block; width: 60%; margin: auto\"\n" +
                "      />\n" +
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
