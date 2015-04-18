package parser.xml;

public interface Parser {
    Document parse(String fileName);
    Document parse(String fileName, boolean validation);
}
