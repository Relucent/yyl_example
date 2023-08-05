package yyl.example.basic.sound;

import java.net.URL;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/**
 * 获取音乐元数据的示例（获得MIDI作者信息）
 */
public class MidiMetaExample {

    public static void main(String[] args) {

        // 获得唱片地址
        URL url = MidiMetaExample.class.getResource("doraemon.mid");

        try {
            // 得到唱片 (指定的MIDI序列)
            Sequence sequence = MidiSystem.getSequence(url);

            // 遍历MIDI文件的各个轨道，查找元数据事件
            for (Track track : sequence.getTracks()) {

                // 查找事件
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();

                    // 查找元数据事件
                    if (message instanceof MetaMessage) {
                        MetaMessage metaMessage = (MetaMessage) message;

                        // 检查它是否是作者信息事件（元事件类型0x03）
                        if (metaMessage.getType() == 0x03) {
                            String authorInfo = new String(metaMessage.getData(), "UTF-8");
                            System.out.println("Author Information: " + authorInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
