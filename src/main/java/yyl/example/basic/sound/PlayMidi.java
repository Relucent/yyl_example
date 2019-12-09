package yyl.example.basic.sound;

import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * 播放MIDI音乐 (Window-XP,7,8,10)
 */
public class PlayMidi {

    public static void main(String[] args) {

        // 获得唱片地址
        URL url = PlayMidi.class.getResource("doraemon.mid");

        // 播放器
        Sequencer sequencer = null;
        try {
            // 得到唱片 (指定的MIDI序列)
            Sequence seq = MidiSystem.getSequence(url);

            // 得到播放器
            sequencer = MidiSystem.getSequencer();

            // 打开设备
            sequencer.open();

            // 将唱片放入播放器
            sequencer.setSequence(seq);

            // 获得当前音乐的长度(单位微秒)
            long time = sequencer.getMicrosecondLength() / 1000;

            // 开始播放音乐
            sequencer.start();

            // 等待音乐播放结束
            Thread.sleep(time);

            // 是否正处于播放状态
            if (sequencer.isRunning()) {
                // 停止播放
                sequencer.stop();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 如果设备处于开启状态
            if (sequencer != null && sequencer.isOpen()) {
                // 关闭设备(释放使用的系统资源)
                sequencer.close();
            }
        }
    }
}
