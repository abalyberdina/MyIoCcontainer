package parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.xml.Document;
import parser.xml.MyBeanDefinition;

public class ConcreteBeanFactory implements BeanFactory {

    private Document document;
    private Map<String, Object> cache;

    private final String CONSTRTUCTOR_ARGS_DEFINITION = "constructor-arg";
    private final String SIMPLE_TYPE_DEFINITION = "value";
    private final String REFERENCE_DEFINITION = "ref";
    private final String PROPERTY_DEFINITION = "property";
    private final String BEAN_DEFINITION = "bean";
    private final String ID_DEFINITION = "id";
    private final String CLASS_DEFINITION = "class";
    private final String TYPE_DEFINITION = "type";
    private final String NAME_DEFINITION = "name";
    
    @Override
    public Object getBean(String string) {
        return getBean(string, Object.class);
    }

    @Override
    public <T> T getBean(String string, Class<T> type) {
        if (cache == null) {
            cache = new HashMap<>();
        }
        if (cache.containsKey(string)) {
            return (T) cache.get(string);
        }
        List<MyBeanDefinition> beans = document.getNodesByTagName(BEAN_DEFINITION);
        MyBeanDefinition result = null;
        for (MyBeanDefinition node : beans) {
            if (node.getAttributes().get(ID_DEFINITION).equals(string)) {
                result = node;
            }
        }
        if (result != null) {
            try {
                T resBean = (T) createObject(result);
                setProperties(result, resBean);
                cache.put(string, resBean);
                return resBean;
            } catch (InstantiationException | IllegalAccessException
                    | ClassNotFoundException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException
                    | SecurityException | NoSuchFieldException e) {
                System.out.println("Can't create object");
            }
        }
        return null;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    private Object createObject(MyBeanDefinition node) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {

        List<Class<?>> constrTypes = new ArrayList<>();
        List<Object> constrArgs = new ArrayList<>();
        String className = null;
        if (node.getAttributes().get(CLASS_DEFINITION) != null) {
            className = node.getAttributes().get(CLASS_DEFINITION);
        }
        Class<?> clazz = Class.forName(className);
        setConstuctorArgs(node, constrTypes, constrArgs);
        Object result = null;
        Constructor constructor = clazz.getConstructor((Class<?>[]) constrTypes
                .toArray());
        result = constructor.newInstance(constrArgs.toArray());
        return result;
    }

    private void setConstuctorArgs(MyBeanDefinition node, List<Class<?>> constrTypes,
            List<Object> constrArgs) throws ClassNotFoundException {
        List<MyBeanDefinition> properties = node.getChildren();
        for (MyBeanDefinition element : properties) {
            if (element.getName().equals(CONSTRTUCTOR_ARGS_DEFINITION)) {
                if (element.getAttributes().get(SIMPLE_TYPE_DEFINITION) != null) {
                    // try {
                    // int value = Integer.parseInt(element.getAttributes()
                    // .get(SIMPLE_TYPE));
                    // constrTypes.add(Integer.class);
                    // constrArgs.add(value);
                    // } catch (Exception e) {
                    // try {
                    // double value = Double.parseDouble(element
                    // .getAttributes().get(SIMPLE_TYPE));
                    // constrTypes.add(Double.class);
                    // constrArgs.add(value);
                    // } catch (Exception ex) {
                    // constrTypes.add(String.class);
                    // constrArgs.add(element.getAttributes().get(
                    // SIMPLE_TYPE));
                    // }
                    // }
                    if (element.getAttributes().get(TYPE_DEFINITION) != null) {
                        Class<?> clazz = Class.forName(element.getAttributes()
                                .get(TYPE_DEFINITION));
                        constrTypes.add(clazz);
                        constrArgs.add(element.getAttributes().get(
                                SIMPLE_TYPE_DEFINITION));
                    } else {
                        constrTypes.add(String.class);
                        constrArgs.add(element.getAttributes().get(
                                SIMPLE_TYPE_DEFINITION));
                    }
                }
                if (element.getAttributes().get(REFERENCE_DEFINITION) != null) {
                    Object bean = this.getBean(element.getAttributes().get(
                            REFERENCE_DEFINITION));
                    constrArgs.add(bean);
                    constrTypes.add(bean.getClass());
                }
            }
        }
    }

    private void setProperties(MyBeanDefinition node, Object bean)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        List<MyBeanDefinition> properties = node.getChildren();
        Class<?> clazz = bean.getClass();
        Field field = null;
        for (MyBeanDefinition element : properties) {
            if (element.getName().equals(PROPERTY_DEFINITION)) {
                field = clazz.getDeclaredField(element.getAttributes().get(
                        NAME_DEFINITION));
                field.set(bean,
                        element.getAttributes().get(SIMPLE_TYPE_DEFINITION));
            }
        }
    }
}
