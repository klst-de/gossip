package org.compiere.apps.form;

import java.util.logging.Logger;

import com.klst.gossip.GenericFormPanel;

public class WorkflowActivities extends GenericFormPanel {

	private static final Logger LOG = Logger.getLogger(WorkflowActivities.class.getName());
	
	private static int WF_ACTIVITIES_AD_Window_ID = 298;
	// TableName=AD_WF_Activity AD_Table_ID=644 / AD_Tab_ID=576 / AD_Window_ID=298
	
	public WorkflowActivities() {
		super(WF_ACTIVITIES_AD_Window_ID);
	}

	// TODO registerTableSelectionListener
}
