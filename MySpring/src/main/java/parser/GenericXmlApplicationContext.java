package parser;

import parser.xml.ParserTypes;

public class GenericXmlApplicationContext {
    private final XmlBeanDefinitionReader reader;
    private BeanFactory beanFactory = new ConcreteBeanFactory();

    public GenericXmlApplicationContext() {
        this.reader = new XmlBeanDefinitionReader(this);
    }

    public void setValidating(boolean validating) {
        reader.setValidating(validating);
    }

    public void setParserType(ParserTypes parserType) {
        reader.setParserType(parserType);
    }

    public void load(String xmlFileLocation) {
        reader.loadBeanDefinitions(xmlFileLocation);
    }
    public BeanFactory getBeanFactory() {
        return beanFactory;
    } // - создает и возвращает экземпляр BeanFactory, в котором хранится ссылка
      // на объектное представление конфигурационного xml-файла

}
