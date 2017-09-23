/******************************************************************************
 * Copyright © 2017 saber-nyan                                                *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *     http://www.apache.org/licenses/LICENSE-2.0                             *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package ru.saber_nyan.board_cli.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * A logger that writes to console and to file.
 */
public final class LoggerImpl {

	/**
	 * Get {@link org.slf4j.Logger}!
	 *
	 * @param clazz       context class
	 * @param logFilePath directory with logs
	 * @return logger with {@link ConsoleAppender} and {@link RollingFileAppender}
	 */
	@NotNull
	public static Logger getLogger(@NotNull Class clazz, @NotNull String logFilePath) {
		String patternStr = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		PatternLayoutEncoder fileLogEncoder = new PatternLayoutEncoder();
		fileLogEncoder.setContext(context);
		fileLogEncoder.setPattern(patternStr);
		fileLogEncoder.start();

		RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
		fileAppender.setContext(context);
		fileAppender.setName("logfile");
		fileAppender.setEncoder(fileLogEncoder);
		fileAppender.setAppend(true);

		TimeBasedRollingPolicy fileAppenderPolicy = new TimeBasedRollingPolicy();
		fileAppenderPolicy.setContext(context);
		fileAppenderPolicy.setParent(fileAppender);
		fileAppenderPolicy.setFileNamePattern(logFilePath + File.separator + "board-cli-%d{yyyy-MM-dd__HH}.log.gz");
		fileAppenderPolicy.setMaxHistory(5);
		fileAppenderPolicy.start();

		fileAppender.setRollingPolicy(fileAppenderPolicy);
		fileAppender.start();

		Logger logger = context.getLogger(clazz);
		logger.setLevel(Level.INFO);


		logger.addAppender(fileAppender);

		return logger;
	}
}
