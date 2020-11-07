package testgef;
//添加Action并生成菜单
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import action.DiagramAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	 private IWorkbenchAction exitAction;
	 private IWorkbenchAction aboutAction;
	 private DiagramAction diagramAction;


	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        
        diagramAction = new DiagramAction(window);
        register(diagramAction);
	}
	
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", "File");
        fileMenu.add(diagramAction);
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        
        MenuManager helpMenu = new MenuManager("&Help", "help");
        helpMenu.add(aboutAction);
        
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
	}
	
	


}

