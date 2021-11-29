package github.weichware10.util;

import org.junit.Ignore;
import org.junit.Test;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.config.ConfigClient;

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
        ;
    }
}
