package org.maziarz.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public final class AnimatedShell {

	private static int step = -1;

	private static void toogle(final Shell shell) {
		int x = shell.getBounds().x;
		int y = shell.getBounds().y;
		int width = shell.getSize().x;
		int height = shell.getSize().y;
		
		for (int i = 0; i < width / 3; i++) {
			try {
				shell.setBounds(x -= step, y -= step, width += step, height += step);

				shell.setAlpha(240);
				Thread.sleep(2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		step = -step;
	}

	private static void setShellLocation(Shell shell) {
		Rectangle monitorArea = shell.getDisplay().getPrimaryMonitor().getBounds();
		Rectangle shellArea = shell.getBounds();
		int x = monitorArea.x + (monitorArea.width - shellArea.width) / 2;
		int y = monitorArea.y + (monitorArea.height - shellArea.height) / 2;
		shell.setLocation(x, y);
	}

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display, SWT.TITLE);
		shell.setLayout(new FillLayout());
		shell.setSize(100, 100);

		Button b = new Button(shell, SWT.PUSH);
		b.setText("Animate");

		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				toogle(shell);
			}
		});
		shell.open();
		setShellLocation(shell);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}