import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

class NetCdfGUI {
    private Reader reader;
    private File file;
    private JFrame mainFrame;
    private JPanel controlPanel;

    NetCdfGUI(){
        GUI();
        makeSomeAction();
    }

    private void GUI(){
        mainFrame = new JFrame("NetCDF File reader");
        mainFrame.setSize(300,300);
        mainFrame.setLocation(100, 100);
        mainFrame.setLayout(new GridLayout(2,1));

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(controlPanel);

        mainFrame.setVisible(true);
    }

    private void makeSomeAction(){
        JButton ok = new JButton("Choose File");
        JButton submitButton = new JButton("Convert into txt");
        JButton cancelButton = new JButton("Exit");

        ok.setActionCommand("Choose File");
        submitButton.setActionCommand("Convert into txt");
        cancelButton.setActionCommand("Exit");

        ok.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());


        controlPanel.add(ok);
        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command){
                case "Choose File":
                    JFileChooser openFile = new JFileChooser();
                    int ret = openFile.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        file = openFile.getSelectedFile();
                        if (file.getName().endsWith(".nc")){
                            String filePath = file.getPath();
                            reader = new Reader(filePath);
                            reader.readWholeFile();
                        }
                        else {
                            System.out.println("Error! Choose a netcdf file!");
                            file = null;
                        }
                    }
                    else {
                        file = null;
                        System.out.println("Вы не выбрали файл!");
                    }
                    break;
                case "Convert into txt":
                    reader.convertIntoTxt(file);
                    break;
                case "Exit":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}
