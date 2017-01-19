import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

import java.io.IOException;
import java.util.Scanner;


public class Reader {
    private NetcdfFile nc = null;
    private String fileName;
    private Scanner scanner;


    public Reader(String filePath){
        fileName = filePath;
        scanner = new Scanner(System.in);
    }

    public void readWholeFile(){
        try {
            nc = NetcdfDataset.open(fileName, null);
//            System.out.println("Краткое описание файла: " + nc.findGlobalAttribute("title").getStringValue());
//            System.out.println(nc.getDimensions().size());
            System.out.println(nc);
            System.out.println("---------------------------------");
            System.out.println("Хотите построить диаграмму? д/н ");
            System.out.println("---------------------------------");
            String answer = scanner.next();

            if (answer.equals("д")){
                getData();
            }
            else {
                return;
            }
        }
        catch (IOException ioe) {
            System.out.println("При попытке открыть файл " + fileName + " произошла ошибка: " + ioe);


        } finally {
            if (null != nc) try {
              nc.close();
            } catch (IOException ioe) {
                System.out.println("При попытке закрыть файл " + fileName + " произошла ошибка: " + ioe);
            }
        }
    }

    public void getData(){
        String varName = "data";
        Variable v = nc.findVariable(varName);
        if (null == v) return;
        try {
            String dimensions = "0:70, 70, 50";
            Array data = v.read(dimensions);
//            System.out.println(data);
//            NCdumpW.printArray(data, varName, System.out, null);
            // Вывод всех данных
//            for (int i = 0; i < data.getSize(); i++){
//                System.out.println(data.getShort(i));
//            }

        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (InvalidRangeException e) {
            System.out.println(e);
        }
    }
}
