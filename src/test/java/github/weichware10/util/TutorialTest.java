package github.weichware10.util;

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
}
