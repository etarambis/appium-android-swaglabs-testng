package data;

import com.poiji.bind.Poiji;
import models.ErrorMessage;

import java.io.File;
import java.util.List;

public class ExcelReader {
    private static final String EXCEL_PATH = "src/test/resources/data/dataExcel.xlsx";

    public static List<ErrorMessage> getErrorMessageList() {
        return Poiji.fromExcel(new File(EXCEL_PATH), ErrorMessage.class);
    }
}