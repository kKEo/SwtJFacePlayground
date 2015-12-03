package org.maziarz.layouts;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class A03ScrolledCompositeTest {
  public void run() {
    Display display = new Display();
    Shell shell = new Shell(display);
    createContents(shell);
    shell.setSize(200, 200);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  private void createContents(Composite parent) {
    parent.setLayout(new FillLayout());

    ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);

    Composite child = new Composite(sc, SWT.NONE);
    child.setLayout(new FillLayout());

    new Button(child, SWT.PUSH).setText("One");
    new Button(child, SWT.PUSH).setText("Two");

    sc.setContent(child);

    sc.setMinSize(400, 400);

    sc.setExpandHorizontal(true);
    sc.setExpandVertical(true);
  }

  public static void main(String[] args) {
    new A03ScrolledCompositeTest().run();
  }
}