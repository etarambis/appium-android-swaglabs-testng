package models;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("mensajes")
public class ErrorMessage {

    @ExcelCellName("NOMBRE")
    private String name;

    @ExcelCellName("MENSAJE")
    private String message;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
