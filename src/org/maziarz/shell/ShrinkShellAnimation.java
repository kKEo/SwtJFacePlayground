package org.maziarz.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is used to show the animation
 * by closing the shell.
 * @author Debadatta Mishra(PIKU)
 *
 */
public final class ShrinkShellAnimation
{
	/**
	 * This method is used to place the shell in
	 * the centre of the monitor area.
	 * @param shell of type {@link Shell}
	 * @author Debadatta Mishra(PIKU)
	 */
	private static void setShellLocation( Shell shell )
	{
		Rectangle monitorArea = shell.getDisplay().getPrimaryMonitor().getBounds();
		Rectangle shellArea = shell.getBounds();
		int x = monitorArea.x + (monitorArea.width - shellArea.width)/2;
		int y = monitorArea.y + (monitorArea.height - shellArea.height)/2;
		shell.setLocation(x,y);
	}

	/**
	 * This method is used to show the animation
	 * by decreasing the x and y coordinates and
	 * by setting the size dynamically.
	 * @param shell of type {@link Shell}
	 */
	private static void doAnimation( Shell shell )
	{
		Point shellArea = shell.getSize();
		int x = shellArea.x;
		int y = shellArea.y;
		while( x != -200 )
		{
			try
			{
				shell.setSize(x--, y--);
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
	}

	//Main method to execute the test.
	public static void main(String[] args) 
	{
		final Display display = new Display();
		final Shell shell = new Shell( display , SWT.CLOSE );
		shell.setText("Close and see the efffect");
		//Add the shell listener to handle the animated close event
		shell.addShellListener( new ShellAdapter()
		{
			public void shellClosed(ShellEvent se) 
			{
				doAnimation(shell);
			}
		}
		);
		shell.setSize(400, 200);
		setShellLocation(shell);
		shell.open ();
		while (!shell.isDisposed ()) 
		{
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
}