import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.DENY

import java.nio.charset.Charset


conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

def LOG_LEVEL_PATTERN = System.getProperty("LOG_LEVEL_PATTERN") ?: '%5p'
def PID = System.getProperty("PID") ?: "- "
def LOG_EXCEPTION_CONVERSION_WORD = System.getProperty("LOG_EXCEPTION_CONVERSION_WORD") ?: '%xEx'
def CONSOLE_LOG_PATTERN = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} " +
        "%clr(${LOG_LEVEL_PATTERN}) %clr(${PID}){magenta} " +
        "%clr(---){faint} %clr([%15.15t]){faint}" +
        "%clr(%-40.40logger{39}){cyan} " +
        "%clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD}"
scan("2 minutes")
// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        outputPatternAsHeader: true
        charset = Charset.forName('UTF-8')
        pattern = CONSOLE_LOG_PATTERN
    }
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}


def HOME_DIR = "."

//ERROR级别的日志RollingFileAppender
appender("ERROR", RollingFileAppender) {
    //过滤器，只记录ERROR级别的日志
    filter(LevelFilter) {
        level = ERROR
        onMatch = ACCEPT
        onMismatch = DENY
    }
    encoder(PatternLayoutEncoder) {
        //默认为pattern = "%level %logger - %msg%n"
        //%d表示日期，%thread表示线程名，%level日志级别，%file具体的文件，%line记录日志位置，%msg日志消息，%n换行符
//        pattern = "%-4(%d{HH:mm:ss.SSS} [%thread]) %-5level %logger{32} \\(%file:%line\\) - %msg%n"
        pattern = CONSOLE_LOG_PATTERN
    }
    //指定日志生成格式TimeBasedRollingPolicy
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${HOME_DIR}/logs/%d{yyyy-MM-dd}_ERROR_%i.log"
        maxHistory = 30 //日志最长保留30天SizeAndTimeBasedFNATP
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "10MB"
        }
    }

    //约束生成单个日志文件大小为10M，超过后新建一个文件保存
//    triggeringPolicy(SizeBasedTriggeringPolicy) {
//        maxFileSize = "10MB"
//    }
    append = true
}
logger("console", ERROR, ['ERROR'], false)

//INFO级别的日志RollingFileAppender
appender("INFO", RollingFileAppender) {
    //过滤器，只记录INFO级别的日志
    filter(LevelFilter) {
        level = INFO
        onMatch = ACCEPT
        onMismatch = DENY
    }
    //PatternLayoutEncoder对输出日志信息进行格式化
    encoder(PatternLayoutEncoder) {
        //默认为pattern = "%level %logger - %msg%n"
        //%d表示日期，%thread表示线程名，%level日志级别，%file具体的文件，%line记录日志位置，%msg日志消息，%n换行符
        pattern = CONSOLE_LOG_PATTERN
    }
    //指定日志生成格式，文件名以日期命名，生成每日日志文件，如果超出大小则另起文件存放
    //%d{yyyy-MM-dd}-日期，%i-用于记录每日日志个数
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${HOME_DIR}/logs/%d{yyyy-MM-dd}_INFO_%i.log"
        maxHistory = 30 //日志最长保留30天
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "10MB" //单个日志文件最大为10MB
        }
    }
//    //约束生成单个日志文件大小为10M，超过后新建一个文件保存
//    triggeringPolicy(SizeBasedTriggeringPolicy) {
//        maxFileSize = "10MB"
//    }
    append = true
}
//为false表示只打印到本log的appender中，不打印父类
logger("console", INFO, ['INFO'], false)

//将指定级别的日志输出到日志文件中

logger('org.springframework.security', INFO, ['STDOUT'], false)
logger('grails.plugin.springsecurity', INFO, ['STDOUT'], false)
root(ERROR, ['ERROR'])
//root(INFO, ['INFO'])
root(INFO, ['STDOUT', 'INFO'])









