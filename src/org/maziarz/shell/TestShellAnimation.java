package org.maziarz.shell;

import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class TestShellAnimation {

	private static void doAnimation(Shell shell) {
		for (int i = 21; i < 501; i++) {
			shell.setSize(20 + i, 20 + i);
		}
	}

	private static void openAnimation(Shell shell) {
		try {
			shell.open();
			doAnimation(shell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void shrinkClose(final Shell shell) {
		Display.getCurrent().syncExec(new Runnable() {
			@Override
			public void run() {
				int x = shell.getSize().x;
				int y = shell.getSize().y;
				while (x != -200) {
					try {
						shell.setSize(x - 1, y - 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// Main method to execute
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());
		shell.setSize(20, 20);
		Button b = new Button(shell, SWT.BORDER | SWT.BOLD);
		b.setText("Close me");

		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				shrinkClose(shell);
				shell.dispose();
			}
		});
		openAnimation(shell);
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event arg0) {
				shrinkClose(shell);
			}
		});
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}