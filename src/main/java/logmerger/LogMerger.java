package logmerger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import components.MainWindow;


public class LogMerger {

    public static void main(String[] args){
    	ExecutorService mainExecutor = Executors.newFixedThreadPool(4);
        MainWindow mainWindow = new MainWindow(mainExecutor);
        mainWindow.populateFrame();
        mainWindow.setFrameConstraints();
    }
}
