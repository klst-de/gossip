package com.tutorialspoint.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// aus https://stackoverflow.com/questions/1944446/close-one-jframe-without-closing-another
public class TwoJFrames // {
// -------------- EUG:
			implements Runnable {
	// tatsächlich sind es 4:
	int frameCount = 2;
	List<JFrame> frames;
	// ctor
	TwoJFrames(int nb) {
		frameCount = nb;
		frames = new ArrayList<JFrame>(frameCount);
	}
	
	private JFrame makeFrame(int frameNumber) {
		JFrame frame = new JFrame("Frame number " + frameNumber);
		frame.setDefaultCloseOperation(frames.isEmpty() ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
		frames.add(frame);
		return frame;
	}
	
	private JFrame makeFrameWithPanel(int frameNumber) {
		JFrame frame = makeFrame(frameNumber);
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel("Click on the corner to "+(frameNumber==0 ? "exit" : "close")), BorderLayout.CENTER);
		JButton newFrame = new JButton("add NewFrame");
		newFrame.addActionListener(event -> {
			JFrame newframe = makeFrameWithPanel(frames.size());
//			newframe.setContentPane(contentPane); ???
			newframe.pack();
			// If the component is null, or the GraphicsConfiguration associated with this component is null, 
			// the window is placed in the center of thescreen. 
			// The center point can be obtained with the GraphicsEnvironment.getCenterPoint method. 
			newframe.setLocationRelativeTo(null); // oben links würde es sonst angezeigt
			newframe.setVisible(true);
		});
		p.add(newFrame, BorderLayout.PAGE_END);
		frame.setContentPane(p);
		return frame;
	}

	@Override
	public void run() {
        for (int i = 0; i < frameCount; i++) {
//            JFrame frame = new JFrame("Frame number " + i);
//            frames.add(frame);   	
            // 0 : ende : sonst close
//            frame.setDefaultCloseOperation(i==0 ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
//        	JFrame frame = makeFrame(i);
//        	
//            JPanel p = new JPanel(new BorderLayout());
//            p.add(new JLabel("Click on the corner to "+(i==0 ? "exit" : "close")), BorderLayout.CENTER);
//            frame.setContentPane(p);
        	JFrame frame = makeFrameWithPanel(i);
        	
            frame.setSize(200, 200);
            frame.setLocation(100 + 20 * i, 100 + 20 * i);
            frame.setVisible(true);
        }
	}

// --------------
    public static void main(String[] args) {
        int nb = 4;
        if (args != null && args.length > 0) {
            nb = Integer.parseInt(args[0]);
        }

        final int frameCount = nb;
        SwingUtilities.invokeLater(new TwoJFrames(nb) {
//            public void run() {
//                for (int i = 0; i < frameCount; i++) {
//                    JFrame frame = new JFrame("Frame number " + i);
//                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                    JPanel p = new JPanel(new BorderLayout());
//                    p.add(new JLabel("Click on the corner to close..."), BorderLayout.CENTER);
//                    frame.setContentPane(p);
//                    frame.setSize(200, 200);
//                    frame.setLocation(100 + 20 * i, 100 + 20 * i);
//                    frame.setVisible(true);
//                }
//            }
        });

    }
}
