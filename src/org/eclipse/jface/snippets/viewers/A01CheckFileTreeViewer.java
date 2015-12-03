package org.eclipse.jface.snippets.viewers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class A01CheckFileTreeViewer {
  public static void main(String[] args) {
    new CheckFileTree();
  }
}
class CheckFileTree extends ApplicationWindow{

  public CheckFileTree() {
    super(null);
    // Don't return from open() until window closes
    setBlockOnOpen(true);

    open();
    Display.getCurrent().dispose();
  }

  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setSize(400, 400);
  }

  /**
   * Creates the main window's contents
   *
   * @param parent the main window
   * @return Control
   */
  protected Control createContents(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));

    // Create the tree viewer to display the file tree
    final CheckboxTreeViewer tv = new CheckboxTreeViewer(composite);
    tv.getTree().setLayoutData(new RowLayout(SWT.VERTICAL));
    tv.setContentProvider(new FileTreeContentProvider());
    tv.setLabelProvider(new FileTreeLabelProvider());
    tv.setInput("root"); // pass a non-null that will be ignored

    // When user checks a checkbox in the tree, check all its children
    tv.addCheckStateListener(new ICheckStateListener() {
      public void checkStateChanged(CheckStateChangedEvent event) {
        if (event.getChecked()) {
          tv.setSubtreeChecked(event.getElement(), true);
        }
      }
    });
    return composite;
  }

}


class FileTreeContentProvider implements ITreeContentProvider {
  public Object[] getChildren(Object arg0) {
    return ((File) arg0).listFiles();
  }

  public Object getParent(Object arg0) {
    return ((File) arg0).getParentFile();
  }

  public boolean hasChildren(Object arg0) {
    Object[] obj = getChildren(arg0);
    return obj == null ? false : obj.length > 0;
  }

  public Object[] getElements(Object arg0) {
    return File.listRoots();
  }

  public void dispose() {
  }

  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
  }
}

class FileTreeLabelProvider implements ILabelProvider {
  private List listeners;

  private Image file;

  private Image dir;

  public FileTreeLabelProvider() {
    listeners = new ArrayList();

    try {
      file = new Image(null, new FileInputStream("images/file.gif"));
      dir = new Image(null, new FileInputStream("images/directory.gif"));
    } catch (FileNotFoundException e) {
    }
  }

  public Image getImage(Object arg0) {
    return ((File) arg0).isDirectory() ? dir : file;
  }

  public String getText(Object arg0) {
    String text = ((File) arg0).getName();

    if (((File) arg0).getName().length() == 0) {
      text = ((File) arg0).getPath();
    }
    return text;
  }

  public void addListener(ILabelProviderListener arg0) {
    listeners.add(arg0);
  }

  public void dispose() {
    // Dispose the images
    if (dir != null)
      dir.dispose();
    if (file != null)
      file.dispose();
  }

  public boolean isLabelProperty(Object arg0, String arg1) {
    return false;
  }

  public void removeListener(ILabelProviderListener arg0) {
    listeners.remove(arg0);
  }
}