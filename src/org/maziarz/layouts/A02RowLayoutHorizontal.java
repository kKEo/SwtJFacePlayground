package org.maziarz.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class A02RowLayoutHorizontal {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		// shell.setLayout(new FillLayout(SWT.VERTICAL));

		GridLayout layout = new GridLayout();
		shell.setLayout(layout);

		{
			Composite c = new Composite(shell, SWT.NONE);
			c.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));
			RowLayout l = new RowLayout(SWT.HORIZONTAL);
			l.pack = false;
			c.setLayout(l);

			new Button(c, SWT.PUSH).setText("one");
			new Button(c, SWT.PUSH).setText("two");
			new Button(c, SWT.PUSH).setText("three");
			new Button(c, SWT.PUSH).setText("four");
			new Button(c, SWT.PUSH).setText("five");
			new Button(c, SWT.PUSH).setText("six");
			new Button(c, SWT.PUSH).setText("seven");
		}

		{
			Composite c = new Composite(shell, SWT.NONE);
			c.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));
			c.setLayout(new RowLayout(SWT.HORIZONTAL));

			new Button(c, SWT.PUSH).setText("1one");
			new Button(c, SWT.PUSH).setText("2two");
			new Button(c, SWT.PUSH).setText("3three");
			new Button(c, SWT.PUSH).setText("4four");
		}

		shell.setSize(200, 200);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}