package ru.saber_nyan;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.io.PrintStream;

public class Main {

	private final static PrintStream err = System.err;
	private final static PrintStream out = System.out;
	private final static int ERR_TERMINAL_INIT = 1;
	private final static int ERR_TERMINAL_OPER = 2;

	public static void main(String[] args) {
//		Terminal terminal = null;
//		Screen screen = null;
//		try {
//			terminal = new DefaultTerminalFactory().createTerminal();
//			screen = new TerminalScreen(terminal);
//			terminal.enterPrivateMode();
//		} catch (IOException e) {
//			err.println("Error in terminal init:");
//			e.printStackTrace(err);
//			System.exit(ERR_TERMINAL_INIT);
//		}
//
//
//		try {
//			Panel panel = new Panel(new GridLayout(2));
//			panel.addComponent(new TextBox("test!", TextBox.Style.SINGLE_LINE));
//			panel.addComponent(new Button("OK!", () -> out.println("Clicked \'OK!\'...")));
//
//			BasicWindow window = new BasicWindow("Test Terminal!");
//			window.setComponent(panel);
//
//			MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(terminal.getTerminalSize()), new EmptySpace(TextColor.ANSI.YELLOW));
//
//			gui.addWindowAndWait(window);
//		} catch (IOException e) {
//			err.println("Error in terminal operation:");
//			e.printStackTrace(err);
//			System.exit(ERR_TERMINAL_OPER);
//		}

		// Setup terminal and screen layers
		Screen screen = null;

		try {
			Terminal terminal = new DefaultTerminalFactory().createTerminal();
			screen = new TerminalScreen(terminal);
			screen.startScreen();
		} catch (IOException e) {
			err.println("Error in terminal init:");
			e.printStackTrace(err);
			System.exit(ERR_TERMINAL_INIT);
		}

		// Create panel to hold components
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		panel.addComponent(new Label("Forename"));
		panel.addComponent(new TextBox());

		panel.addComponent(new Label("Surname"));
		panel.addComponent(new TextBox());

		panel.addComponent(new Button("Close", () -> System.exit(0)));
		panel.addComponent(new Button("Submit", () -> out.println("\"Submit\" clicked!")));

		BasicWindow window = new BasicWindow();
		window.setComponent(panel);

		MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.CYAN));
		gui.addWindowAndWait(window);

	}
}
