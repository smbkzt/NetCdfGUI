import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class NetCdfGUI {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;


    public NetCdfGUI(){
        GUI();
        makeSomeAction();
    }

    private void GUI(){
        mainFrame = new JFrame("NetCDF File reader");
        mainFrame.setSize(300,300);
        mainFrame.setLocation(100, 100);
        mainFrame.setLayout(new GridLayout(3,1));

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);

        mainFrame.setVisible(true);
    }

    private void makeSomeAction(){
        headerLabel.setText("Control in action: Button");

        JButton ok = new JButton("Choose File");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        ok.setActionCommand("Choose File");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

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
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        String filePath = file.getPath();
                        Reader reader = new Reader(filePath);
                        reader.readWholeFile();
                    }
                    break;
                case "Submit":
                    statusLabel.setText("Submit button is clicked");
                    break;
                case "Cancel":
                    statusLabel.setText("Ok button is clicked");
                    break;
                default:
                    statusLabel.setText("Error");
                    break;
            }
        }
    }
}
