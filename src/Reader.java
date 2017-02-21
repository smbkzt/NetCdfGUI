import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NCdumpW;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Reader {
    private NetcdfFile nc = null;
    private String filePath;


    Reader(String filePath){
        this.filePath = filePath;
    }

    String readWholeFile(){
        try {
            nc = NetcdfDataset.open(filePath, null);
//            System.out.println(nc.getDimensions().size());
        }
        catch (IOException ioe) {
            System.out.println("При попытке открыть файл " + filePath + " произошла ошибка: " + ioe);

        } finally {
            if (null != nc) try {
              nc.close();
            } catch (IOException ioe) {
                System.out.println("При попытке закрыть файл " + filePath + " произошла ошибка: " + ioe);
            }
        }
        return nc.toString();
    }

    void convertIntoTxt(File file){
        String path = file.getParent() + file.getName().replaceAll(".nc", ".txt");
        System.out.println(path);

        try(FileWriter fileWriter = new FileWriter(path, false)){
            fileWriter.append(readWholeFile());
            fileWriter.flush();

        } catch (IOException e){
            System.out.println(e);
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
            NCdumpW.printArray(data, varName, System.out, null);
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
