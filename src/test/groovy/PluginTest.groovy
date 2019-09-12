import org.gradle.api.Project
import org.gradle.internal.impldep.org.junit.Test
import org.gradle.testfixtures.ProjectBuilder

class PluginTest {

    @Test
    public void testPlugin(){
        Project project = new ProjectBuilder().build()
        project.pluginManager.apply("com.shengsheng")
    }
}
