package parser.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBeanDefinition {
    private MyBeanDefinition parent;
    private List<MyBeanDefinition> children;

    private String name;
    private String data;
    private Map<String, String> attributes;

    public MyBeanDefinition() {
        parent = null;
        children = new ArrayList<>();
        attributes = new HashMap<>();
    }

    public MyBeanDefinition getParent() {
        return parent;
    }

    public void setParent(MyBeanDefinition parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<MyBeanDefinition> getChildren() {
        return children;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void addChild(MyBeanDefinition node) {
        this.children.add(node);
    }

    public void addAtribute(String name, String value) {
        attributes.put(name, value);
    }
}
