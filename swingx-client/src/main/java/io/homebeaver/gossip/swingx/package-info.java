/*

in (client) gibt es package org.compiere.swing 
mit Klassen Cxxx, wobei C wahrscheinlich für Client steht, könnte auch Compiere sein

Bsp.:
CTextField extends JTextField implements CEditor, KeyListener

In gossip leite ich das Meiste von swingx ab, aka jdesktop, swingset3

Bsp.:
CTextField extends JXTextField implements CEditor, KeyListener
aber
public class CCheckBox extends JCheckBox implements CEditor ... weil in swingx JCheckBox nicht erweitert wurde

 */
package io.homebeaver.gossip.swingx;