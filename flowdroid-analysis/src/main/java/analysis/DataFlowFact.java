package analysis;

import soot.*;
import soot.jimple.internal.JimpleLocal;

public class DataFlowFact {
    private static DataFlowFact ZERO;
    private final Local variable;
    private SootField field;
    private Value offset;

    public static DataFlowFact zero(){
        if(ZERO == null){
            ZERO = new DataFlowFact();
        }
        return ZERO;
    }

    private DataFlowFact(){
        //Used for ZERO fact.
        this(new JimpleLocal("<ZERO>", Scene.v().getRefType("java.lang.Object")));
    }

    /**
     * Use this constructor for the dataflow analysis in Exercise 1.
     * @param variable the Jimple local that contains tainted information
     */
    public DataFlowFact(Local variable){
        this(variable, (SootField) null);
    }

    /**
     * Use this constructor for the field-based dataflow analysis in Exercise 2.
     * @param field the soot field that receives a tainted data-flow.
     */
    public DataFlowFact(SootField field){
        //A field-based data flow fact has a local variable "FIELDBASED" as base. This is a dummy local variable that we construct here.
        this(new JimpleLocal("FIELDBASED", Scene.v().getRefType("java.lang.Object")),field);
    }

    public DataFlowFact(Immediate offset){
        //A field-based data flow fact has a local variable "FIELDBASED" as base. This is a dummy local variable that we construct here.
        this(new JimpleLocal("OFFSETBASED", Scene.v().getRefType("java.lang.Object")),offset);
    }

    /**
     * Use this constructor for the field-sensitive dataflow analysis of Exercise 3.
     * @param variable the base variable at a field write statement that receives the taint.
     * @param field the soot field that receives a tainted data-flow.
     */
    public DataFlowFact(Local variable, SootField field){
        this.variable = variable;
        this.field = field;
    }

    public DataFlowFact(Local variable, Value offset){
        this.variable = variable;
        this.offset = offset;
    }

    public String toString(){
//        return variable + (field == null ?  "" : " " + field);
        return variable + (field == null ?  "" : " " + field) + (offset == null ?  "" : " " + offset);
    }

    public Local getVariable(){
        return variable;
    }

    public SootField getField(){
        return field;
    }

    public Value getOffset(){
        return offset;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((offset == null) ? 0 : offset.hashCode());
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((variable == null) ? 0 : variable.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataFlowFact other = (DataFlowFact) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        if (offset == null) {
            if (other.offset != null)
                return false;
        } else if (!offset.equals(other.offset))
            return false;
        if (variable == null) {
            if (other.variable != null)
                return false;
        } else if (!variable.equals(other.variable))
            return false;
        return true;
    }

}