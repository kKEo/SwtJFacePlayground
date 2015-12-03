package org.maziarz.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public final class HoverHelpDialog {
	private Shell hoverShell = null;
	private String helpMessage = null;
	private int messageLines = 1;
	private int msgLength = 30;

	public HoverHelpDialog(Shell shell, String message) {
		this.hoverShell = shell;
		this.helpMessage = getWrappedString(message, msgLength);
		getNoOfLines(message);
	}

	private int getNoOfLines(String message) {
		messageLines = (message.length() / msgLength) + 2;
		return messageLines;
	}


	public static void addTextListener(final Text text) {
		try {
			Listener shellListener = new Listener() {
				int startX, startY;

				public void handleEvent(Event e) {
					// To track the x and y position
					if (e.type == SWT.MouseDown && e.button == 1) {
						startX = e.x;
						startY = e.y;
					}
					// Track the position of x and y and set the location of the shell accordingly
					if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
						Point p = text.getShell().toDisplay(e.x, e.y);
						p.x -= startX;
						p.y -= startY;
						text.getShell().setLocation(p);
					}
					// Close the shell if it out of focus
					if (e.type == SWT.FocusOut) {
						text.getShell().dispose();
					}
				}
			};
			text.addListener(SWT.MouseDown, shellListener);
			text.addListener(SWT.MouseMove, shellListener);
			text.addListener(SWT.FocusOut, shellListener);

			text.getShell().addControlListener(new ControlAdapter() {
				public void controlResized(ControlEvent e) {
					text.setBounds(text.getShell().getClientArea());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getWrappedString(String str, int lengthSize) {
		StringBuilder wrappedString = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0, n = str.length(); i < n; i++) {
				sb.append(str.charAt(i));
				if (sb.length() == lengthSize) {
					wrappedString.append(sb.toString()).append("\n");
					sb = null;
					sb = new StringBuilder();
				}
			}
			wrappedString.append(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wrappedString.toString();
	}

	/**
	 * Method used to open the dialog box
	 */
	public void open() {
		final Shell shell = new Shell(hoverShell, SWT.RESIZE | SWT.BORDER);
		try {
			shell.setSize(200, 200);
			shell.setLayout(new RowLayout());
			final Text label = new Text(shell, SWT.READ_ONLY);
			label.setText(helpMessage);
			label.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			shell.open();
			addTextListener(label);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM);

		shell.setText("Hello");

		// Button to show the hover dialog
		Button btn = new Button(shell, SWT.PUSH);
		btn.setText("Press to see the Hover dialog");
		btn.setBounds(90, 10, 200, 30);

		btn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				new HoverHelpDialog(new Shell(), "Test displayed in window").open();
			}
		});

		shell.setSize(400, 200);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}