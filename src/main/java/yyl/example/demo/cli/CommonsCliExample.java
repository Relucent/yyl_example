package yyl.example.demo.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 基本参数解析，适合简单项目
 */
public class CommonsCliExample {

    public static void main(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("n", "name", true, "Name");
        options.addOption("v", "value", true, "Value");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String name = cmd.getOptionValue("name");
        String value = cmd.getOptionValue("value");

        if (name == null || value == null) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("myapp", options);
            System.exit(1);
        }

        System.out.println(name + ":" + value);
    }
}
