package parser.xml;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Document {

    private MyBeanDefinition root;

    public Document(MyBeanDefinition root) {
        this.root = root;
    }

    public List<MyBeanDefinition> getNodesByTagName(String tagName) {
        LinkedList<MyBeanDefinition> stack = new LinkedList<>();
        List<MyBeanDefinition> result = new ArrayList<>();
        stack.add(root);
        // int index = 0;
        while (!stack.isEmpty()) {
            if (stack.peek().getData().equals(tagName)) {
                result.add(stack.getLast());
            }
            if (stack.peek().getChildren() != null) {
                stack.addAll(0, stack.pop().getChildren());
            }
            // stack.pop();
        }
        return result;
    }
}
