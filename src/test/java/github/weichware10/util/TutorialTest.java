package github.weichware10.util;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.config.ConfigClient;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Tester {@link Tutorial}.
 */
public class TutorialTest {

    /**
     * Erbt von der abstrakten Klasse um sie zu testen.
     */
    public class TestTutorial extends Tutorial {

        public TestTutorial(ConfigClient configClient) {
            super(configClient, ToolType.CODECHARTS);
        }

        @Override
        protected void tutorial() {
            ;
        }
    }

    /**
     * Testen, ob die Einstellungen des configClient in Betracht genommen wird.
     * D.h. ob das Tutorial angezeigt wird.
     */
    @Test
    @Ignore
    public void tutorialBooleanShouldBeRespected() {
        ConfigClient configClient = new ConfigClient();
        configClient.loadConfiguration("www.weichware10.com/config");
        TestTutorial testsubject = new TestTutorial(configClient);
        configClient.getConfig().getCodeChartsConfiguration().setTutorial(true);
        testsubject.start();
        // Überprüfen, ob Tutorial angezeigt wird, da der Config-Wert true ist
        configClient.getConfig().getCodeChartsConfiguration().setTutorial(false);
        testsubject.start();
        // Überprüfen, ob Tutorial nicht angezeigt wird, da der Config-Wert false ist
    }
}
