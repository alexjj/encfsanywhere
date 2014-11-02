package de.voot.encfsanywhere.client.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.google.gwt.event.shared.HandlerManager;

import de.voot.encfsanywhere.client.event.AlertEvent;
import de.voot.encfsanywhere.client.gin.InjectorHolder;

/**
 * This Handler sends an AlertEvent to the eventbus if a log message appears
 * with level > INFO.
 */
public class AlertEventLogHandler extends Handler {
	private HandlerManager eventBus = InjectorHolder.getInstance().getHandlerManager();

	@Override
	public void publish(LogRecord record) {
		if (isLoggable(record) && record.getLevel().intValue() > Level.INFO.intValue()) {
			String caption = "Error";
			String text = record.getLevel().getName() + ": " + record.getMessage() + "\n\nSee console for greater detail.";
			eventBus.fireEvent(new AlertEvent(caption, text));
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() {
	}

}
