import com.google.inject.AbstractModule
import services.NgrokApplicationLifecycle

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[NgrokApplicationLifecycle]).asEagerSingleton()
  }
}
