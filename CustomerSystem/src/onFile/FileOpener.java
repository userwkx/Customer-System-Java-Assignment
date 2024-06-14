package onFile;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class FileOpener {

    public static void openTextFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!Desktop.isDesktopSupported()) {
                System.out.println("不存在文件！");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                desktop.open(file);
            } else {
                System.out.println("没找到文件！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
