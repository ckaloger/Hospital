package services;

import javax.enterprise.util.AnnotationLiteral;

public class ComponentSelectionAnnotationLiteral extends AnnotationLiteral<ComponentSelection> implements ComponentSelection{
    private int value;
    private ComponentSelectionAnnotationLiteral(int value) {
        this.value = value;
    }
    @Override
    public int value() {
        return value;
    }
    public static ComponentSelectionAnnotationLiteral network(int value){
        return new ComponentSelectionAnnotationLiteral(value);
    }
}