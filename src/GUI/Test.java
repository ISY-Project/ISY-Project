package src.GUI;

// max value and major and minor ticks painted.
import javax.swing.*;
import javax.swing.event.*;

public class Test extends JFrame implements ChangeListener{
	static JFrame frame;
	static JSlider slider;
	static JLabel sliderLabel;

	// main class
	public static void main(String[] args)
	{
		// create a new frame
		frame = new JFrame("frame");

		// create a object
		Test test = new Test();

		// create label
		sliderLabel = new JLabel();

		// create a panel
		JPanel pannel = new JPanel();

		// create a slider
		slider = new JSlider(0, 1, 0);

		// paint the ticks and tracks
		slider.setPaintTrack(true);
		slider.setPaintTicks(true);

		// set spacing
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);

		// setChangeListener
		slider.addChangeListener(test);

		// add slider to panel
		pannel.add(slider);
		pannel.add(sliderLabel);

		frame.add(pannel);

		// set the text of label
		String text = getBoolean(slider.getValue()) ? "Manual" : "Algorithm";
		sliderLabel.setText(text);

		// set the size of frame
		frame.setSize(300, 300);

		frame.setVisible(true);
	}

	// if JSlider value is changed
	@Override
	public void stateChanged(ChangeEvent e) {
		String text = getBoolean(slider.getValue()) ? "Manual" : "Algorithm";
		sliderLabel.setText(text);
	}

	public static boolean getBoolean(int value) {
        return (value!=0);
    }
}