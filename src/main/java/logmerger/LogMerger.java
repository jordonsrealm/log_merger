package logmerger;

import components.MainWindow;


public class LogMerger {

    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow();
        mainWindow.populateFrame();
        mainWindow.setFrameConstraints();
    }
}
