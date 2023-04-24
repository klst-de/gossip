package io.homebeaver.gossip.swingx;

import java.awt.Component;

import javax.swing.JScrollPane;

/**
 *	Adempiere Srcoll Pane.
 *	
 *  @author Jorg Janke
 *  @version $Id: CScrollPane.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
//copied from package org.compiere.swing
public class CScrollPane extends JScrollPane {

	private static final long serialVersionUID = -2941967111871448295L;


	/**
	 * 	Adempiere ScollPane
	 */
	public CScrollPane ()
	{
		this (null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}	//	CScollPane

	/**
	 * 	Adempiere ScollPane
	 *	@param vsbPolicy vertical policy
	 *	@param hsbPolicy horizontal policy
	 */
	public CScrollPane (int vsbPolicy, int hsbPolicy)
	{
		this (null, vsbPolicy, hsbPolicy);
	}	//	CScollPane

	/**
	 * 	Adempiere ScollPane
	 *	@param view view
	 */
	public CScrollPane (Component view)
	{
		this (view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}	//	CScollPane

	/**
	 * 	Adempiere ScollPane
	 *	@param view view
	 *	@param vsbPolicy vertical policy
	 *	@param hsbPolicy horizontal policy
	 */
	public CScrollPane (Component view, int vsbPolicy, int hsbPolicy)
	{
		super (view, vsbPolicy, hsbPolicy);
		setOpaque(false);
		getViewport().setOpaque(false);
	}	//	CScollPane
	
	
	/**
	 *  Set Background
	 *  @param bg AdempiereColor for Background, if null set standard background
	 */
//	public void setBackgroundColor (CompiereColor bg)
//	{
//		if (bg == null)
//			bg = new CompiereColor(AdempierePLAF.getFormBackground());
//		putClientProperty(CompiereLookAndFeel.BACKGROUND, bg);
//	}   //  setBackground

}
