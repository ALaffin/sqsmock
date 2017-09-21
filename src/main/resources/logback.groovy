import ch.qos.logback.core.*;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

appender(name="CONSOLE", clazz=ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "name=sqsmock date=%date{ISO8601} level=%level actor=%X{akkaSource} message=%msg\n"
    }
}

logger(name="sqsmock", level=INFO)
logger(name="com.amazonaws", level=WARN)

root(level=INFO, appenderNames=["CONSOLE"])