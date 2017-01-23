package yyl.example.basic.sound;

import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * 播放MIDI音乐 (Window-XP,7,8)
 */
public class PlayMidi {
	private static Sequencer midi;
	public static void play(URL url) {
		try {
			Sequence seq = MidiSystem.getSequence(url);
			midi = MidiSystem.getSequencer();
			midi.open();
			midi.setSequence(seq);

			if (!midi.isRunning())
				midi.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void stop() {
		if (midi.isRunning())
			midi.stop();

		if (midi.isOpen())
			midi.close();
	}

	public static void main(String[] args) {
		URL url = PlayMidi.class.getResource("doraemon.mid");
		PlayMidi.play(url);
		long time = midi.getMicrosecondLength() / 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		PlayMidi.stop();
	}
}