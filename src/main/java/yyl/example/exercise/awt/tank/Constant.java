package yyl.example.exercise.awt.tank;

public class Constant {
	public final static int Canvas_WIDTH = 52 * 4*2;
	public final static int Canvas_HEIGHT = 52 * 4*2;

	public static final int FACE_UP = 1;
	public static final int FACE_DOWN = 2;
	public static final int FACE_LEFT = 3;
	public static final int FACE_RIGHT = 4;

	public static final int HANDLE_MOVE_STEP = 0x00000000;//0.0000
	public static final int HANDLE_MOVE_UP = 0x00000001;//0.0001
	public static final int HANDLE_MOVE_DOWN = 0x00000002;//0.0010
	public static final int HANDLE_MOVE_LEFT = 0x00000004;//0.0100
	public static final int HANDLE_MOVE_RIGHT = 0x00000008;//0.1000
	public static final int HANDLE_FIRE_A = 0x00000010;//0.0000
	public static final int HANDLE_FIRE_B = 0x00000020;//0.0000

	public static final int HANDLE_UN_MOVE_UP = 0xE;//0.0001
	public static final int HANDLE_UN_MOVE_DOWN = 0xD;//0.0010
	public static final int HANDLE_UN_MOVE_LEFT = 0xB;//0.0100
	public static final int HANDLE_UN_MOVE_RIGHT = 0x7;//0.1000
	public static final int HANDLE_UN_FIRE_A = 0x40;//0.0000
	public static final int HANDLE_UN_FIRE_B = 0x80;//0.0000

}
