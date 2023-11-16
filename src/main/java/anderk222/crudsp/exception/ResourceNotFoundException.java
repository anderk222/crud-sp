package anderk222.crudsp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResourceNotFoundException extends RuntimeException {

    private Object id;
    private String field;
    private String resource;

    public ResourceNotFoundException(Object _id, String _field, String _resource){
        super(String.format("%s with %s %s", _resource,_field,_id));

        this.id = _id;
        this.field = _field;
        this.resource = _resource;


    }

    public ResourceNotFoundException(){

        super("Resource not found");

    }


}
