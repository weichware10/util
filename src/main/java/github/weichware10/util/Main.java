package github.weichware10.util;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.config.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration testconfig = new Configuration(new ToolType[]{ToolType.CODECHARTS, ToolType.EYETRACKING, ToolType.ZOOMMAPS});
        System.out.println(testconfig);
    }
}
