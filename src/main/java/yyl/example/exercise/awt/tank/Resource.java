package yyl.example.exercise.awt.tank;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Resource {
	private static Image getImage(String path) {
		return Toolkit.getDefaultToolkit().getImage(Constant.class.getResource(path));
	}

	public static Image BLOCK_0 = getImage("image/block_0.gif");//��ש0
	public static Image BLOCK_1 = getImage("image/block_1.gif");//��ש1
	public static Image BLOCK_2 = getImage("image/block_2.gif");//��ש2
	public static Image BLOCK_3 = getImage("image/block_3.gif");//��ש3
	public static Image BLOCK_4 = getImage("image/block_4.gif");//��ש4
	public static Image STEEL = getImage("image/steel.gif");//��
	public static Image FOREST = getImage("image/forest.gif");//ɭ��
	public static Image LAKE = getImage("image/lake.gif");//����
	public static Image ICE = getImage("image/ice.gif");//��
	public static Image BULLET_UP = getImage("image/bullet_up.gif");//�ӵ�(����>��)
	public static Image BULLET_DOWN = getImage("image/bullet_down.gif");//�ӵ�(����>��)
	public static Image BULLET_LEFT = getImage("image/bullet_left.gif");//�ӵ�(����>��)
	public static Image BULLET_RIGHT = getImage("image/bullet_right.gif");//�ӵ�(����>��)
	public static Image DESTROY = getImage("image/destroy.gif");//����

	public static Image TANK_UP = getImage("image/tank_up.gif");//̹��(����>��)
	public static Image TANK_DOWN = getImage("image/tank_down.gif");//̹��(����>��)
	public static Image TANK_LEFT = getImage("image/tank_left.gif");//̹��(����>��)
	public static Image TANK_RIGHT = getImage("image/tank_right.gif");//̹��(����>��)

	public static Image TANK2_UP = getImage("image/tank2_up.gif");//̹��(����>��)
	public static Image TANK2_DOWN = getImage("image/tank2_down.gif");//̹��(����>��)
	public static Image TANK2_LEFT = getImage("image/tank2_left.gif");//̹��(����>��)
	public static Image TANK2_RIGHT = getImage("image/tank2_right.gif");//̹��(����>��)

	public static Image E_TANK0_UP = getImage("image/e_tank0_up.gif");//̹��(����>��)
	public static Image E_TANK0_DOWN = getImage("image/e_tank0_down.gif");//̹��(����>��)
	public static Image E_TANK0_LEFT = getImage("image/e_tank0_left.gif");//̹��(����>��)
	public static Image E_TANK0_RIGHT = getImage("image/e_tank0_right.gif");//̹��(����>��)

	public static Image E_TANK1_UP = getImage("image/e_tank1_up.gif");//̹��(����>��)
	public static Image E_TANK1_DOWN = getImage("image/e_tank1_down.gif");//̹��(����>��)
	public static Image E_TANK1_LEFT = getImage("image/e_tank1_left.gif");//̹��(����>��)
	public static Image E_TANK1_RIGHT = getImage("image/e_tank1_right.gif");//̹��(����>��)

	public static Image E_TANK2_UP = getImage("image/e_tank2_up.gif");//̹��(����>��)
	public static Image E_TANK2_DOWN = getImage("image/e_tank2_down.gif");//̹��(����>��)
	public static Image E_TANK2_LEFT = getImage("image/e_tank2_left.gif");//̹��(����>��)
	public static Image E_TANK2_RIGHT = getImage("image/e_tank2_right.gif");//̹��(����>��)

	public static Image E_TANK3_UP = getImage("image/e_tank3_up.gif");//̹��(����>��)
	public static Image E_TANK3_DOWN = getImage("image/e_tank3_down.gif");//̹��(����>��)
	public static Image E_TANK3_LEFT = getImage("image/e_tank3_left.gif");//̹��(����>��)
	public static Image E_TANK3_RIGHT = getImage("image/e_tank3_right.gif");//̹��(����>��)

	public static Image TANK_DESTROY= getImage("image/tank_destroy.gif");//̹�˻���
	
	
	public static char[][] readys() {
		char[][] info = new char[13][13];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(Resource.class.getResourceAsStream("config.ini")));
			for (int y = 0; y < 13; y++) {
				String line = br.readLine().trim();
				for (int x = 0; x < 13; x++) {
					info[x][y] = line.charAt(x);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return info;
	}
}
